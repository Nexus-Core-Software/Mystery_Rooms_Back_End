package com.project.demo.logic.entity.game;

import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import com.project.demo.rest.game.dto.Reto4RequestDTO;
import com.project.demo.rest.game.dto.Reto4ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Reto4service {

    @Autowired
    private ResultadoReto4Repository resultadoRepo;

    @Autowired
    private PartidaRepository partidaRepo;

    @Autowired
    private UserRepository userRepository;

    /**
     * Guarda o actualiza el resultado del Reto 4.
     * Si el usuario ya tiene resultado para esa partida, lo sobreescribe (upsert).
     */
    @Transactional
    public Reto4ResponseDTO guardarResultado(Long userId, Reto4RequestDTO request) {

        // Validar que la partida existe
        Partida partida = partidaRepo.findById(request.getPartidaId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Partida no encontrada con id: " + request.getPartidaId()
                ));

        // Validar que el usuario existe
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario no encontrado con id: " + userId
                ));

        // Upsert: si ya existe un resultado para partida+usuario lo actualiza,
        // si no existe crea uno nuevo
        ResultadoReto4 resultado = resultadoRepo
                .findByPartidaIdAndUserId(request.getPartidaId(), userId)
                .orElse(new ResultadoReto4());

        resultado.setPartida(partida);
        resultado.setUser(user);
        resultado.setPuntajeObtenido(request.getPuntajeObtenido());
        resultado.setIntentosFallidos(request.getIntentosFallidos());
        resultado.setCompletado(request.getCompletado());
        resultado.setTiempoSegundos(request.getTiempoSegundos());

        ResultadoReto4 saved = resultadoRepo.save(resultado);

        return toDTO(saved);
    }

    /**
     * Devuelve el historial de resultados del Reto 4 de un usuario.
     */
    public List<Reto4ResponseDTO> obtenerResultadosPorUsuario(Long userId) {
        return resultadoRepo.findByUserId(userId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ── Helper: convierte entidad → DTO ──────────────────

    private Reto4ResponseDTO toDTO(ResultadoReto4 r) {
        return new Reto4ResponseDTO(
                r.getId(),
                r.getPartida().getId(),
                r.getUser().getId(),
                r.getUser().getName(),      // nombre del User real
                r.getUser().getEmail(),     // email del User real
                r.getPuntajeObtenido(),
                r.getIntentosFallidos(),
                r.getCompletado(),
                r.getTiempoSegundos(),
                r.getCreatedAt()
        );
    }
}