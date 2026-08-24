package com.eventhub.participante.domain;

import java.time.OffsetDateTime;

public class Participante {
    private Long participanteId;
    private String nombre;
    private String apellido;
    private String correo;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public Participante() {
    }

    public Participante(Long participanteId, String nombre, String apellido, String correo, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.participanteId = participanteId;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getParticipanteId() {
        return participanteId;
    }

    public void setParticipanteId(Long participanteId) {
        this.participanteId = participanteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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
