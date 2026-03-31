package com.project.demo.rest.game;

import com.project.demo.logic.entity.game.Reto4service;
import com.project.demo.rest.game.dto.Reto4RequestDTO;
import com.project.demo.rest.game.dto.Reto4ResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game/reto4")
// Sin @CrossOrigin — CorsConfig.java ya lo maneja globalmente
public class Reto4controller {

    @Autowired
    private Reto4service reto4Service;

    /**
     * POST /api/game/reto4/resultado/{userId}
     *
     * Guarda el resultado del Reto 4 para un usuario.
     * Angular llama este endpoint cuando el jugador termina el puzzle.
     *
     * Body:
     * {
     *   "partidaId":        1,
     *   "puntajeObtenido":  760,
     *   "intentosFallidos": 3,
     *   "completado":       true,
     *   "tiempoSegundos":   1423
     * }
     */
    @PostMapping("/resultado/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Reto4ResponseDTO> guardarResultado(
            @PathVariable Long userId,
            @Valid @RequestBody Reto4RequestDTO request) {

        Reto4ResponseDTO response = reto4Service.guardarResultado(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/game/reto4/resultados/{userId}
     *
     * Devuelve el historial de resultados del Reto 4 de un usuario.
     * Útil para el panel de estadísticas.
     */
    @GetMapping("/resultados/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Reto4ResponseDTO>> obtenerResultados(
            @PathVariable Long userId) {

        return ResponseEntity.ok(reto4Service.obtenerResultadosPorUsuario(userId));
    }

    /**
     * GET /api/game/reto4/health
     * Público — para verificar que el endpoint responde sin necesitar token.
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Reto4Controller OK");
    }
}