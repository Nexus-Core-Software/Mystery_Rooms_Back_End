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


@RestController
@RequestMapping("/contact")
public class ContactController {
    @GetMapping
    public String contactTest() {
        return "Contact Test";
    }


    @Autowired
    private EmailService emailService;


    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(@RequestBody ContactForm contactForm) throws MessagingException {
        String message = "Technical support requested from = " + contactForm.getEmail();
        emailService.sendEmail(contactForm);

        String confirmation = "Confimation email sent to: " + contactForm.getEmail();
        emailService.sendConfirmationEmail(contactForm);

        return ResponseEntity.ok(contactForm);
    }
}
