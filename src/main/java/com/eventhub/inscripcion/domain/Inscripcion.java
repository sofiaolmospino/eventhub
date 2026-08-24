package com.eventhub.inscripcion.domain;

import java.time.OffsetDateTime;

public class Inscripcion {
    private Long inscripcionId;
    private Long participanteId;
    private Long eventoId;
    private OffsetDateTime fechaInscripcion;
    private String estado;
    private Long usuarioId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public Inscripcion() {
    }

    public Inscripcion(Long inscripcionId, Long participanteId, Long eventoId, OffsetDateTime fechaInscripcion, String estado, Long usuarioId, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.inscripcionId = inscripcionId;
        this.participanteId = participanteId;
        this.eventoId = eventoId;
        this.fechaInscripcion = fechaInscripcion;
        this.estado = estado;
        this.usuarioId = usuarioId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getInscripcionId() {
        return inscripcionId;
    }

    public void setInscripcionId(Long inscripcionId) {
        this.inscripcionId = inscripcionId;
    }

    public Long getParticipanteId() {
        return participanteId;
    }

    public void setParticipanteId(Long participanteId) {
        this.participanteId = participanteId;
    }

    public Long getEventoId() {
        return eventoId;
    }

    public void setEventoId(Long eventoId) {
        this.eventoId = eventoId;
    }

    public OffsetDateTime getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(OffsetDateTime fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
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
