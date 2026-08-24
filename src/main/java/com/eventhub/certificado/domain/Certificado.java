package com.eventhub.certificado.domain;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Certificado {
    private Long certificadoId;
    private Long participanteId;
    private Long eventoId;
    private OffsetDateTime fechaEmision;
    private BigDecimal porcentajeAsistencia;
    private String estado;
    private Long usuarioId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public Certificado() {
    }

    public Certificado(Long certificadoId, Long participanteId, Long eventoId, OffsetDateTime fechaEmision, BigDecimal porcentajeAsistencia, String estado, Long usuarioId, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.certificadoId = certificadoId;
        this.participanteId = participanteId;
        this.eventoId = eventoId;
        this.fechaEmision = fechaEmision;
        this.porcentajeAsistencia = porcentajeAsistencia;
        this.estado = estado;
        this.usuarioId = usuarioId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getCertificadoId() {
        return certificadoId;
    }

    public void setCertificadoId(Long certificadoId) {
        this.certificadoId = certificadoId;
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

    public OffsetDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(OffsetDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public BigDecimal getPorcentajeAsistencia() {
        return porcentajeAsistencia;
    }

    public void setPorcentajeAsistencia(BigDecimal porcentajeAsistencia) {
        this.porcentajeAsistencia = porcentajeAsistencia;
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
