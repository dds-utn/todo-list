package com.todolist.controller;

import com.todolist.repository.TareaRepository;
import io.javalin.http.Context;

import java.util.Map;

public class TareaController {
    private final TareaRepository tareaRepository;

    public TareaController(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    public void listar(Context ctx) {
        ctx.render("/templates/tareas.hbs", Map.of("tareas", tareaRepository.listarTodas()));
    }
}
