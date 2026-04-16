package com.project.demo.rest.game;

import com.project.demo.logic.entity.game.CloseRoomResult;
import com.project.demo.logic.entity.game.FinishSessionResult;
import com.project.demo.logic.entity.game.Game;
import com.project.demo.logic.entity.game.GameSessionResult;
import com.project.demo.logic.entity.game.GameRepository;
import com.project.demo.logic.entity.game.GameService;
import com.project.demo.logic.entity.http.GlobalResponseHandler;
import com.project.demo.logic.entity.user.User;
import com.project.demo.rest.game.dto.CloseRoomRequest;
import com.project.demo.rest.game.dto.FinishSessionRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/games", "/api/games"})
public class GameRestController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameService gameService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'USER')")
    public List<Game> getAllGames(){
        return gameRepository.findAll();
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public Game updateGame(@PathVariable Long id, @RequestBody Game game) {
        return gameRepository.findById(id)
                .map(existingGame -> {
                    existingGame.setName(game.getName());
                    existingGame.setDescription(game.getDescription());
                    existingGame.setImgURL(game.getImgURL());
                    existingGame.setStatus(game.getStatus());
                    return gameRepository.save(existingGame);
                })
                .orElseGet(() -> {
                    game.setId(id);
                    return gameRepository.save(game);
                });
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public Game addGame(@RequestBody Game game) {
        return gameService.createGame(game);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteGame (@PathVariable Long id) {
        gameRepository.deleteById(id);
    }

    @DeleteMapping("/{roomId}/close")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> closeRoom(
            @PathVariable Long roomId,
            @RequestBody CloseRoomRequest closeRoomRequest,
            HttpServletRequest request
    ) {
        if (closeRoomRequest == null || !Boolean.TRUE.equals(closeRoomRequest.getConfirmacion())) {
            return new GlobalResponseHandler().handleResponse(
                    "Debes confirmar el cierre de la sala.",
                    HttpStatus.BAD_REQUEST,
                    request
            );
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();

        CloseRoomResult closeRoomResult = gameService.closeRoom(roomId, authenticatedUser.getId());
        return new GlobalResponseHandler().handleResponse(
                closeRoomResult.getMessage(),
                closeRoomResult.getResponse(),
                HttpStatus.OK,
                request
        );
    }

    @PatchMapping("/{roomId}/pause")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> pauseGame(@PathVariable Long roomId, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();

        GameSessionResult gameSessionResult = gameService.pauseGame(roomId, authenticatedUser.getId());
        return new GlobalResponseHandler().handleResponse(
                gameSessionResult.getMessage(),
                gameSessionResult.getResponse(),
                HttpStatus.OK,
                request
        );
    }

    @PatchMapping("/{roomId}/resume")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> resumeGame(@PathVariable Long roomId, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();

        GameSessionResult gameSessionResult = gameService.resumeGame(roomId, authenticatedUser.getId());
        return new GlobalResponseHandler().handleResponse(
                gameSessionResult.getMessage(),
                gameSessionResult.getResponse(),
                HttpStatus.OK,
                request
        );
    }

        @PostMapping("/{roomId}/finish")
        @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
        public ResponseEntity<?> finishSession(
            @PathVariable Long roomId,
            @RequestBody(required = false) FinishSessionRequest finishSessionRequest,
            HttpServletRequest request
        ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();

        String closureReason = finishSessionRequest != null ? finishSessionRequest.getClosureReason() : null;
        FinishSessionResult finishSessionResult = gameService.finishSession(
            roomId,
            authenticatedUser.getId(),
            closureReason
        );

        return new GlobalResponseHandler().handleResponse(
            finishSessionResult.getMessage(),
            finishSessionResult.getResponse(),
            HttpStatus.OK,
            request
        );
        }

}
