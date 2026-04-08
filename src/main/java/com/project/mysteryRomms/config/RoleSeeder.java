package com.project.mysteryRomms.config;

import com.project.mysteryRomms.model.entity.Role;
import com.project.mysteryRomms.model.enums.RoleEnum;
import com.project.mysteryRomms.repository.RoleRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

// @Component indica que esta clase se ejecuta automáticamente cuando arranca la aplicación
@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;

    // Constructor: recibe el repositorio de roles para poder trabajar con la base de datos
    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Este método se ejecuta cuando la aplicación arranca
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles(); // Llamamos al método que carga los roles
    }

    // Método que asegura que los roles básicos existan en la base de datos
    private void loadRoles() {
        // Lista de roles que queremos asegurar que existan
        RoleEnum[] roleNames = new RoleEnum[] { RoleEnum.USER, RoleEnum.SUPER_ADMIN };

        // Descripciones para cada rol
        Map<RoleEnum, String> roleDescriptionMap = Map.of(
                RoleEnum.USER, "Default user role", // Rol normal para cualquier usuario
                RoleEnum.SUPER_ADMIN, "Super Administrator role" // Rol especial con todos los permisos
        );

        // Recorremos cada rol y verificamos si ya existe en la base de datos
        Arrays.stream(roleNames).forEach((roleName) -> {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);

            // Si el rol existe, lo mostramos en consola
            optionalRole.ifPresentOrElse(System.out::println, () -> {
                // Si no existe, lo creamos y lo guardamos
                Role roleToCreate = new Role();
                roleToCreate.setName(roleName);
                roleToCreate.setDescription(roleDescriptionMap.get(roleName));
                roleRepository.save(roleToCreate);
            });
        });
    }
}
