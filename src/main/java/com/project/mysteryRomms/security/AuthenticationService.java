package com.project.mysteryRomms.security;

import com.project.mysteryRomms.model.entity.User;
import com.project.mysteryRomms.model.enums.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;

// @Service indica que esta clase es un componente de servicio dentro de Spring
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private OAuth2AuthorizedClientService authorizedClientService;
    private final AuthenticationManager authenticationManager;

    // Constructor con inyección de dependencias
    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Método para autenticar un usuario
    public User authenticate(User input) {
        // Se valida el usuario con email y contraseña
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        // Si la autenticación es correcta, se busca el usuario en la BD
        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
