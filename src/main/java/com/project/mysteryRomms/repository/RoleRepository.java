package com.project.mysteryRomms.repository;

import com.project.mysteryRomms.model.entity.Role;
import com.project.mysteryRomms.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// @Repository indica que esta interfaz es un componente de acceso a datos
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    // Método para buscar un rol por su nombre (ejemplo: USER, SUPER_ADMIN)
    Optional<Role> findByName(RoleEnum name);
}
