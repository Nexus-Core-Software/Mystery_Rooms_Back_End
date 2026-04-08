package com.project.mysteryRomms.model.entity;

import jakarta.persistence.*;
import java.util.Calendar;
import java.util.Date;

// @Entity indica que esta clase será una tabla en la base de datos
@Entity
public class PasswordResetToken {

    // Tiempo de expiración del token en minutos (60 * 24 = 24 horas)
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autogenerado
    private Long id;

    // El valor único del token
    private String token;

    // Relación uno a uno con el usuario que pidió el reset
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    // Fecha de expiración del token
    private Date expiryDate;

    // Constructor vacío (necesario para JPA)
    public PasswordResetToken() {
    }

    // Constructor que inicializa token, usuario y calcula fecha de expiración
    public PasswordResetToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    // Método privado para calcular la fecha de expiración
    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    // Método para verificar si el token ya expiró
    public boolean isExpired() {
        return new Date().after(this.expiryDate);
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }
}
