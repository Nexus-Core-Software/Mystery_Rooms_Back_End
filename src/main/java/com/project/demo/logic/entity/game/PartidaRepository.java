package com.project.demo.logic.entity.game;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PartidaRepository extends JpaRepository<Partida, Long> {
    List<Partida> findByGameId(Long gameId);
    List<Partida> findByEstado(String estado);
}