package com.eventhub.sede.domain;

import java.time.OffsetDateTime;

public class Sede {
    private Long sedeId;
    private String nombre;
    private String direccion;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public Sede() {
    }

    public Sede(Long sedeId, String nombre, String direccion, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.sedeId = sedeId;
        this.nombre = nombre;
        this.direccion = direccion;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getSedeId() {
        return sedeId;
    }

    public void setSedeId(Long sedeId) {
        this.sedeId = sedeId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
