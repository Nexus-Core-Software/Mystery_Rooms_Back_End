package com.project.mysteryRomms.controller;

import com.project.mysteryRomms.model.entity.User;
import com.project.mysteryRomms.repository.RepositoryUser;
import com.project.mysteryRomms.service.StorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Optional;

// @RestController indica que esta clase maneja peticiones HTTP
@RequestMapping("media")
@RestController
public class MediaController {
    private final StorageService storageService;
    private final HttpServletRequest request;
    private final RepositoryUser repositoryUser;

    // Constructor: recibe el servicio de almacenamiento, la request y el repositorio de usuarios
    public MediaController(StorageService storageService, HttpServletRequest request, RepositoryUser repositoryUser) {
        this.storageService = storageService;
        this.request = request;
        this.repositoryUser = repositoryUser;
    }

    // Endpoint para subir un archivo
    @PostMapping("upload")
    public Map<String, String> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        // Guardamos el archivo en el servidor
        String path = storageService.store(multipartFile);

        // Construimos la URL pública para acceder al archivo
        String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        String url = ServletUriComponentsBuilder.fromHttpUrl(host).path("/media/").path(path).toUriString();

        // Obtenemos el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<User> userOptional = repositoryUser.findByEmail(email);

        // Si el usuario existe, actualizamos su foto de perfil con la ruta del archivo
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPhotoUrl(path);
            repositoryUser.save(user);
        }

        // Devolvemos la URL del archivo como respuesta
        return Map.of("url", url);
    }

    // Endpoint para descargar un archivo por nombre
    @GetMapping("{filename: +}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws IOException {
        // Cargamos el archivo como recurso
        Resource file = storageService.loadAsResource(filename);
        String contentType = Files.probeContentType(file.getFile().toPath());

        // Lo devolvemos como respuesta HTTP
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE).body(file);
    }
}
