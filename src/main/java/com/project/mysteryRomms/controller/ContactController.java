package com.project.mysteryRomms.controller;

import com.project.mysteryRomms.model.entity.ContactForm;
import com.project.mysteryRomms.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


// @RestController indica que esta clase maneja peticiones HTTP
@RequestMapping("/contact")
@RestController
public class ContactController {

    // Endpoint de prueba para verificar que el controlador funciona
    @GetMapping
    public String contactTest() {
        return "Contact Test";
    }

    // Inyectamos el servicio de correos
    @Autowired
    private EmailService emailService;

    // Endpoint para enviar un correo desde el formulario de contacto
    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(@RequestBody ContactForm contactForm) throws MessagingException {
        // Mensaje de soporte técnico
        String message = "Technical support requested from = " + contactForm.getEmail();
        emailService.sendEmail(contactForm);

        // Confirmación al usuario
        String confirmation = "Confimation email sent to: " + contactForm.getEmail();
        emailService.sendConfirmationEmail(contactForm);

        // Devolvemos el formulario como respuesta
        return ResponseEntity.ok(contactForm);
    }
}
