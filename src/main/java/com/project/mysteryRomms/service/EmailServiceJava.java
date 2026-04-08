package com.project.mysteryRomms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

// @Service indica que esta clase es un componente de servicio gestionado por Spring
@Service
public class EmailServiceJava {

    @Autowired
    private JavaMailSender mailSender;

    // Método para enviar un correo simple (texto plano)
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to); // destinatario
        message.setSubject(subject); // asunto
        message.setText(text); // cuerpo del mensaje
        message.setFrom("nexuscoresoftware@gmail.com"); // remitente fijo
        mailSender.send(message); // enviar correo
    }
}
