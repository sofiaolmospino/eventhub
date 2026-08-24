package com.eventhub.evento.domain;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.eventhub.sesionevento.domain.SesionEvento;

public class Evento {
    private Long eventoId;
    private String nombre;
    private String descripcion;
    private String responsable;
    private String modalidad;
    private OffsetDateTime fechaInicio;
    private OffsetDateTime fechaFin;
    private Integer capacidad;
    private String estado;
    private Long usuarioId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private final List<SesionEvento> sesiones = new ArrayList<>();

    public Evento() {
    }

    public Evento(Long eventoId, String nombre, String descripcion, String responsable, String modalidad, OffsetDateTime fechaInicio, OffsetDateTime fechaFin, Integer capacidad, String estado, Long usuarioId, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.eventoId = eventoId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.responsable = responsable;
        this.modalidad = modalidad;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.capacidad = capacidad;
        this.estado = estado;
        this.usuarioId = usuarioId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getEventoId() {
        return eventoId;
    }

    public void setEventoId(Long eventoId) {
        this.eventoId = eventoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
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

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
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

    public void agregarSesion(SesionEvento sesion) {
        if (sesion == null) {
            throw new IllegalArgumentException("La sesión no puede ser null");
        }

        sesiones.add(sesion);
    }

    public List<SesionEvento> getSesiones() {
        return Collections.unmodifiableList(sesiones);
    }
}
