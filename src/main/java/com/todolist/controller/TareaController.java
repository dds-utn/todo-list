package com.todolist.controller;

import com.todolist.model.Tarea;
import com.todolist.repository.TareaRepository;
import io.javalin.Javalin;

import java.util.List;

public class TareaController {
    private final TareaRepository tareaRepository;

    public TareaController(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    public void register(Javalin app) {
        app.get("/tareas", ctx -> ctx.json(tareaRepository.listarTodas()));
        app.get("/tareas/vista", ctx -> ctx.html(renderHtml(tareaRepository.listarTodas())));
    }

    private String renderHtml(List<Tarea> tareas) {
        StringBuilder filas = new StringBuilder();
        for (Tarea tarea : tareas) {
            filas.append("<tr>")
                    .append("<td>").append(tarea.getId()).append("</td>")
                    .append("<td>").append(escapeHtml(tarea.getDescripcion())).append("</td>")
                    .append("<td>").append(tarea.isCompletada() ? "Sí" : "No").append("</td>")
                    .append("</tr>");
        }

        return "<!DOCTYPE html>"
                + "<html lang=\"es\">"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<title>Tareas</title>"
                + "</head>"
                + "<body>"
                + "<h1>Lista de tareas</h1>"
                + "<table border=\"1\" cellpadding=\"6\" cellspacing=\"0\">"
                + "<thead><tr><th>ID</th><th>Descripción</th><th>Completada</th></tr></thead>"
                + "<tbody>" + filas + "</tbody>"
                + "</table>"
                + "</body>"
                + "</html>";
    }

    private String escapeHtml(String valor) {
        return valor
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}
