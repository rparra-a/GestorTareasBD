package latinasincloud.GestionTareas.Java.controller;

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

    //Listar Tareas GET
    @GetMapping
    public ResponseEntity<List<Tarea>> getTareas() {
        return ResponseEntity.ok(tareaService.listarTareas());
    }

    //Obtener tarea por ID GET
    @GetMapping("/{id}")
    public ResponseEntity<Tarea> obtenerTarea(@PathVariable int id) {
        Tarea tarea = tareaService.obtenerTareaId(id);
        if (tarea != null) {
            return ResponseEntity.ok(tarea);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    //Crear tarea POST
    @PostMapping
    public ResponseEntity<Tarea> crearTarea(@RequestBody Tarea tarea) {
        Tarea nuevaTarea = tareaService.crearTarea(tarea);
        return ResponseEntity.status(201).body(nuevaTarea);
    }

    // Actualizar tarea PUT
    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizarTarea(@PathVariable int id, @RequestBody Tarea tareaAc) {
        Tarea actualizada = tareaService.actualizarTarea(id, tareaAc);
        if (actualizada != null) {
            return ResponseEntity.ok(actualizada);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    //Eliminar tarea DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable int id) {
        boolean eliminada = tareaService.EliminarTarea(id);
        if (eliminada){
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
