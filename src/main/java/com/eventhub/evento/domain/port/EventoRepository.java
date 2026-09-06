package com.eventhub.evento.domain.port;

import com.eventhub.evento.domain.Evento;
import java.util.List;
import java.util.Optional;

public interface EventoRepository {
    Evento guardar(Evento evento);
    Optional<Evento> buscarPorId(Long id);
    List<Evento> listarTodos();
    boolean existePorNombre(String nombre);
}
