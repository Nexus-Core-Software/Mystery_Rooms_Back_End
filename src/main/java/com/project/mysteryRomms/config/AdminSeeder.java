package com.project.mysteryRomms.config;

// Importamos las clases necesarias para trabajar con usuarios, roles y seguridad
import com.project.mysteryRomms.model.entity.Role;
import com.project.mysteryRomms.model.entity.User;
import com.project.mysteryRomms.model.enums.RoleEnum;
import com.project.mysteryRomms.repository.RoleRepository;
import com.project.mysteryRomms.repository.RepositoryUser;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

// @Component indica que esta clase es un "componente" de Spring, o sea, que se ejecuta automáticamente
@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
    // Aquí guardamos referencias a los repositorios y al codificador de contraseñas
    private final RoleRepository roleRepository;
    private final RepositoryUser repositoryUser;
    private final PasswordEncoder passwordEncoder;

    // Constructor: recibe los repositorios y el codificador para poder usarlos
    public AdminSeeder(
            RoleRepository roleRepository,
            RepositoryUser repositoryUser,
            PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.repositoryUser = repositoryUser;
        this.passwordEncoder = passwordEncoder;
    }

    // Este método se ejecuta automáticamente cuando la aplicación arranca
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createSuperAdministrator(); // Llamamos al método que crea el super administrador
    }

    // Método que crea un usuario especial llamado "Super Admin"
    private void createSuperAdministrator() {
        // Creamos un objeto usuario con datos básicos
        User superAdmin = new User();
        superAdmin.setAddress("Heredia, San Francisco");
        superAdmin.setName("Super");
        superAdmin.setLastname("Admin");
        superAdmin.setEmail("super.admin@gmail.com");
        superAdmin.setPassword("superadmin123"); // Contraseña sin codificar (se codifica más adelante)
        superAdmin.setEnabled(true); // El usuario está activo
        superAdmin.setPhoneNumber("85841515");
        superAdmin.setPhotoUrl("");

        // Buscamos si ya existe el rol SUPER_ADMIN y si ya existe un usuario con ese correo
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);
        Optional<User> optionalUser = repositoryUser.findByEmail(superAdmin.getEmail());

        // Si el rol no existe o el usuario ya está creado, no hacemos nada
        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        // Si el rol existe y el usuario no está creado, lo guardamos en la base de datos
        var user = new User();
        user.setName(superAdmin.getName());
        user.setLastname(superAdmin.getLastname());
        user.setEmail(superAdmin.getEmail());
        // Codificamos la contraseña para que no quede en texto plano
        user.setPassword(passwordEncoder.encode(superAdmin.getPassword()));
        // Le asignamos el rol de SUPER_ADMIN
        user.setRole(optionalRole.get());
        // Guardamos el usuario en la base de datos
        repositoryUser.save(user);
    }
}
