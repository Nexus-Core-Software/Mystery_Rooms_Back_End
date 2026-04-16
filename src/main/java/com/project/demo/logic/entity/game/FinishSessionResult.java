package com.project.demo.logic.entity.game;

import com.project.demo.rest.game.dto.FinishSessionResponse;

public class FinishSessionResult {
    private final String message;
    private final FinishSessionResponse response;

    private FinishSessionResult(String message, FinishSessionResponse response) {
        this.message = message;
        this.response = response;
    }

    public static FinishSessionResult finished(FinishSessionResponse response) {
        return new FinishSessionResult("Sesión finalizada correctamente", response);
    }

    public static FinishSessionResult alreadyFinished(FinishSessionResponse response) {
        return new FinishSessionResult("La sesión ya fue finalizada anteriormente.", response);
    }

    public String getMessage() {
        return message;
    }

    public FinishSessionResponse getResponse() {
        return response;
    }
}
