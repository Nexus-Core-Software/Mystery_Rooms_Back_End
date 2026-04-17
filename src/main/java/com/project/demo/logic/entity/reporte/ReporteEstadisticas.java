package com.project.demo.logic.entity.reporte;

import com.project.demo.logic.entity.game.GameStatus;

import java.time.LocalDateTime;
import java.util.Map;

public class ReporteEstadisticas {
    private LocalDateTime generatedAt;
    private long totalPlayers;
    private long totalTeams;
    private long totalGames;
    private long totalOrders;
    private long totalGifts;
    private long totalGiftLists;
    private long totalEventos;
    private long totalRecompensas;
    private long totalRewardPoints;
    private long activeEventos;
    private Map<GameStatus, Long> gamesByStatus;

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public long getTotalPlayers() {
        return totalPlayers;
    }

    public void setTotalPlayers(long totalPlayers) {
        this.totalPlayers = totalPlayers;
    }

    public long getTotalTeams() {
        return totalTeams;
    }

    public void setTotalTeams(long totalTeams) {
        this.totalTeams = totalTeams;
    }

    public long getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(long totalGames) {
        this.totalGames = totalGames;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public long getTotalGifts() {
        return totalGifts;
    }

    public void setTotalGifts(long totalGifts) {
        this.totalGifts = totalGifts;
    }

    public long getTotalGiftLists() {
        return totalGiftLists;
    }

    public void setTotalGiftLists(long totalGiftLists) {
        this.totalGiftLists = totalGiftLists;
    }

    public long getTotalEventos() {
        return totalEventos;
    }

    public void setTotalEventos(long totalEventos) {
        this.totalEventos = totalEventos;
    }

    public long getTotalRecompensas() {
        return totalRecompensas;
    }

    public void setTotalRecompensas(long totalRecompensas) {
        this.totalRecompensas = totalRecompensas;
    }

    public long getTotalRewardPoints() {
        return totalRewardPoints;
    }

    public void setTotalRewardPoints(long totalRewardPoints) {
        this.totalRewardPoints = totalRewardPoints;
    }

    public long getActiveEventos() {
        return activeEventos;
    }

    public void setActiveEventos(long activeEventos) {
        this.activeEventos = activeEventos;
    }

    public Map<GameStatus, Long> getGamesByStatus() {
        return gamesByStatus;
    }

    public void setGamesByStatus(Map<GameStatus, Long> gamesByStatus) {
        this.gamesByStatus = gamesByStatus;
    }
}
