package com.eventhub.sesionevento.domain;

import java.time.OffsetDateTime;

public class SesionEvento {
    private Long sesionEventoId;
    private Long eventoId;
    private OffsetDateTime fechaInicio;
    private OffsetDateTime fechaFin;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public SesionEvento() {
    }

    public SesionEvento(Long sesionEventoId, Long eventoId, OffsetDateTime fechaInicio, OffsetDateTime fechaFin, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.sesionEventoId = sesionEventoId;
        this.eventoId = eventoId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getSesionEventoId() {
        return sesionEventoId;
    }

    public void setSesionEventoId(Long sesionEventoId) {
        this.sesionEventoId = sesionEventoId;
    }

    public Long getEventoId() {
        return eventoId;
    }

    public void setEventoId(Long eventoId) {
        this.eventoId = eventoId;
    }

    public OffsetDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(OffsetDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public OffsetDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(OffsetDateTime fechaFin) {
        this.fechaFin = fechaFin;
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
