package latinasincloud.GestionTareas.Java.service;

import latinasincloud.GestionTareas.Java.model.Estado;
import latinasincloud.GestionTareas.Java.repository.IEstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadoService{
    @Autowired
    private IEstadoRepository estadoRepository;

    public Estado obtenerEstadoPorNombre(String nombre){
        return estadoRepository.findByNombre(nombre).orElse(null);
    }
}