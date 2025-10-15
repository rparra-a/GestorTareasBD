package latinasincloud.GestionTareas.Java.controller;

import latinasincloud.GestionTareas.Java.exception.EstadoInvalidoException;
import latinasincloud.GestionTareas.Java.exception.RecursoNoEncontradoException;
import latinasincloud.GestionTareas.Java.model.Tarea;
import latinasincloud.GestionTareas.Java.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import latinasincloud.GestionTareas.Java.service.TareaService;
import latinasincloud.GestionTareas.Java.dto.TareaDto;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")

public class TareaController {


    @Autowired
    private TareaService tareaService;

    // Crear Tarea POST
    @PostMapping
    public ResponseEntity<?> crearTarea(@RequestBody TareaDto tarea) {
        try {
            TareaDto nuevaTarea = tareaService.crearTarea(tarea);
            return ResponseEntity.status(201).body(nuevaTarea);
        }catch (RuntimeException ex){
            return ResponseEntity.status(400).body(ex.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<List<TareaDto>> listarTareas(){
        return ResponseEntity.ok(tareaService.listarTareas());
    }

    // Obtener tarea por id (GET)
    // añadir otro endpoint para obtener el libro por Id
    //Obtener tarea por ID GET
    @GetMapping("/{id}")
    public ResponseEntity<TareaDto> obtenerTarea(@PathVariable int id) {
        TareaDto tarea = tareaService.obtenerTareaDto(id);
        if (tarea != null) {
            return ResponseEntity.ok(tarea);
        }
        else{
            // Lanza la excepción en lugar de construir el 404 aquí
            throw new RecursoNoEncontradoException("Tarea no encontrada con ID: " + id);
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<TareaDto>> obtenerTareasEstado(@PathVariable String estado) {
        return ResponseEntity.ok(tareaService.obtenerTareasEstado(estado));
    }

    // Actualizar tarea PUT
    @PutMapping("/{id}")
    public ResponseEntity<TareaDto> actualizarTarea(@PathVariable int id, @RequestBody TareaDto tareaAc) {
        TareaDto actualizada = tareaService.actualizarTarea(id, tareaAc);
        if (actualizada != null) {
            return ResponseEntity.ok(actualizada);
        }
        else{
            // Lanza la excepción en lugar de construir el 404 aquí
            throw new RecursoNoEncontradoException("No se puede actualizar. Tarea no encontrada con ID: " + id);
        }
    }

    // Eliminar tarea (DELETE)  verifica el estado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable int id) {
        boolean eliminada = tareaService.eliminarTarea(id);

        if (eliminada){
            return ResponseEntity.noContent().build();
        }
        else{
            // Si la eliminación falló, determinamos la razón para lanzar la excepción correcta
            TareaDto tarea = tareaService.obtenerTareaDto(id);

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
