package com.project.demo.rest.validacion;

import com.project.demo.logic.entity.http.GlobalResponseHandler;
import com.project.demo.logic.entity.validacion.ValidacionService;
import com.project.demo.rest.validacion.dto.ResultadoValidacion;
import com.project.demo.rest.validacion.dto.ValidacionRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/validacion", "/api/validacion"})
public class ValidacionRestController {
    private final ValidacionService validacionService;

    public ValidacionRestController(ValidacionService validacionService) {
        this.validacionService = validacionService;
    }

    @PostMapping("/respuesta")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> validarRespuesta(
            @RequestBody ValidacionRequest request,
            HttpServletRequest httpRequest
    ) {
        ResultadoValidacion resultado = validacionService.validarRespuesta(request);
        return new GlobalResponseHandler().handleResponse(
                "Validacion procesada.",
                resultado,
                HttpStatus.OK,
                httpRequest
        );
    }
}
