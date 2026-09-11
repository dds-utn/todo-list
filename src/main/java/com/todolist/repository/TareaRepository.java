package com.todolist.repository;

import com.todolist.model.Tarea;

import java.util.List;

public interface TareaRepository {
    List<Tarea> listarTodas();

    List<Tarea> buscarPorDescripcion(String texto);
}
