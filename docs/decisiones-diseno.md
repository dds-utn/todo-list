# Decisiones de diseño — CRUD de tareas y detalle

## Completar y eliminar como `POST`, no `PUT`/`DELETE`

Las rutas para completar (`POST /tareas/{id}/completar`) y borrar
(`POST /tareas/{id}/eliminar`) una tarea usan `POST` con el verbo en el
path, en vez de `PUT`/`PATCH` o `DELETE` sobre `/tareas/{id}`.

**Por qué:** las vistas son forms HTML servidos por el propio backend
(sin JavaScript/fetch de por medio), y los forms HTML solo soportan los
métodos `GET` y `POST` como atributo `method`. No hay forma de que un
`<form>` envíe un `PUT` o un `DELETE` nativamente; hacerlo requeriría JS
(`fetch`) o una librería de method-override, que no están en el alcance
de este proyecto.

**Alternativas descartadas:**
- `DELETE /tareas/{id}` / `PUT /tareas/{id}/completar`: más "RESTful",
  pero no invocables desde un `<form>` sin JavaScript.
- Method override vía query param (`?_method=DELETE`) o header: agrega
  una capa de "magia" y una dependencia extra solo para simular el verbo,
  sin necesidad real dado que no hay una API HTTP consumida por otro
  cliente (todavía es solo esta vista server-rendered).

**Cómo aplica:** si en el futuro se agrega un cliente propio (app,
frontend separado, fetch/AJAX), tiene sentido exponer además rutas
`DELETE`/`PUT` reales para ese consumidor, dejando estas `POST` como
soporte específico de los forms HTML.

## Adjuntos precargados, sin lógica de subida

El detalle de una tarea (`GET /tareas/{id}`) muestra una lista de URLs
de adjuntos, pero no hay endpoint ni lógica real de upload — las tareas
sembradas en `InMemoryTareaRepository` ya traen URLs de ejemplo
(`https://picsum.photos/...`) y una tarea nueva (`agregar`) arranca con
la lista vacía. El `<form>` de "subir adjunto" en la vista está deshabilitado
(`disabled`) y no envía a ningún lado.

**Por qué:** se pidió explícitamente no implementar la lógica de backend
para adjuntos, solo la parte visual de "cómo se vería" con datos de
ejemplo.

**Cómo aplica:** si se agrega upload real, hay que: (1) sumar un endpoint
`POST /tareas/{id}/adjuntos` que acepte multipart, (2) decidir dónde
persisten los archivos (memoria no sirve para binarios grandes), y (3)
sacar el `disabled` del form y apuntar su `action`/`enctype` a ese
endpoint.

## `Tarea` sigue siendo inmutable

Para "completar" una tarea, el repositorio no muta el objeto `Tarea`
existente: crea uno nuevo vía `Tarea#completar()` y reemplaza la
posición en la lista (`InMemoryTareaRepository#marcarComoCompletada`).

**Por qué:** mantiene la inmutabilidad ya usada por el modelo (todos los
campos son `final`), evitando estado mutable compartido en una
`CopyOnWriteArrayList` accedida potencialmente desde varios requests
concurrentes.

**Cómo aplica:** cualquier otra operación de "modificar" una tarea
existente (editar descripción, cambiar prioridad, etc.) debería seguir el
mismo patrón: crear una instancia nueva y reemplazarla en el repositorio,
en vez de agregar setters a `Tarea`.
