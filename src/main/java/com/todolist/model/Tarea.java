package com.todolist.model;

import java.util.List;

public class Tarea {
    private final long id;
    private final String descripcion;
    private final String detalle;
    private final boolean completada;
    private final Prioridad prioridad;
    private final List<String> adjuntos;

    public Tarea(long id, String descripcion, String detalle, boolean completada, Prioridad prioridad, List<String> adjuntos) {
        this.id = id;
        this.descripcion = descripcion;
        this.detalle = detalle;
        this.completada = completada;
        this.prioridad = prioridad;
        this.adjuntos = List.copyOf(adjuntos);
    }

    public Tarea completar() {
        return new Tarea(id, descripcion, detalle, true, prioridad, adjuntos);
    }

    public long getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getDetalle() {
        return detalle;
    }

    public boolean isCompletada() {
        return completada;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public String getPrioridadCssClass() {
        return switch (prioridad) {
            case ALTA -> "tag-prioridad-alta";
            case MEDIA -> "tag-prioridad-media";
            case BAJA -> "tag-prioridad-baja";
        };
    }

    public List<String> getAdjuntos() {
        return adjuntos;
    }

    public boolean isTieneAdjuntos() {
        return !adjuntos.isEmpty();
    }
}
