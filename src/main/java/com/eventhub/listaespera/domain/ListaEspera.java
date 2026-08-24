package com.eventhub.listaespera.domain;

import java.time.OffsetDateTime;

public class ListaEspera {
    private Long listaEsperaId;
    private Long participanteId;
    private Long eventoId;
    private OffsetDateTime fechaIngreso;
    private Integer posicion;
    private String estado;
    private Long usuarioId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public Long getListaEsperaId() {
        return listaEsperaId;
    }

    public void setListaEsperaId(Long listaEsperaId) {
        this.listaEsperaId = listaEsperaId;
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

    public OffsetDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(OffsetDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
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
