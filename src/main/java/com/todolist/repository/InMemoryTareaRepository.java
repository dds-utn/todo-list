package com.todolist.repository;

import com.todolist.model.Tarea;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryTareaRepository implements TareaRepository {
    private final List<Tarea> tareas = new CopyOnWriteArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public InMemoryTareaRepository() {
        tareas.add(new Tarea(nextId.getAndIncrement(), "Comprar leche", false));
        tareas.add(new Tarea(nextId.getAndIncrement(), "Terminar el informe", false));
        tareas.add(new Tarea(nextId.getAndIncrement(), "Pagar la factura de luz", true));
    }

    @Override
    public List<Tarea> listarTodas() {
        return List.copyOf(tareas);
    }

    @Override
    public List<Tarea> buscarPorDescripcion(String texto) {
        String textoBuscado = texto.toLowerCase(Locale.ROOT);
        return tareas.stream()
                .filter(tarea -> tarea.getDescripcion().toLowerCase(Locale.ROOT).contains(textoBuscado))
                .toList();
    }
}
