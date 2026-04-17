package com.project.demo.rest.evento;

import com.project.demo.logic.entity.evento.Evento;
import com.project.demo.logic.entity.evento.EventoService;
import com.project.demo.logic.entity.http.GlobalResponseHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping({"/eventos", "/api/eventos"})
public class EventoRestController {
    private final EventoService eventoService;

    public EventoRestController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping("/activo")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getActiveEvent(HttpServletRequest request) {
        Optional<Evento> activeEvent = eventoService.findActiveEvent();
        if (activeEvent.isPresent()) {
            return new GlobalResponseHandler().handleResponse(
                    "Evento activo encontrado",
                    activeEvent.get(),
                    HttpStatus.OK,
                    request
            );
        }

        return new GlobalResponseHandler().handleResponse(
                "No hay un evento activo.",
                HttpStatus.NOT_FOUND,
                request
        );
    }
}
