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

@RestController
@RequestMapping ("/role")
public class RoleRestController {
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        Pageable pageable = PageRequest.of(page-1, size);
        Page<Role> rolePage = roleRepository.findAll(pageable);
        Meta meta = new Meta(request.getMethod(), request.getRequestURL().toString()); // Para los get all
        meta.setTotalPages(rolePage.getTotalPages());
        meta.setTotalElements(rolePage.getTotalElements());
        meta.setPageNumber(rolePage.getNumber() + 1);
        meta.setPageSize(rolePage.getSize());

        return new GlobalResponseHandler().handleResponse("Roles retrieved successfully",
                rolePage.getContent(), HttpStatus.OK, meta);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public Role getRoleById(@PathVariable Integer id) {
        return roleRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("No se encontró el rol con Id" + id)
        );
    }
}
