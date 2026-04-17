package com.project.demo.logic.entity.validacion;

import com.project.demo.logic.entity.team.Player;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "validacion_intento")
public class ValidacionIntento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Column(name = "actividad_tipo", nullable = false)
    private String actividadTipo;

    @Column(name = "actividad_id", nullable = false)
    private Long actividadId;

    @Column(name = "respuesta_dada", nullable = false)
    private String respuestaDada;

    @Column(name = "respuesta_correcta")
    private String respuestaCorrecta;

    @Column(name = "es_correcta", nullable = false)
    private Boolean esCorrecta;

    @Column(name = "puntaje_obtenido", nullable = false)
    private Integer puntajeObtenido;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getActividadTipo() {
        return actividadTipo;
    }

    public void setActividadTipo(String actividadTipo) {
        this.actividadTipo = actividadTipo;
    }

    public Long getActividadId() {
        return actividadId;
    }

    public void setActividadId(Long actividadId) {
        this.actividadId = actividadId;
    }

    public String getRespuestaDada() {
        return respuestaDada;
    }

    public void setRespuestaDada(String respuestaDada) {
        this.respuestaDada = respuestaDada;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public Boolean getEsCorrecta() {
        return esCorrecta;
    }

    public void setEsCorrecta(Boolean esCorrecta) {
        this.esCorrecta = esCorrecta;
    }

    public Integer getPuntajeObtenido() {
        return puntajeObtenido;
    }

    public void setPuntajeObtenido(Integer puntajeObtenido) {
        this.puntajeObtenido = puntajeObtenido;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
