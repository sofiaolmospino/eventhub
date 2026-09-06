package com.eventhub.evento.infrastructure.memory;

import com.eventhub.evento.domain.Evento;
import com.eventhub.evento.domain.port.EventoRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EventoRepositoryEnMemoria implements EventoRepository {

    private final Map<Long, Evento> datos = new LinkedHashMap<>();

    @Override
    public Evento guardar(Evento evento) {
        datos.put(evento.getEventoId(), evento);
        return evento;
    }

    @Override
    public Optional<Evento> buscarPorId(Long id) {
        return Optional.ofNullable(datos.get(id));
    }

    @Override
    public List<Evento> listarTodos() {
        return new ArrayList<>(datos.values());
    }

    @Override
    public boolean existePorNombre(String nombre) {
        return datos.values().stream()
                .anyMatch(e -> e.getNombre().equalsIgnoreCase(nombre));
    }
}