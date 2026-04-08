package com.project.mysteryRomms.exception;

import com.project.mysteryRomms.dto.response.HttpResponse;
import com.project.mysteryRomms.dto.response.Meta;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// @RestControllerAdvice indica que esta clase aplica a todos los controladores
@RestControllerAdvice
public class GlobalResponseHandler {

    // Método para manejar respuestas con mensaje, datos, estado y request
    @ResponseBody
    public <T> ResponseEntity<?> handleResponse(String message, T body, HttpStatus status, HttpServletRequest request) {
        Meta meta = new Meta(request.getMethod(), request.getRequestURL().toString());
        if (body instanceof HttpResponse) {
            HttpResponse<?> response = (HttpResponse<?>) body;
            response.setMeta(meta);
            return new ResponseEntity<>(response, status);
        }
        HttpResponse<T> response = new HttpResponse<>(message, body, meta);
        return new ResponseEntity<>(response, status);
    }

    // Método para manejar respuestas con solo mensaje y estado
    @ResponseBody
    public <T> ResponseEntity<?> handleResponse(String message, HttpStatus status, HttpServletRequest request) {
        Meta meta = new Meta(request.getMethod(), request.getRequestURL().toString());
        HttpResponse<?> response = new HttpResponse<>(message, meta);
        return new ResponseEntity<>(response, status);
    }

    // Método para manejar respuestas con mensaje, datos, estado y meta personalizada
    @ResponseBody
    public <T> ResponseEntity<?> handleResponse(String message, T body, HttpStatus status, Meta meta) {
        if (body instanceof HttpResponse) {
            HttpResponse<?> response = (HttpResponse<?>) body;
            response.setMeta(meta);
            return new ResponseEntity<>(response, status);
        }
        HttpResponse<T> response = new HttpResponse<>(message, body, meta);
        return new ResponseEntity<>(response, status);
    }
}
