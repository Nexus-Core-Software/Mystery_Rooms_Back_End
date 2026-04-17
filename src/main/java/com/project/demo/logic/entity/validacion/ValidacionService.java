package com.project.demo.logic.entity.validacion;

import com.project.demo.logic.entity.team.Player;
import com.project.demo.logic.entity.team.PlayerRepository;
import com.project.demo.rest.validacion.dto.ResultadoValidacion;
import com.project.demo.rest.validacion.dto.ValidacionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Locale;

@Service
public class ValidacionService {
    private final PlayerRepository playerRepository;
    private final ValidacionIntentoRepository validacionIntentoRepository;

    public ValidacionService(
            PlayerRepository playerRepository,
            ValidacionIntentoRepository validacionIntentoRepository
    ) {
        this.playerRepository = playerRepository;
        this.validacionIntentoRepository = validacionIntentoRepository;
    }

    public ResultadoValidacion validarRespuesta(ValidacionRequest request) {
        if (request == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solicitud invalida.");
        }

        if (request.getJugadorId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jugador requerido.");
        }

        if (request.getActividadTipo() == null || request.getActividadTipo().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Actividad requerida.");
        }

        if (request.getActividadId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Actividad requerida.");
        }

        if (request.getRespuesta() == null || request.getRespuesta().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Respuesta requerida.");
        }

        if (request.getRespuestaCorrecta() == null || request.getRespuestaCorrecta().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Respuesta correcta requerida.");
        }

        Player player = playerRepository.findById(request.getJugadorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jugador no encontrado."));

        String expected = normalize(request.getRespuestaCorrecta());
        String actual = normalize(request.getRespuesta());
        boolean esCorrecta = expected.equals(actual);
        int puntajeMaximo = resolvePuntajeMaximo(request.getPuntajeMaximo());
        int puntajeObtenido = esCorrecta ? puntajeMaximo : 0;

        String mensajeRetroalimentacion = esCorrecta
                ? "Respuesta correcta."
                : "Respuesta incorrecta. Revisa la pista y vuelve a intentarlo.";

        ResultadoValidacion resultado = new ResultadoValidacion(
                esCorrecta,
                mensajeRetroalimentacion,
                puntajeObtenido,
                esCorrecta ? null : request.getRespuestaCorrecta()
        );

        guardarIntento(player, request, esCorrecta, puntajeObtenido);

        return resultado;
    }

    private void guardarIntento(
            Player player,
            ValidacionRequest request,
            boolean esCorrecta,
            int puntajeObtenido
    ) {
        ValidacionIntento intento = new ValidacionIntento();
        intento.setPlayer(player);
        intento.setActividadTipo(request.getActividadTipo().trim());
        intento.setActividadId(request.getActividadId());
        intento.setRespuestaDada(request.getRespuesta());
        intento.setEsCorrecta(esCorrecta);
        intento.setPuntajeObtenido(puntajeObtenido);
        intento.setRespuestaCorrecta(esCorrecta ? null : request.getRespuestaCorrecta());

        validacionIntentoRepository.save(intento);
    }

    private int resolvePuntajeMaximo(Integer puntajeMaximo) {
        if (puntajeMaximo == null || puntajeMaximo <= 0) {
            return 1;
        }
        return puntajeMaximo;
    }

    private String normalize(String value) {
        String trimmed = value.trim();
        String normalizedWhitespace = trimmed.replaceAll("\\s+", " ");
        return normalizedWhitespace.toLowerCase(Locale.ROOT);
    }
}
