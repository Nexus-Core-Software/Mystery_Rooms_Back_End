package com.project.demo.logic.entity.evento;

import com.project.demo.logic.entity.team.Player;
import com.project.demo.logic.entity.team.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecompensaEventoService {
    private final RecompensaEventoRepository recompensaEventoRepository;
    private final EventoService eventoService;
    private final PlayerRepository playerRepository;

    public RecompensaEventoService(
            RecompensaEventoRepository recompensaEventoRepository,
            EventoService eventoService,
            PlayerRepository playerRepository
    ) {
        this.recompensaEventoRepository = recompensaEventoRepository;
        this.eventoService = eventoService;
        this.playerRepository = playerRepository;
    }

    @Transactional
    public RecompensaEventoResult grantRewards(Long eventoId, Long jugadorId) {
        Optional<Evento> eventoOptional = eventoService.findById(eventoId);
        if (eventoOptional.isEmpty()) {
            return RecompensaEventoResult.eventNotFound();
        }

        Evento evento = eventoOptional.get();
        if (!eventoService.isActive(evento)) {
            return RecompensaEventoResult.noActiveEvent();
        }

        Optional<Player> playerOptional = playerRepository.findById(jugadorId);
        if (playerOptional.isEmpty()) {
            return RecompensaEventoResult.playerNotFound();
        }

        List<RecompensaEvento> recompensas = recompensaEventoRepository.findByEventoId(eventoId);
        if (recompensas.isEmpty()) {
            return RecompensaEventoResult.noRewards();
        }

        Player player = playerOptional.get();
        List<RecompensaEvento> otorgadas = new ArrayList<>();

        for (RecompensaEvento recompensa : recompensas) {
            boolean alreadyGranted = recompensaEventoRepository
                    .existsByIdAndJugadoresOtorgados_Id(recompensa.getId(), jugadorId);
            if (!alreadyGranted) {
                recompensa.getJugadoresOtorgados().add(player);
                recompensaEventoRepository.save(recompensa);
                otorgadas.add(recompensa);
            }
        }

        if (otorgadas.isEmpty()) {
            return RecompensaEventoResult.alreadyGranted(recompensas);
        }

        return RecompensaEventoResult.granted(otorgadas);
    }
}
