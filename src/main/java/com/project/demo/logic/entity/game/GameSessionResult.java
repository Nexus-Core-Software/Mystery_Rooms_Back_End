package com.project.demo.logic.entity.game;

import com.project.demo.rest.game.dto.GameSessionResponse;

public class GameSessionResult {
    private final String message;
    private final GameSessionResponse response;

    private GameSessionResult(String message, GameSessionResponse response) {
        this.message = message;
        this.response = response;
    }

    public static GameSessionResult paused(GameSessionResponse response) {
        return new GameSessionResult("Sesión pausada correctamente", response);
    }

    public static GameSessionResult resumed(GameSessionResponse response) {
        return new GameSessionResult("Sesión reanudada correctamente", response);
    }

    public String getMessage() {
        return message;
    }

    public GameSessionResponse getResponse() {
        return response;
    }
}