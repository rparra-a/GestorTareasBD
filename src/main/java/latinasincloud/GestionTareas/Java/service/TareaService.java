package latinasincloud.GestionTareas.Java.service;

import latinasincloud.GestionTareas.Java.dto.TareaDto;
import latinasincloud.GestionTareas.Java.model.Estado;
import latinasincloud.GestionTareas.Java.model.Tarea;
import latinasincloud.GestionTareas.Java.repository.ITareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TareaService {

    @Autowired
    private ITareaRepository tareaRepository;

    // uso repositorio de tarea y referencio a servicio estado

    @Autowired
    private EstadoService estadoService;

    private TareaDto toDto(Tarea tarea) {
        return new TareaDto(
                tarea.getId(),
                tarea.getTitulo(),
                tarea.getEstado().getNombre(),
                tarea.getDescripcion()
        );
    }

    private Tarea toEntity(TareaDto tareaDto, Estado estado) {
        Tarea tarea = new Tarea();
        tarea.setTitulo(tareaDto.getTitulo());
        tarea.setDescripcion(tareaDto.getDescripcion());
        tarea.setEstado(estado);
        return tarea;
    }

    // crear tarea

    public TareaDto crearTarea(TareaDto tareaDto) {
        Estado estado = estadoService.obtenerEstadoPorNombre(tareaDto.getEstado());
        if (estado == null) {
            throw new RuntimeException("Estado no encontrada");
        }

        Tarea tarea = toEntity(tareaDto, estado);
        Tarea guardada = tareaRepository.save(tarea);

        return toDto(guardada);
    }

    //listar tareas
    public List<TareaDto> listarTareas() {
        return tareaRepository.findAll().stream().map(this::toDto).toList();
    }

    private Tarea obtenerTarea(int id) {
        return tareaRepository.findById(id).orElse(null);
    }


    public TareaDto obtenerTareaDto(int id) {
        Tarea tarea = obtenerTarea(id);
        return tarea != null ? toDto(tarea) : null;
    }

    // Buscar por estado
    public List<TareaDto> obtenerTareasEstado(String estado) {
        return tareaRepository.findByEstado_Nombre(estado).stream().map(this::toDto).toList();
    }

    // Actualizar
    public TareaDto actualizarTarea(int id, TareaDto tareaDto) {
        Tarea tarea = obtenerTarea(id);
        if (tarea == null) return null;

        tarea.setTitulo(tareaDto.getTitulo().isEmpty() ? tarea.getTitulo() : tareaDto.getTitulo());
        tarea.setDescripcion(tareaDto.getDescripcion().isEmpty() ? tarea.getDescripcion() : tareaDto.getDescripcion());

        if (tareaDto.getEstado() != null) {
            Estado estado = estadoService.obtenerEstadoPorNombre(tareaDto.getEstado());
            if (estado != null) tarea.setEstado(estado);
        }

        tareaRepository.save(tarea);
        return toDto(tarea);
    }

    // eliminar
    public boolean eliminarTarea(int id) {
        Tarea tarea = obtenerTarea(id);
        if (tarea != null) {
            tareaRepository.delete(tarea);
            return true;
        }
        return false;
    }
}