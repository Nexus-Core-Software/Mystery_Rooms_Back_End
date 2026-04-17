package com.project.demo.logic.entity.evento;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EventoService {
    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public Optional<Evento> findById(Long eventoId) {
        return eventoRepository.findById(eventoId);
    }

    public Optional<Evento> findActiveEvent() {
        LocalDateTime now = LocalDateTime.now();
        return eventoRepository.findFirstByActivoTrueAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(now, now);
    }

    public boolean isActive(Evento evento) {
        if (evento == null) {
            return false;
        }

        if (!Boolean.TRUE.equals(evento.getActivo())) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = evento.getFechaInicio();
        LocalDateTime end = evento.getFechaFin();

        if (start != null && now.isBefore(start)) {
            return false;
        }

        if (end != null && now.isAfter(end)) {
            return false;
        }

        return true;
    }
}
