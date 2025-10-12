package latinasincloud.GestionTareas.Java.repository;

import latinasincloud.GestionTareas.Java.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITareaRepository extends JpaRepository<Tarea,Integer> {
    List<Tarea> findByEstado_Nombre(String nombre);
}