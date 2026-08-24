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

    public ListaEspera() {
    }

    public ListaEspera(Long listaEsperaId, Long participanteId, Long eventoId, OffsetDateTime fechaIngreso, Integer posicion, String estado, Long usuarioId, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.listaEsperaId = listaEsperaId;
        this.participanteId = participanteId;
        this.eventoId = eventoId;
        this.fechaIngreso = fechaIngreso;
        this.posicion = posicion;
        this.estado = estado;
        this.usuarioId = usuarioId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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
