package com.project.mysteryRomms.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

// Interfaz que define las operaciones básicas de almacenamiento
public interface StorageService {
    // Inicializa el sistema de almacenamiento (ej. crear directorios)
    void init() throws IOException;

    // Guarda un archivo en el sistema y devuelve su nombre
    String store(MultipartFile file);

    // Recupera un archivo como recurso (para descargar o mostrar)
    Resource loadAsResource(String filename);
}
