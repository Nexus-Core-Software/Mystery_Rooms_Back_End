package com.project.mysteryRomms.security;

import com.project.mysteryRomms.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// @Configuration indica que esta clase define beans de configuración para Spring
@Configuration
public class AppConfig {

    @Autowired
    private final RepositoryUser repositoryUser;

    // Constructor para inyectar el repositorio de usuarios
    public AppConfig(RepositoryUser repositoryUser) {
        this.repositoryUser = repositoryUser;
    }

    // Bean que define cómo cargar usuarios desde la base de datos
    @Bean
    UserDetailsService userDetailsService() {
        return username -> repositoryUser.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // Bean para encriptar contraseñas con BCrypt
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean para manejar la autenticación
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Bean que define el proveedor de autenticación usando DAO
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
