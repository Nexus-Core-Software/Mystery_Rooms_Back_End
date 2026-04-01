package com.project.demo.rest.game.dto;

import com.project.demo.logic.entity.game.GameStatus;

import java.time.LocalDateTime;

public class GameSessionResponse {
    private Long roomId;
    private GameStatus estado;
    private LocalDateTime pausedAt;
    private LocalDateTime resumedAt;

    public GameSessionResponse(Long roomId, GameStatus estado, LocalDateTime pausedAt, LocalDateTime resumedAt) {
        this.roomId = roomId;
        this.estado = estado;
        this.pausedAt = pausedAt;
        this.resumedAt = resumedAt;
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

    public LocalDateTime getPausedAt() {
        return pausedAt;
    }

    public void setPausedAt(LocalDateTime pausedAt) {
        this.pausedAt = pausedAt;
    }

    public LocalDateTime getResumedAt() {
        return resumedAt;
    }

    public void setResumedAt(LocalDateTime resumedAt) {
        this.resumedAt = resumedAt;
    }
}