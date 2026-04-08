package com.project.mysteryRomms.service;

import com.project.mysteryRomms.model.entity.ContactForm;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String innovaCode;

    // Enviar el correo al equipo de soporte
    public void sendEmail(@RequestBody ContactForm contactForm) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject(contactForm.getSubject());

        String html =
                "<!doctype html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head><meta charset=\"UTF-8\"><title>Email</title></head>\n" +
                        "<body>\n" +
                        "<div><h1>Technical Support Inquiry</h1></div>\n" +
                        "<div>Sender: <b>" + contactForm.getName() + "</b></div>\n" +
                        "<div>Email: <b>" + contactForm.getEmail() + "</b></div>\n" +
                        "<div>Message: <b>" + contactForm.getMessage() + "</b></div>\n" +
                        "</body>\n</html>";

        helper.setText(html, true);
        helper.setTo(innovaCode); // se envía al correo configurado en la app
        helper.setFrom(contactForm.getEmail()); // remitente es el usuario
        mailSender.send(mimeMessage);
    }

    // Enviar correo de confirmación al usuario
    public void sendConfirmationEmail(@RequestBody ContactForm contactForm) throws MessagingException {
        MimeMessage confirmation = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(confirmation);
        helper.setSubject(contactForm.getSubject());

        String html =
                "<!doctype html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head><meta charset=\"UTF-8\"><title>Confirmation email</title></head>\n" +
                        "<body>\n" +
                        "<div><h1>Hi " + contactForm.getName() + "!</h1></div>\n" +
                        "<div>We received your email and will be in contact with you in the next 24 hours!</div>\n" +
                        "<div>Best,</div>\n" +
                        "<div>Innovacode</div>\n" +
                        "</body>\n</html>";

        helper.setText(html, true);
        helper.setTo(contactForm.getEmail()); // se envía al usuario
        helper.setFrom(innovaCode); // remitente es el correo de la empresa
        mailSender.send(confirmation);
    }
}
