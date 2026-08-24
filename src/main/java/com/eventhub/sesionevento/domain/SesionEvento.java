package com.eventhub.sesionevento.domain;

import java.time.OffsetDateTime;

public class SesionEvento {
    private Long sesionEventoId;
    private Long eventoId;
    private OffsetDateTime fechaInicio;
    private OffsetDateTime fechaFin;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

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
