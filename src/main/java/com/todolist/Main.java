package com.todolist;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "7000"));

        Javalin app = Javalin.create();

        app.get("/healthz", ctx -> ctx.status(HttpStatus.OK).json(Map.of("status", "UP")));

        app.start(port);
    }
}
