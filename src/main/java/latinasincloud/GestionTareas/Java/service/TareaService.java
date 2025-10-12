package latinasincloud.GestionTareas.Java.service;

import latinasincloud.GestionTareas.Java.model.Tarea;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors; // Necesario para el filtrado

@Service
public class TareaService {
    //agregar lista de Tareas y contador Id incremental
    private final List<Tarea> tareas = new ArrayList<Tarea>();
    private static int contadorId = 1;

    // incorporar los métodos (CRUD)

    // 1. Crear tarea (POST)

    public Tarea crearTarea(Tarea tarea) {
        /* asigna Id autogenerado */
        tarea.setId(contadorId++);
        tareas.add(tarea);
        return tarea;
    }


    //2. Listar todas las tareas. (GET)
    //forma básica son filtro
    /*  public List<Tarea> listarTareas() {
        return tareas;
    } */

    /*
    // Lista tarea, opcionalmente filtradas por estado y/o título.
     * @param estado Filtro por estado ("pendiente", "en progreso", "completada"). Puede ser null.
     * @param título Filtro por título, busca si el título contiene este texto. Puede ser null.
     * @return Lista de tareas que cumplen con los filtros.
     */

    public List<Tarea> listarTareas(String estado, String titulo){
        // El método ahora recibe parámetros
        return tareas.stream()
                .filter(tarea -> estado == null || tarea.getEstado().equalsIgnoreCase(estado))
                .filter(tarea -> titulo == null || tarea.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }

    // 3. Obtener una tarea por ID. (GET)

    public Tarea obtenerTareaId(int id){
        return tareas.stream().filter(l -> l.getId() == id).findFirst().orElse(null);
        }


    //4. Actualizar una tarea (título, descripción y/o estado). (PUT)

    public Tarea actualizarTarea(int id, Tarea tareaAc) {
        Tarea tarea = obtenerTareaId(id);
        if (tarea != null) {
            // Se ha simplificado la lógica del operador ternario para usar el valor de la tarea actualizada
            // solo si no es nulo/vacío,
            // usar el valor de la tareaAc solo si no está vacío, manteniendo el valor anterior si está vacío.
            tarea.setTitulo(tareaAc.getTitulo() != null && !tareaAc.getTitulo().isEmpty() ? tareaAc.getTitulo() : tarea.getTitulo());
            tarea.setDescripcion(tareaAc.getDescripcion() != null && !tareaAc.getDescripcion().isEmpty() ? tareaAc.getDescripcion() : tarea.getDescripcion());
            tarea.setEstado(tareaAc.getEstado() != null && !tareaAc.getEstado().isEmpty() ? tareaAc.getEstado() : tarea.getEstado());
            return tarea;
        }
        return null;
    }

    // 5. Eliminar una tarea por ID. (DELETE)

    /**
     * Elimina una tarea solo si su estado es "completada".
     * @param id El ID de la tarea a eliminar.
     * @return true si la tarea fue eliminada, false si no se encontró o su estado no es "completada".
     */
    public boolean EliminarTarea(int id){
        Tarea tarea = obtenerTareaId(id);

        // 1. Verificar si la tarea existe
        if (tarea == null) {
            return false; // No se encontró
        }

        // 2. Verificar si el estado es "completada"
        if ("completada".equalsIgnoreCase(tarea.getEstado())) {
            // Eliminar solo si está "completada"
            return tareas.remove(tarea);
        }

        // Si existe pero no está "completada", no se elimina.
        return false;
    }

    /*
    // Eliminar
    // condicional con operador ternario
    // si encuentro mi libro remuevo

    public boolean eliminarTarea(int id) {
                return tareas.removeIf(tarea -> tarea.getId() == id);
    }
    */

}

