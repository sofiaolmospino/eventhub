package com.eventhub.evento.domain.exception;

public class EventoNoEncontradoException extends RuntimeException {

    public EventoNoEncontradoException(Long id) {
        super("No se encontró el evento con id: " + id);
    }
}