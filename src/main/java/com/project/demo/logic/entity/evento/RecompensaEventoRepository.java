package com.project.demo.logic.entity.evento;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecompensaEventoRepository extends JpaRepository<RecompensaEvento, Long> {
    List<RecompensaEvento> findByEventoId(Long eventoId);

    boolean existsByIdAndJugadoresOtorgados_Id(Long recompensaId, Long jugadorId);
}
