package com.project.demo.rest.validacion.dto;

public class ResultadoValidacion {
    private boolean esCorrecta;
    private String mensajeRetroalimentacion;
    private Integer puntajeObtenido;
    private String respuestaCorrecta;

    public ResultadoValidacion() {
    }

    public ResultadoValidacion(
            boolean esCorrecta,
            String mensajeRetroalimentacion,
            Integer puntajeObtenido,
            String respuestaCorrecta
    ) {
        this.esCorrecta = esCorrecta;
        this.mensajeRetroalimentacion = mensajeRetroalimentacion;
        this.puntajeObtenido = puntajeObtenido;
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public boolean getEsCorrecta() {
        return esCorrecta;
    }

    public void setEsCorrecta(boolean esCorrecta) {
        this.esCorrecta = esCorrecta;
    }

    public String getMensajeRetroalimentacion() {
        return mensajeRetroalimentacion;
    }

    public void setMensajeRetroalimentacion(String mensajeRetroalimentacion) {
        this.mensajeRetroalimentacion = mensajeRetroalimentacion;
    }

    public Integer getPuntajeObtenido() {
        return puntajeObtenido;
    }

    public void setPuntajeObtenido(Integer puntajeObtenido) {
        this.puntajeObtenido = puntajeObtenido;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }
}
