package com.project.demo.rest.game.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Reto4RequestDTO {

    @NotNull(message = "El ID de partida es obligatorio")
    private Long partidaId;

    @NotNull(message = "El puntaje es obligatorio")
    @Min(value = 0, message = "El puntaje no puede ser negativo")
    private Integer puntajeObtenido;

    @NotNull(message = "Los intentos fallidos son obligatorios")
    @Min(value = 0)
    private Integer intentosFallidos;

    @NotNull(message = "El campo completado es obligatorio")
    private Boolean completado;

    @NotNull(message = "El tiempo es obligatorio")
    @Min(value = 0)
    private Integer tiempoSegundos;

    public Long getPartidaId() { return partidaId; }
    public void setPartidaId(Long v) { this.partidaId = v; }

    public Integer getPuntajeObtenido() { return puntajeObtenido; }
    public void setPuntajeObtenido(Integer v) { this.puntajeObtenido = v; }

    public Integer getIntentosFallidos() { return intentosFallidos; }
    public void setIntentosFallidos(Integer v) { this.intentosFallidos = v; }

    public Boolean getCompletado() { return completado; }
    public void setCompletado(Boolean v) { this.completado = v; }

    public Integer getTiempoSegundos() { return tiempoSegundos; }
    public void setTiempoSegundos(Integer v) { this.tiempoSegundos = v; }
}