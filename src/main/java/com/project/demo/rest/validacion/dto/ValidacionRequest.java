package com.project.demo.rest.validacion.dto;

public class ValidacionRequest {
    private Long jugadorId;
    private String actividadTipo;
    private Long actividadId;
    private String respuesta;
    private String respuestaCorrecta;
    private Integer puntajeMaximo;

    public Long getJugadorId() {
        return jugadorId;
    }

    public void setJugadorId(Long jugadorId) {
        this.jugadorId = jugadorId;
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

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public Integer getPuntajeMaximo() {
        return puntajeMaximo;
    }

    public void setPuntajeMaximo(Integer puntajeMaximo) {
        this.puntajeMaximo = puntajeMaximo;
    }
}
