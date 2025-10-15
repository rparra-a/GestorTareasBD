package latinasincloud.GestionTareas.Java.dto;

import latinasincloud.GestionTareas.Java.model.Estado;
import latinasincloud.GestionTareas.Java.model.Tarea;

// Tiene los mismos atributos que tarea

public class TareaDto {
    private int id;
    private String titulo;
    private String estado;
    private String descripcion;

    public TareaDto(int id, String titulo, String estadoNombre, String descripcion) {
        this.id = id;
        this.titulo = titulo;
        this.estado = estadoNombre;
        this.descripcion = descripcion;
    }

    // encapsula la información con los getters de los atributos
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getEstado() {
        return estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

}