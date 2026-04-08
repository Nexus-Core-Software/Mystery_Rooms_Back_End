package com.project.mysteryRomms.dto.response;

// Clase Meta: contiene información adicional sobre la respuesta HTTP
public class Meta {
    // Método HTTP usado (GET, POST, PUT, DELETE)
    private String method;

    // URL de la petición
    private String url;

    // Número total de páginas (cuando se usa paginación)
    private int totalPages;

    // Número total de elementos (ejemplo: total de usuarios)
    private long totalElements;

    // Número de la página actual
    private int pageNumber;

    // Tamaño de la página (cuántos elementos por página)
    private int pageSize;

    // Constructor: inicializa con método y URL
    public Meta(String method, String url) {
        this.method = method;
        this.url = url;
    }

    // Getters y setters para acceder y modificar los atributos
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public int getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }
    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getPageNumber() {
        return pageNumber;
    }
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
