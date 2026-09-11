package com.todolist.model;

public class Tarea {
    private final long id;
    private final String descripcion;
    private final boolean completada;

    public Tarea(long id, String descripcion, boolean completada) {
        this.id = id;
        this.descripcion = descripcion;
        this.completada = completada;
    }

    public long getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isCompletada() {
        return completada;
    }
}
