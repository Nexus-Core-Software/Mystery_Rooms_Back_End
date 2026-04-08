package com.project.mysteryRomms.controller;

import com.project.mysteryRomms.model.enums.UserRepository;
import com.project.mysteryRomms.repository.RoleRepository;
import com.project.mysteryRomms.security.AuthenticationService;
import com.project.mysteryRomms.security.JwtService;
import com.project.mysteryRomms.dto.request.ResetPasswordRequest;
import com.project.mysteryRomms.dto.response.LoginResponse;
import com.project.mysteryRomms.model.entity.Role;
import com.project.mysteryRomms.model.entity.User;
import com.project.mysteryRomms.model.enums.RoleEnum;
import com.project.mysteryRomms.exception.GlobalResponseHandler;
import com.project.mysteryRomms.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.project.mysteryRomms.service.EmailServiceJava;

import java.util.Optional;

// @RestController indica que esta clase maneja peticiones HTTP relacionadas con autenticación
@RequestMapping("/auth")
@RestController
public class AuthController {

    @Autowired
    private UserRepository userRepository; // Para acceder a los usuarios en la base de datos

    @Autowired
    private PasswordEncoder passwordEncoder; // Para codificar contraseñas

    @Autowired
    private RoleRepository roleRepository; // Para acceder a los roles

    @Autowired
    private UserService userService; // Lógica de negocio para usuarios

    @Autowired
    private EmailServiceJava emailService; // Servicio para enviar correos

    @Autowired
    private AuthenticationService authenticationService; // Servicio de autenticación

    private final JwtService jwtService; // Servicio para generar tokens JWT

    // Constructor: inicializa JwtService y AuthenticationService
    public AuthController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    // LOGIN: autenticar un usuario
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody User user, HttpServletRequest request) {
        Optional<User> foundedUser = userRepository.findByEmail(user.getEmail());

        if (foundedUser.isEmpty()) {
            return new GlobalResponseHandler().handleResponse("No se ha encontrado el usuario",
                    HttpStatus.UNAUTHORIZED, request);
        }

        User authenticatedUser = foundedUser.get();

        // Si el usuario está deshabilitado, no puede entrar
        if (!authenticatedUser.isEnabled()) {
            return new GlobalResponseHandler().handleResponse("Usuario deshabilitado",
                    HttpStatus.FORBIDDEN, request);
        }

        // Autenticamos al usuario
        authenticatedUser = authenticationService.authenticate(user);

        // Generamos un token JWT para que el usuario pueda usar el sistema
        String jwtToken = jwtService.generateToken(authenticatedUser);

        // Creamos la respuesta con el token y datos del usuario
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setAuthUser(authenticatedUser);

        return ResponseEntity.ok(loginResponse);
    }

    // SIGNUP: registrar un nuevo usuario
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
        }

        // Codificamos la contraseña
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Role not found");
        }
        user.setRole(optionalRole.get());
        user.setEnabled(true);
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    // FORGOT PASSWORD: enviar correo con link de recuperación
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody User user) {
        String token = userService.createPasswordResetToken(user);
        if (token == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        String resetLink = "Enter in this link to reset your password: " + "http://localhost:4200/reset-password"
                + " Your token is " + token;
        emailService.sendEmail(user.getEmail(), "Password Reset Request",
                "To reset your password, click the link below:\n" + resetLink);
        return ResponseEntity.ok("Password reset link sent to your email");
    }

    // RESET PASSWORD: cambiar la contraseña usando el token
    @PutMapping("/reset-password/{token}")
    public ResponseEntity<?> resetPassword(@PathVariable String token, @RequestBody ResetPasswordRequest request) {
        boolean result = userService.resetPassword(token, request.getNewPassword());
        if (!result) {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }
        return ResponseEntity.ok("Password reset successfully");
    }
}
