package com.project.demo.logic.entity.game;

import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import com.project.demo.rest.game.dto.CloseRoomResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

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
            finalizarSesion(game);
        }

        game.setStatus(GameStatus.CERRADA);
        game.setClosedAt(LocalDateTime.now());
        Game savedGame = gameRepository.save(game);

        notifyPlayersWithRetry(savedGame);

        return CloseRoomResult.closed(buildResponse(savedGame));
    }

    private CloseRoomResponse buildResponse(Game game) {
        return new CloseRoomResponse(game.getId(), game.getStatus(), game.getClosedAt());
    }

    private boolean hasActiveGameSession(Game game) {
        // TODO: Integrate active session verification when session module is available.
        return false;
    }

    private void finalizarSesion(Game game) {
        // TODO: Integrate game session finalization logic.
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
}