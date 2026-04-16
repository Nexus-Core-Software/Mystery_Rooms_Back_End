package com.project.demo.rest.game.dto;

import com.project.demo.logic.entity.game.GameStatus;

import java.time.LocalDateTime;

public class FinishSessionResponse {
    private Long roomId;
    private GameStatus estado;
    private LocalDateTime finishedAt;
    private Long sessionDuration;
    private String closureReason;

    public FinishSessionResponse(
            Long roomId,
            GameStatus estado,
            LocalDateTime finishedAt,
            Long sessionDuration,
            String closureReason
    ) {
        this.roomId = roomId;
        this.estado = estado;
        this.finishedAt = finishedAt;
        this.sessionDuration = sessionDuration;
        this.closureReason = closureReason;
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

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Long getSessionDuration() {
        return sessionDuration;
    }

    public void setSessionDuration(Long sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

    public String getClosureReason() {
        return closureReason;
    }

    public void setClosureReason(String closureReason) {
        this.closureReason = closureReason;
    }
}
