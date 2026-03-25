package com.project.demo.rest.game.dto;

import com.project.demo.logic.entity.game.GameStatus;

import java.time.LocalDateTime;

public class CloseRoomResponse {
    private Long roomId;
    private GameStatus estado;
    private LocalDateTime closedAt;

    public CloseRoomResponse(Long roomId, GameStatus estado, LocalDateTime closedAt) {
        this.roomId = roomId;
        this.estado = estado;
        this.closedAt = closedAt;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public GameStatus getEstado() {
        return estado;
    }

    public void setEstado(GameStatus estado) {
        this.estado = estado;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }
}