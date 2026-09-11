package com.todolist.repository;

import com.todolist.model.Prioridad;
import com.todolist.model.Tarea;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryTareaRepository implements TareaRepository {
    private final List<Tarea> tareas = new CopyOnWriteArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public InMemoryTareaRepository() {
        tareas.add(new Tarea(
                nextId.getAndIncrement(),
                "Comprar leche",
                "Ir al supermercado de la esquina, llevar la lista completa de almacén.",
                false,
                Prioridad.BAJA,
                List.of("https://picsum.photos/seed/leche/200")
        ));
        tareas.add(new Tarea(
                nextId.getAndIncrement(),
                "Terminar el informe",
                "Cerrar el informe trimestral y enviarlo a revisión antes del viernes.",
                false,
                Prioridad.ALTA,
                List.of("https://picsum.photos/seed/informe/200", "https://picsum.photos/seed/informe2/200")
        ));
        tareas.add(new Tarea(
                nextId.getAndIncrement(),
                "Pagar la factura de luz",
                "Vence el día 10, se puede pagar por home banking.",
                true,
                Prioridad.MEDIA,
                List.of()
        ));
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

    @Override
    public Optional<Tarea> buscarPorId(long id) {
        return tareas.stream()
                .filter(tarea -> tarea.getId() == id)
                .findFirst();
    }

    @Override
    public Tarea agregar(String descripcion, String detalle, Prioridad prioridad) {
        Tarea tarea = new Tarea(nextId.getAndIncrement(), descripcion, detalle, false, prioridad, List.of());
        tareas.add(tarea);
        return tarea;
    }

    @Override
    public void eliminar(long id) {
        tareas.removeIf(tarea -> tarea.getId() == id);
    }

    @Override
    public void marcarComoCompletada(long id) {
        for (int i = 0; i < tareas.size(); i++) {
            if (tareas.get(i).getId() == id) {
                tareas.set(i, tareas.get(i).completar());
                return;
            }
        }
    }
}
