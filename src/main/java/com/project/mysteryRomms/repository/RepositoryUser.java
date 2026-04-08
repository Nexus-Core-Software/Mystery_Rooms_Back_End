package com.project.mysteryRomms.repository;

import com.project.mysteryRomms.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

// Interfaz que maneja el acceso a datos de la entidad User
public interface RepositoryUser extends JpaRepository<User, Long>  {

    // Buscar usuarios cuyo nombre contenga ciertos caracteres (ignorando mayúsculas/minúsculas)
    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE %?1%")
    List<User> findUsersWithCharacterInName(String character);

    // Buscar usuario por nombre exacto
    @Query("SELECT u FROM User u WHERE u.name = ?1")
    Optional<User> findByName(String name);

    // Buscar usuario por ID
    @Query("SELECT u FROM User u WHERE u.id = ?1")
    Optional<User> findById(Long id);

    // Buscar usuario por apellido
    Optional<User> findByLastname(String lastname);

    // Buscar usuario por email
    Optional<User> findByEmail(String email);

    // Actualizar el estado "enabled" de un usuario por ID
    @Query("UPDATE User u SET u.enabled = :enabled WHERE u.id = :id")
    void setUserEnabled(@Param("id") Long id, @Param("enabled") boolean enabled);
}
