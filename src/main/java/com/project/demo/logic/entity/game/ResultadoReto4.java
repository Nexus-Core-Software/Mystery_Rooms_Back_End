package com.project.demo.logic.entity.game;

import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

// Guarda el resultado del Reto 4 (Simulación de IA) por usuario y partida.
@Entity
@Table(name = "resultados_reto4")
public class ResultadoReto4 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partida_id", nullable = false)
    private Partida partida;

    // Referencia al User existente en entity/user/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "puntaje_obtenido", nullable = false)
    private Integer puntajeObtenido;

    @Column(name = "intentos_fallidos", nullable = false)
    private Integer intentosFallidos;

    @Column(name = "completado", nullable = false)
    private Boolean completado = false;

    @Column(name = "tiempo_segundos", nullable = false)
    private Integer tiempoSegundos;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    // ── Getters y Setters ──────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Partida getPartida() { return partida; }
    public void setPartida(Partida partida) { this.partida = partida; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Integer getPuntajeObtenido() { return puntajeObtenido; }
    public void setPuntajeObtenido(Integer v) { this.puntajeObtenido = v; }

    public Integer getIntentosFallidos() { return intentosFallidos; }
    public void setIntentosFallidos(Integer v) { this.intentosFallidos = v; }

    public Boolean getCompletado() { return completado; }
    public void setCompletado(Boolean v) { this.completado = v; }

    public Integer getTiempoSegundos() { return tiempoSegundos; }
    public void setTiempoSegundos(Integer v) { this.tiempoSegundos = v; }

    public Date getCreatedAt() { return createdAt; }
}