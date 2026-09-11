package com.todolist.routes;

import com.todolist.controller.TareaController;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

import java.util.Map;

public class Routes {
    public static void configure(Javalin app, TareaController tareaController) {
        app.get("/healthz", ctx -> ctx.status(HttpStatus.OK).json(Map.of("status", "UP")));

        app.get("/tareas", tareaController::listar);
        app.get("/tareas/vista", tareaController::vista);
    }
}
