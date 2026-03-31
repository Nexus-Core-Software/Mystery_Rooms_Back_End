package com.project.demo.logic.entity.game;

import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

// Representa una sesión activa de juego.
// Una Partida pertenece a un Game (el juego del catálogo).
@Entity
@Table(name = "partidas")
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Game — la partida pertenece a un juego del catálogo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(name = "tiempo_inicio", nullable = false)
    private LocalDateTime tiempoInicio;

    @Column(name = "tiempo_fin")
    private LocalDateTime tiempoFin;

    // Lista de jugadores separados por coma: "userId1,userId2"
    @Column(name = "jugadores", nullable = false)
    private String jugadores;

    @Column(name = "tiempo_actual", nullable = false)
    private LocalTime tiempoActual;

    // Estado: "Iniciada", "Jugando", "Pausada", "Finalizada"
    @Column(name = "estado", nullable = false, length = 15)
    private String estado = "Iniciada";

    // Resultado: "Ganada", "Perdida", "Abandono"
    @Column(name = "resultado")
    private String resultado;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    // ── Getters y Setters ──────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Game getGame() { return game; }
    public void setGame(Game game) { this.game = game; }

    public LocalDateTime getTiempoInicio() { return tiempoInicio; }
    public void setTiempoInicio(LocalDateTime tiempoInicio) { this.tiempoInicio = tiempoInicio; }

    public LocalDateTime getTiempoFin() { return tiempoFin; }
    public void setTiempoFin(LocalDateTime tiempoFin) { this.tiempoFin = tiempoFin; }

    public String getJugadores() { return jugadores; }
    public void setJugadores(String jugadores) { this.jugadores = jugadores; }

    public LocalTime getTiempoActual() { return tiempoActual; }
    public void setTiempoActual(LocalTime tiempoActual) { this.tiempoActual = tiempoActual; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }

    public Date getCreatedAt() { return createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
}