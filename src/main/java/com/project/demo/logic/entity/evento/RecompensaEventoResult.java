package com.project.demo.logic.entity.evento;

import org.springframework.http.HttpStatus;

import java.util.List;

public class RecompensaEventoResult {
    private final String message;
    private final List<RecompensaEvento> response;
    private final HttpStatus status;

    private RecompensaEventoResult(String message, List<RecompensaEvento> response, HttpStatus status) {
        this.message = message;
        this.response = response;
        this.status = status;
    }

    public static RecompensaEventoResult granted(List<RecompensaEvento> response) {
        return new RecompensaEventoResult("Recompensa otorgada correctamente", response, HttpStatus.OK);
    }

    public static RecompensaEventoResult alreadyGranted(List<RecompensaEvento> response) {
        return new RecompensaEventoResult("La recompensa ya fue otorgada al jugador.", response, HttpStatus.OK);
    }

    public static RecompensaEventoResult noActiveEvent() {
        return new RecompensaEventoResult("No hay un evento activo.", null, HttpStatus.BAD_REQUEST);
    }

    public static RecompensaEventoResult eventNotFound() {
        return new RecompensaEventoResult("Evento no encontrado.", null, HttpStatus.NOT_FOUND);
    }

    public static RecompensaEventoResult playerNotFound() {
        return new RecompensaEventoResult("Jugador no encontrado.", null, HttpStatus.NOT_FOUND);
    }

    public static RecompensaEventoResult noRewards() {
        return new RecompensaEventoResult("El evento no tiene recompensas.", null, HttpStatus.NOT_FOUND);
    }

    public String getMessage() {
        return message;
    }

    public List<RecompensaEvento> getResponse() {
        return response;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
