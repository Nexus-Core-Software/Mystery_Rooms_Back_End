package com.project.mysteryRomms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

// @Configuration indica que esta clase define configuraciones especiales para la aplicación
@Configuration
public class EmailConfig {

    // Este método crea y configura el "enviador de correos"
    @Bean
    public JavaMailSender javaMailSender() {
        // Creamos un objeto que sabe cómo enviar correos usando Gmail
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com"); // Servidor de Gmail
        mailSender.setPort(587);              // Puerto para enviar correos con seguridad
        mailSender.setUsername("nexuscoresoftware@gmail.com"); // Usuario (correo)
        mailSender.setPassword("uhfmsjwkxzviridi");            // Contraseña o clave de aplicación

        // Configuramos propiedades adicionales para que funcione bien
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp"); // Protocolo de envío
        props.put("mail.smtp.auth", "true");          // Requiere autenticación (usuario/contraseña)
        props.put("mail.smtp.starttls.enable", "true"); // Activa seguridad (TLS)
        props.put("mail.debug", "true");              // Muestra mensajes de depuración

        // Devolvemos el objeto listo para enviar correos
        return mailSender;
    }
}
