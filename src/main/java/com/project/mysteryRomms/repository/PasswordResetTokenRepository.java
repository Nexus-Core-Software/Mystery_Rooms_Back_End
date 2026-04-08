package com.project.mysteryRomms.repository;

import com.project.mysteryRomms.model.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// @Repository indica que esta interfaz es un componente de acceso a datos
@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    // Método para buscar un token específico en la base de datos
    PasswordResetToken findByToken(String token);
}
