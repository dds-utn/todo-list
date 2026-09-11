# Todo List

Proyecto base en Java con [Javalin](https://javalin.io/), pensado como ejercicio incremental: cada branch agrega una feature sobre la anterior.

## Cómo correr

Requiere JDK 17+ y Maven.

```bash
mvn package
java -jar target/todo-list.jar
```

Por defecto levanta en el puerto `7000` (configurable con la variable de entorno `PORT`).

- `GET /healthz` — healthcheck, devuelve `{"status":"UP"}`.
- `GET /tareas` — listado de tareas (HTML, con Handlebars).
- `GET /tareas/{id}` — detalle de una tarea.

## Requisitos funcionales

### Implementados

- Healthcheck (`GET /healthz`).
- Repositorio de tareas en memoria (alta, baja, completar, búsqueda, detalle).
- Vistas renderizadas con Handlebars (motor propio via `FileRenderer`, sin depender del módulo `javalin-rendering` que no soporta Handlebars en la línea 6.x de Javalin).
- Navbar y footer compartidos entre vistas usando un **layout de Handlebars** (`{{#block}}`/`{{#partial}}`), sin duplicar el `<head>`/estructura HTML en cada template.
- 3 temas visuales intercambiables, en CSS puro (minimal, neón, pastel).
- Búsqueda de tareas por nombre: parcial, case-insensitive, resuelta en el repositorio, vía query string sobre `GET /tareas`.
- Alta, completar y borrado de tareas.
- Detalle de tarea con prioridad (mostrada como tag en el listado) y galería de adjuntos (URLs precargadas, sin lógica real de subida).
- Accesibilidad y responsive: labels, `aria-label`, tabla con scroll horizontal, layout que se adapta a mobile.
- Sistema de CTAs consistente (`primary`/`secondary`/`success`/`danger`/`ghost`) en los 3 temas.

### Pendientes / a implementar

- Upload real de adjuntos (hoy es solo UI, el form está deshabilitado).
- Edición de una tarea existente (descripción, prioridad, detalle).
- Persistencia real — hoy todo vive en memoria y se pierde al reiniciar.
- Rutas `PUT`/`DELETE` reales, si en algún momento se consume esta API desde un cliente propio (hoy `completar`/`eliminar` son `POST` porque los forms HTML no soportan otros verbos, ver `docs/decisiones-diseno.md`).
- Tests automatizados (todavía no hay ninguno).

## Branches

Cada branch parte de la anterior (son incrementales):

| Branch | Resuelve |
|---|---|
| `feature/tareas-endpoint` | Repositorio en memoria + endpoint que devuelve tareas (JSON y HTML en rutas separadas) |
| `feature/tareas-content-negotiation` | Unifica esos dos endpoints en uno solo que negocia por header `Accept` |
| `feature/tareas-handlebars` | Reemplaza el HTML armado a mano por templates Handlebars |
| `feature/tareas-estilos` | CSS puro, 3 temas visuales intercambiables |
| `feature/tareas-busqueda` | Búsqueda por nombre vía query string, case-insensitive y parcial |
| `feature/tareas-crud-detalle` | Alta, completar y borrado de tareas + vista de detalle con prioridad y adjuntos |
| `feature/tareas-layout` | Navbar (logo, usuario) y footer compartidos entre vistas usando un layout de Handlebars |
| `feature/tareas-ux` | Accesibilidad, responsive, sistema de botones/CTAs, fixes de layout |

## Documentación adicional

- [`docs/decisiones-diseno.md`](docs/decisiones-diseno.md) — decisiones de diseño no obvias (por qué `completar`/`eliminar` son `POST`, por qué los adjuntos son solo UI, por qué `Tarea` sigue siendo inmutable).
