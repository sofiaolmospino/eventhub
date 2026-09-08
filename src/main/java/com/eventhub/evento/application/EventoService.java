package com.eventhub.evento.application;

import com.eventhub.evento.domain.Evento;
import com.eventhub.evento.domain.EstadoEvento;
import com.eventhub.evento.domain.exception.EventoNoEncontradoException;
import com.eventhub.evento.domain.exception.NombreEventoDuplicadoException;
import com.eventhub.evento.domain.port.EventoRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class EventoService {

    private final EventoRepository eventoRepository;
    private final AtomicLong secuencia = new AtomicLong(0);

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public Evento registrar(
            String nombre,
            String descripcion,
            String responsable,
            String modalidad,
            OffsetDateTime fechaInicio,
            OffsetDateTime fechaFin,
            Integer capacidad,
            EstadoEvento estado,
            Long usuarioId
    ) {

        if (eventoRepository.existePorNombre(nombre)) {
            throw new NombreEventoDuplicadoException(nombre);
        }

        Long id = secuencia.incrementAndGet();

        Evento evento = new Evento(
                id,
                nombre,
                descripcion,
                responsable,
                modalidad,
                fechaInicio,
                fechaFin,
                capacidad,
                estado,
                usuarioId,
                OffsetDateTime.now(),
                OffsetDateTime.now()
        );

        return eventoRepository.guardar(evento);
    }

    public List<Evento> listar() {
        return eventoRepository.listarTodos();
    }

    public Optional<Evento> buscarPorId(Long id) {
        return eventoRepository.buscarPorId(id);
    }

    public Evento buscarPorIdObligatorio(Long id) {
        return eventoRepository.buscarPorId(id)
                .orElseThrow(() -> new EventoNoEncontradoException(id));
    }
}
