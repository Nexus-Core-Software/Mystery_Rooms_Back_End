package com.project.demo.logic.entity.evento;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.demo.logic.entity.team.Player;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recompensa_evento")
public class RecompensaEvento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(name = "puntos")
    private Integer puntos;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "recompensa_evento_jugador",
            joinColumns = @JoinColumn(name = "recompensa_evento_id"),
            inverseJoinColumns = @JoinColumn(name = "jugador_id")
    )
    @JsonIgnore
    private List<Player> jugadoresOtorgados = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public List<Player> getJugadoresOtorgados() {
        return jugadoresOtorgados;
    }

    public void setJugadoresOtorgados(List<Player> jugadoresOtorgados) {
        this.jugadoresOtorgados = jugadoresOtorgados;
    }
}
