package com.todolist.controller;

import com.todolist.model.Tarea;
import com.todolist.repository.TareaRepository;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TareaController {
    private final TareaRepository tareaRepository;

    public TareaController(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    public void listar(Context ctx) {
        String nombre = ctx.queryParam("nombre");
        List<Tarea> tareas = (nombre == null || nombre.isBlank())
                ? tareaRepository.listarTodas()
                : tareaRepository.buscarPorDescripcion(nombre);

        Map<String, Object> model = new HashMap<>();
        model.put("tareas", tareas);
        model.put("nombre", nombre == null ? "" : nombre);
        ctx.render("/templates/tareas.hbs", model);
    }
}
