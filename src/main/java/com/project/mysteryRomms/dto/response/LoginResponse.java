package com.project.mysteryRomms.dto.response;

import com.project.mysteryRomms.model.entity.User;

// Clase que representa la respuesta al hacer login
public class LoginResponse {
    // Token JWT que se genera al autenticarse
    private String token;

    // Usuario autenticado (con sus datos)
    private User authUser;

    // Tiempo en milisegundos que el token será válido
    private long expiresIn;

    // Métodos getter y setter para acceder y modificar los atributos
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }
    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public User getAuthUser() {
        return authUser;
    }
    public void setAuthUser(User authUser) {
        this.authUser = authUser;
    }
}
