package com.project.demo.rest.game.dto;

import java.util.Date;

public class Reto4ResponseDTO {

    private Long id;
    private Long partidaId;
    private Long userId;
    private String userName;        // name del User real
    private String userEmail;       // email del User real
    private Integer puntajeObtenido;
    private Integer intentosFallidos;
    private Boolean completado;
    private Integer tiempoSegundos;
    private Date createdAt;
    private String mensaje;

    public Reto4ResponseDTO(Long id, Long partidaId, Long userId,
                            String userName, String userEmail,
                            Integer puntajeObtenido, Integer intentosFallidos,
                            Boolean completado, Integer tiempoSegundos,
                            Date createdAt) {
        this.id = id;
        this.partidaId = partidaId;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.puntajeObtenido = puntajeObtenido;
        this.intentosFallidos = intentosFallidos;
        this.completado = completado;
        this.tiempoSegundos = tiempoSegundos;
        this.createdAt = createdAt;
        this.mensaje = Boolean.TRUE.equals(completado)
                ? "Reto 4 completado. Fragmento #4 desbloqueado."
                : "Resultado del Reto 4 guardado.";
    }

    public Long getId() { return id; }
    public Long getPartidaId() { return partidaId; }
    public Long getUserId() { return userId; }
    public String getUserName() { return userName; }
    public String getUserEmail() { return userEmail; }
    public Integer getPuntajeObtenido() { return puntajeObtenido; }
    public Integer getIntentosFallidos() { return intentosFallidos; }
    public Boolean getCompletado() { return completado; }
    public Integer getTiempoSegundos() { return tiempoSegundos; }
    public Date getCreatedAt() { return createdAt; }
    public String getMensaje() { return mensaje; }
}