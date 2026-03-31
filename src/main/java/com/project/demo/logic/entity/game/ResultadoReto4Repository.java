package com.project.demo.logic.entity.game;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ResultadoReto4Repository extends JpaRepository<ResultadoReto4, Long> {
    List<ResultadoReto4> findByUserId(Long userId);
    Optional<ResultadoReto4> findByPartidaIdAndUserId(Long partidaId, Long userId);
}