package com.project.mysteryRomms.controller;

import com.project.mysteryRomms.model.entity.Role;
import com.project.mysteryRomms.model.entity.User;
import com.project.mysteryRomms.model.enums.RoleEnum;
import com.project.mysteryRomms.repository.RoleRepository;
import com.project.mysteryRomms.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

// @RestController indica que esta clase maneja peticiones HTTP (como un "puente" entre el usuario y el servidor)
@RequestMapping("/admin")
@RestController
public class AdminController {

    // Inyectamos los repositorios y el codificador de contraseñas
    @Autowired
    private RepositoryUser repositoryUser;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Método para crear un nuevo administrador
    // @PostMapping indica que se accede con una petición POST a /admin
    // @PreAuthorize asegura que solo alguien con rol SUPER_ADMIN puede usar este método
    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public User createAdministrator(@RequestBody User newAdminUser) {
        // Buscamos si existe el rol SUPER_ADMIN
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);

        // Si no existe el rol, no hacemos nada
        if (optionalRole.isEmpty()) {
            return null;
        }

        // Creamos un nuevo usuario administrador con los datos recibidos
        var user = new User();
        user.setName(newAdminUser.getName());
        user.setEmail(newAdminUser.getEmail());
        // Codificamos la contraseña para que no quede en texto plano
        user.setPassword(passwordEncoder.encode(newAdminUser.getPassword()));
        // Le asignamos el rol de SUPER_ADMIN
        user.setRole(optionalRole.get());

        // Guardamos el nuevo administrador en la base de datos y lo devolvemos
        return repositoryUser.save(user);
    }
}
