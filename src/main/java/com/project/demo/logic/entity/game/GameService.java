package com.project.demo.logic.entity.game;

import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import com.project.demo.rest.game.dto.CloseRoomResponse;
import com.project.demo.rest.game.dto.FinishSessionResponse;
import com.project.demo.rest.game.dto.GameSessionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class GameService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public GameService(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    public Game createGame(Game game) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User host = userRepository.findByEmail(email).orElseThrow();

        game.setStatus(GameStatus.ACTIVA);
        game.setHost(host);

        return gameRepository.save(game);
    }

    public CloseRoomResult closeRoom(Long roomId, Long userId) {
        Game game = gameRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sala no encontrada."));

        if (game.getStatus() != GameStatus.ACTIVA) {
            return CloseRoomResult.notAvailable(buildResponse(game));
        }

        if (game.getHost() == null || !game.getHost().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permisos para cerrar esta sala.");
        }

        if (hasActiveGameSession(game)) {
            finishSession(game.getId(), userId, null);
        }

        game.setStatus(GameStatus.CERRADA);
        game.setClosedAt(LocalDateTime.now());
        Game savedGame = gameRepository.save(game);

        notifyPlayersWithRetry(savedGame);

        return CloseRoomResult.closed(buildResponse(savedGame));
    }

    public GameSessionResult pauseGame(Long roomId, Long userId) {
        Game game = gameRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sala no encontrada."));

        if (game.getStatus() != GameStatus.ACTIVA) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La sesión no está activa");
        }

        if (game.getHost() == null || !game.getHost().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permisos para pausar/reanudar esta sesión");
        }

        game.setStatus(GameStatus.PAUSADA);
        game.setPausedAt(LocalDateTime.now());
        game.setResumedAt(null);

        Game savedGame = gameRepository.save(game);

        notifyPlayersSessionEventWithRetry(savedGame, "paused");

        return GameSessionResult.paused(buildSessionResponse(savedGame));
    }

    public GameSessionResult resumeGame(Long roomId, Long userId) {
        Game game;
        try {
            game = gameRepository.findById(roomId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sala no encontrada."));
        } catch (RuntimeException exception) {
            if (isInvalidGameStatusException(exception)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado de la sesión no es válido");
            }
            throw exception;
        }

        if (game.getStatus() != GameStatus.PAUSADA) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La sesión no está pausada");
        }

        if (game.getHost() == null || !game.getHost().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permisos para pausar/reanudar esta sesión");
        }

        game.setStatus(GameStatus.ACTIVA);
        game.setResumedAt(LocalDateTime.now());

        Game savedGame = gameRepository.save(game);

        notifyPlayersSessionEventWithRetry(savedGame, "resumed");

        return GameSessionResult.resumed(buildSessionResponse(savedGame));
    }

    public FinishSessionResult finishSession(Long roomId, Long userId, String closureReason) {
        Game game = gameRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sala no encontrada."));

        if (game.getStatus() != GameStatus.ACTIVA && game.getStatus() != GameStatus.PAUSADA) {
            return FinishSessionResult.alreadyFinished(buildFinishResponse(game));
        }

        if (game.getHost() == null || !game.getHost().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Solo el host puede finalizar la sesión");
        }

        LocalDateTime finishedAt = LocalDateTime.now();
        Long sessionDuration = calculateSessionDurationSeconds(game.getCreatedAt(), finishedAt);

        game.setSessionDuration(sessionDuration);
        game.setStatus(GameStatus.FINALIZADA);
        game.setFinishedAt(finishedAt);
        game.setClosureReason(resolveClosureReason(closureReason));

        Game savedGame = saveGameWithRetry(game);

        notifyPlayersSessionEventWithRetry(savedGame, "finished");

        return FinishSessionResult.finished(buildFinishResponse(savedGame));
    }

    private CloseRoomResponse buildResponse(Game game) {
        return new CloseRoomResponse(game.getId(), game.getStatus(), game.getClosedAt());
    }

    private GameSessionResponse buildSessionResponse(Game game) {
        return new GameSessionResponse(game.getId(), game.getStatus(), game.getPausedAt(), game.getResumedAt());
    }

    private FinishSessionResponse buildFinishResponse(Game game) {
        return new FinishSessionResponse(
                game.getId(),
                game.getStatus(),
                game.getFinishedAt(),
                game.getSessionDuration(),
                game.getClosureReason()
        );
    }

    private boolean hasActiveGameSession(Game game) {
        // TODO: Integrate active session verification when session module is available.
        return false;
    }

    private Game saveGameWithRetry(Game game) {
        int[] backoffMillis = new int[]{100, 200, 400};
        int attempt = 0;

        while (true) {
            try {
                return gameRepository.save(game);
            } catch (RuntimeException exception) {
                if (attempt >= backoffMillis.length) {
                    throw new ResponseStatusException(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "No se pudo finalizar la sesión.",
                            exception
                    );
                }

                int waitTime = backoffMillis[attempt];
                attempt++;
                LOGGER.error(
                        "Failed to save game session for room {}. Retrying in {}ms.",
                        game.getId(),
                        waitTime,
                        exception
                );

                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    throw new ResponseStatusException(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "No se pudo finalizar la sesión.",
                            interruptedException
                    );
                }
            }
        }
    }

    private Long calculateSessionDurationSeconds(Date createdAt, LocalDateTime finishedAt) {
        if (createdAt == null || finishedAt == null) {
            return null;
        }

        LocalDateTime createdAtTime = LocalDateTime.ofInstant(createdAt.toInstant(), ZoneId.systemDefault());
        long durationSeconds = Duration.between(createdAtTime, finishedAt).getSeconds();
        return durationSeconds < 0 ? 0L : durationSeconds;
    }

    private String resolveClosureReason(String closureReason) {
        if (closureReason == null || closureReason.trim().isEmpty()) {
            return "Finalización manual";
        }

        return closureReason.trim();
    }

    private void notifyPlayersWithRetry(Game game) {
        try {
            notifyPlayersRoomClosed(game);
        } catch (Exception firstError) {
            LOGGER.error("Failed to notify players for room {}. Retrying once.", game.getId(), firstError);
            try {
                notifyPlayersRoomClosed(game);
            } catch (Exception secondError) {
                LOGGER.error("Retry failed when notifying players for room {}.", game.getId(), secondError);
            }
        }
    }

    private void notifyPlayersRoomClosed(Game game) {
        // TODO: Notify connected players through WebSocket when available.
    }

    private void notifyPlayersSessionEventWithRetry(Game game, String action) {
        try {
            notifyPlayersSessionEvent(game, action);
        } catch (Exception firstError) {
            LOGGER.error("Failed to notify players that room {} was {}. Retrying once.", game.getId(), action, firstError);
            try {
                notifyPlayersSessionEvent(game, action);
            } catch (Exception secondError) {
                LOGGER.error("Retry failed while notifying players that room {} was {}.", game.getId(), action, secondError);
            }
        }
    }

    private void notifyPlayersSessionEvent(Game game, String action) {
        // TODO: Broadcast pause/resume events to connected players through WebSocket when available.
    }

    private boolean isInvalidGameStatusException(Throwable throwable) {
        Throwable current = throwable;
        while (current != null) {
            if (current instanceof IllegalArgumentException
                    && current.getMessage() != null
                    && current.getMessage().contains("No enum constant com.project.demo.logic.entity.game.GameStatus")) {
                return true;
            }
            current = current.getCause();
        }
        return false;
    }
}