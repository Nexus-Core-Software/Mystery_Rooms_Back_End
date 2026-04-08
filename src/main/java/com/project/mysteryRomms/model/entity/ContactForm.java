package com.project.mysteryRomms.model.entity;

// Clase que representa un formulario de contacto enviado por un usuario
public class ContactForm {
    // Nombre del usuario que envía el mensaje
    private String name;

    // Correo electrónico del usuario
    private String email;

    // Asunto del mensaje
    private String subject;

    // Contenido del mensaje
    private String message;

    // Constructor para inicializar todos los campos
    public ContactForm(String name, String email, String subject, String message) {
        this.name = name;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    // Métodos getter y setter para acceder y modificar los atributos
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
