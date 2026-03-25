package com.project.demo.logic.entity.game;

import com.project.demo.rest.game.dto.CloseRoomResponse;

public class CloseRoomResult {
    private final String message;
    private final CloseRoomResponse response;

    private CloseRoomResult(String message, CloseRoomResponse response) {
        this.message = message;
        this.response = response;
    }

    public static CloseRoomResult closed(CloseRoomResponse response) {
        return new CloseRoomResult("Sala cerrada correctamente", response);
    }

    public static CloseRoomResult notAvailable(CloseRoomResponse response) {
        return new CloseRoomResult("La sala ya no está disponible.", response);
    }

    public String getMessage() {
        return message;
    }

    public CloseRoomResponse getResponse() {
        return response;
    }
}