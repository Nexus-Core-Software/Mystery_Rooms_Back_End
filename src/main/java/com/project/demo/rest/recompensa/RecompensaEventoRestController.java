package com.project.demo.rest.recompensa;

import com.project.demo.logic.entity.evento.RecompensaEventoResult;
import com.project.demo.logic.entity.evento.RecompensaEventoService;
import com.project.demo.logic.entity.http.GlobalResponseHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/recompensas", "/api/recompensas"})
public class RecompensaEventoRestController {
    private final RecompensaEventoService recompensaEventoService;

    public RecompensaEventoRestController(RecompensaEventoService recompensaEventoService) {
        this.recompensaEventoService = recompensaEventoService;
    }

    @PostMapping("/evento/{eventoId}/otorgar/{jugadorId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> grantEventReward(
            @PathVariable Long eventoId,
            @PathVariable Long jugadorId,
            HttpServletRequest request
    ) {
        RecompensaEventoResult result = recompensaEventoService.grantRewards(eventoId, jugadorId);
        return new GlobalResponseHandler().handleResponse(
                result.getMessage(),
                result.getResponse(),
                result.getStatus(),
                request
        );
    }
}
