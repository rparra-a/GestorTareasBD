package latinasincloud.GestionTareas.Java.service;

import latinasincloud.GestionTareas.Java.model.Tarea;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TareaService {
    private final List<Tarea> tareas = new ArrayList<Tarea>();
    private static int contadorId = 1;

    public Tarea crearTarea(Tarea tarea) {
        tarea.setId(contadorId++);
        tareas.add(tarea);
        return tarea;
    }
    public List<Tarea> listarTareas(){
        return tareas;
    }

    public Tarea obtenerTareaId(int id){
        return tareas.stream().filter(tarea -> tarea.getId() == id).findFirst().orElse(null);
    }

    //Actualizar tarea
    public Tarea actualizarTarea(int id, Tarea tareaAc){
        Tarea tarea = obtenerTareaId(id);
        if (tarea != null) { //if de la forma operador ternario
            tarea.setTitulo(tareaAc.getTitulo().isEmpty() ? tareaAc.getTitulo() : tareaAc.getTitulo());
            tarea.setDescripcion(tareaAc.getDescripcion().isEmpty() ? tareaAc.getDescripcion() : tareaAc.getDescripcion());
            tarea.setEstado(tareaAc.getEstado().isEmpty() ? tareaAc.getEstado() : tareaAc.getEstado());
            return tarea;
        }
        return null;

    }

    public boolean EliminarTarea(int id){
        return tareas.removeIf(tarea -> tarea.getId() == id);

    }
}

