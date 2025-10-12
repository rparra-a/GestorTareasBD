package latinasincloud.GestionTareas.Java.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tareas")
public class Tarea {

    // atributos de la Tarea
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String titulo;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado; //pendiente, en progreso, completada

    // constructor por defecto
    public Tarea() {}

    // constructor con parámetros
    public Tarea(int id, Estado estado, String descripcion, String titulo) {
        this.id = id;
        this.estado = estado;
        this.descripcion = descripcion;
        this.titulo = titulo;
    }

    // getters and setters encapsulamiento de los atributos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Estado getEstado() {return estado;}

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEstado(Estado estado) {this.estado = estado;}
    /*@Override
    public String toString() {
        return "Tarea{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    } */
}
