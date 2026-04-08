package com.project.mysteryRomms.model.entity;

import com.project.mysteryRomms.model.enums.RoleEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

// @Entity indica que esta clase será una tabla en la base de datos
@Table(name = "rol") // Nombre de la tabla en la BD
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // ID autogenerado
    @Column(nullable = false)
    private Integer id;

    // Nombre del rol (ejemplo: USER, SUPER_ADMIN)
    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING) // Se guarda como texto en la BD
    private RoleEnum name;

    // Descripción del rol
    @Column(nullable = false)
    private String description;

    // Fecha de creación (se asigna automáticamente)
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    // Fecha de última actualización (se asigna automáticamente)
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public RoleEnum getName() { return name; }
    public void setName(RoleEnum name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
