package latinasincloud.GestionTareas.Java.repository;

import latinasincloud.GestionTareas.Java.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// esta interfaz hereda todos los métodos CRUD

public interface IEstadoRepository  extends JpaRepository<Estado,Integer> {
    Optional<Estado> findByNombre(String nombre);
}