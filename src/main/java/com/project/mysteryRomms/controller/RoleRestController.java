package com.project.mysteryRomms.controller;

import com.project.mysteryRomms.dto.response.Meta;
import com.project.mysteryRomms.exception.GlobalResponseHandler;
import com.project.mysteryRomms.model.entity.Role;
import com.project.mysteryRomms.repository.RoleRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// @RestController indica que esta clase maneja peticiones HTTP
@RequestMapping ("/role")
@RestController
public class RoleRestController {

    @Autowired
    private RoleRepository roleRepository; // Repositorio para acceder a los roles en la base de datos

    // Endpoint para obtener todos los roles con paginación
    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')") // Solo los SUPER_ADMIN pueden acceder
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "1") int page, // Página actual (por defecto 1)
            @RequestParam(defaultValue = "10") int size, // Tamaño de página (por defecto 10)
            HttpServletRequest request) {

        // Configuramos la paginación
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Role> rolePage = roleRepository.findAll(pageable);

        // Creamos un objeto Meta con información adicional de la respuesta
        Meta meta = new Meta(request.getMethod(), request.getRequestURL().toString());
        meta.setTotalPages(rolePage.getTotalPages());
        meta.setTotalElements(rolePage.getTotalElements());
        meta.setPageNumber(rolePage.getNumber() + 1);
        meta.setPageSize(rolePage.getSize());

        // Devolvemos los roles con un manejador de respuestas global
        return new GlobalResponseHandler().handleResponse("Roles retrieved successfully",
                rolePage.getContent(), HttpStatus.OK, meta);
    }

    // Endpoint para obtener un rol específico por su ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')") // Solo los SUPER_ADMIN pueden acceder
    public Role getRoleById(@PathVariable Integer id) {
        return roleRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("No se encontró el rol con Id " + id)
        );
    }
}
