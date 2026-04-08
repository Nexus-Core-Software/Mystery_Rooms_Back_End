package com.project.mysteryRomms.dto.response;

// Clase genérica para respuestas HTTP.
// <T> significa que puede contener cualquier tipo de dato (User, Role, etc.)
public class HttpResponse<T> {
    // Mensaje que describe la respuesta (ejemplo: "Operación exitosa")
    private String message;

    // Datos que se devuelven (ejemplo: lista de usuarios, un rol específico, etc.)
    private T data;

    // Información adicional sobre la respuesta (paginación, URL, método, etc.)
    private Meta meta;

    // Constructores: permiten crear objetos HttpResponse con diferentes combinaciones
    public HttpResponse(String message) {
        this.message = message;
    }
    public HttpResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }
    public HttpResponse(String message, Meta meta) {
        this.message = message;
        this.meta = meta;
    }
    public HttpResponse(String message, T data, Meta meta) {
        this.message = message;
        this.data = data;
        this.meta = meta;
    }

    // Métodos getter y setter para acceder y modificar los atributos
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }
    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
