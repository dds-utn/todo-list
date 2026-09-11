package com.todolist;

import com.todolist.controller.TareaController;
import com.todolist.repository.InMemoryTareaRepository;
import com.todolist.repository.TareaRepository;
import com.todolist.rendering.HandlebarsFileRenderer;
import com.todolist.routes.Routes;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "7000"));

        Javalin app = Javalin.create(config -> config.fileRenderer(new HandlebarsFileRenderer()));

        TareaRepository tareaRepository = new InMemoryTareaRepository();
        TareaController tareaController = new TareaController(tareaRepository);
        Routes.configure(app, tareaController);

        app.start(port);
    }
}
