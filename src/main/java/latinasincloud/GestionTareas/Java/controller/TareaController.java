package latinasincloud.GestionTareas.Java.controller;

import latinasincloud.GestionTareas.Java.exception.EstadoInvalidoException;
import latinasincloud.GestionTareas.Java.exception.RecursoNoEncontradoException;
import latinasincloud.GestionTareas.Java.model.Tarea;
import latinasincloud.GestionTareas.Java.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")

public class TareaController {


    @Autowired
    private TareaService tareaService;

    // Crear Tarea POST
    @PostMapping
    public ResponseEntity<Tarea> crearTarea(@RequestBody Tarea tarea) {
        Tarea nuevaTarea = tareaService.crearTarea(tarea);
        return ResponseEntity.status(201).body(nuevaTarea);
    }

    // Listar todas las tareas (GET)
   /* @GetMapping
    public ResponseEntity<List<Tarea>> listarTareas() {
        return ResponseEntity.ok(tareaService.listarTareas());
    }
    // esta clase permite responder al sistema que esta pidiendo la información con código HTTP
*/

    // Ahora acepta Query Params para filtrado

    @GetMapping
    public ResponseEntity<List<Tarea>> listarTareas(
            @RequestParam(required = false) String estado, // Parámetro de consulta opcional
            @RequestParam(required = false) String titulo) { // Parámetro de consulta opcional

        // Llama al servicio con los parámetros de filtrado
        return ResponseEntity.ok(tareaService.listarTareas(estado, titulo));
    }


    // Obtener tarea por id (GET)
    // añadir otro endpoint para obtener el libro por Id
    //Obtener tarea por ID GET
    @GetMapping("/{id}")
    public ResponseEntity<Tarea> obtenerTarea(@PathVariable int id) {
        Tarea tarea = tareaService.obtenerTareaId(id);
        if (tarea != null) {
            return ResponseEntity.ok(tarea);
        }
        else{
            // Lanza la excepción en lugar de construir el 404 aquí
            throw new RecursoNoEncontradoException("Tarea no encontrada con ID: " + id);
        }
    }

    // Actualizar tarea PUT
    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizarTarea(@PathVariable int id, @RequestBody Tarea tareaAc) {
        Tarea actualizada = tareaService.actualizarTarea(id, tareaAc);
        if (actualizada != null) {
            return ResponseEntity.ok(actualizada);
        }
        else{
            // Lanza la excepción en lugar de construir el 404 aquí
            throw new RecursoNoEncontradoException("No se puede actualizar. Tarea no encontrada con ID: " + id);
        }
    }

    // Eliminar tarea (DELETE)  Ahora verifica el estado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable int id) {
        boolean eliminada = tareaService.EliminarTarea(id);

        if (eliminada){
            return ResponseEntity.noContent().build();
        }
        else{
            // Si la eliminación falló, determinamos la razón para lanzar la excepción correcta
            Tarea tarea = tareaService.obtenerTareaId(id);

            if (tarea == null) {
                // Razón 1: Tarea no encontrada
                throw new RecursoNoEncontradoException("No se puede eliminar. Tarea no encontrada con ID: " + id);
            }
            else {
                // Razón 2: Tarea existe, pero el estado no es "completada"
                throw new EstadoInvalidoException("No se puede eliminar. La tarea con ID: " + id + " debe estar en estado 'completada' para ser eliminada. Estado actual: " + tarea.getEstado());
            }
        }
    }
}

/*
Ejemplo de uso:

GET /api/tareas - Lista todas las tareas.

GET /api/tareas?estado=pendiente - Lista solo las tareas con estado "pendiente".

GET /api/tareas?titulo=compra - Lista tareas cuyo título contenga "compra".

GET /api/tareas?estado=en%20progreso&titulo=reporte - Lista tareas con estado "en progreso" y título que contenga "reporte".

*/
