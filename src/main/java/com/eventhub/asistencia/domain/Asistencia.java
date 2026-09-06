package com.eventhub.asistencia.domain;

import java.time.OffsetDateTime;

public class Asistencia {
    private Long asistenciaId;
    private Long participanteId;
    private Long sesionEventoId;
    private OffsetDateTime fechaRegistro;
    private String estado;
    private Long usuarioId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public Asistencia() {
    }

    public Asistencia(Long asistenciaId, Long participanteId, Long sesionEventoId, OffsetDateTime fechaRegistro, String estado, Long usuarioId, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.asistenciaId = asistenciaId;
        this.participanteId = participanteId;
        this.sesionEventoId = sesionEventoId;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.usuarioId = usuarioId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getAsistenciaId() {
        return asistenciaId;
    }

    public void setAsistenciaId(Long asistenciaId) {
        this.asistenciaId = asistenciaId;
    }

    public Long getParticipanteId() {
        return participanteId;
    }

    public void setParticipanteId(Long participanteId) {
        this.participanteId = participanteId;
    }

    public Long getSesionEventoId() {
        return sesionEventoId;
    }

    public void setSesionEventoId(Long sesionEventoId) {
        this.sesionEventoId = sesionEventoId;
    }

    public OffsetDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(OffsetDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
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

