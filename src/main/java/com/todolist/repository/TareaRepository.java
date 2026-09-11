package com.todolist.repository;

import com.todolist.model.Prioridad;
import com.todolist.model.Tarea;

import java.util.List;
import java.util.Optional;

public interface TareaRepository {
    List<Tarea> listarTodas();

    List<Tarea> buscarPorDescripcion(String texto);

    Optional<Tarea> buscarPorId(long id);

    Tarea agregar(String descripcion, String detalle, Prioridad prioridad);

    void eliminar(long id);

    void marcarComoCompletada(long id);
}
