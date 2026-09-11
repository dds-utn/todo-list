package com.todolist.controller;

import com.todolist.model.Prioridad;
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

    public void detalle(Context ctx) {
        long id = Long.parseLong(ctx.pathParam("id"));
        Tarea tarea = tareaRepository.buscarPorId(id).orElse(null);

        if (tarea == null) {
            ctx.status(404).result("Tarea no encontrada");
            return;
        }

        ctx.render("/templates/tarea-detalle.hbs", Map.of("tarea", tarea));
    }

    public void crear(Context ctx) {
        String descripcion = ctx.formParam("descripcion");
        String detalle = ctx.formParam("detalle");
        String prioridadParam = ctx.formParam("prioridad");
        Prioridad prioridad = (prioridadParam == null || prioridadParam.isBlank())
                ? Prioridad.MEDIA
                : Prioridad.valueOf(prioridadParam);

        tareaRepository.agregar(descripcion, detalle == null ? "" : detalle, prioridad);
        ctx.redirect("/tareas");
    }

    public void completar(Context ctx) {
        long id = Long.parseLong(ctx.pathParam("id"));
        tareaRepository.marcarComoCompletada(id);
        ctx.redirect("/tareas");
    }

    public void eliminar(Context ctx) {
        long id = Long.parseLong(ctx.pathParam("id"));
        tareaRepository.eliminar(id);
        ctx.redirect("/tareas");
    }
}
