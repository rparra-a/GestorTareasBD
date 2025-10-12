package latinasincloud.GestionTareas.Java.model;

public class Tarea {

    // atributos de la Tarea
    private int id;
    private String titulo;
    private String descripcion;
    private String estado; //pendiente, en progreso, completada

    // constructor por defecto
    public Tarea() {}

    // constructor con parámetros
    public Tarea(int id, String estado, String descripcion, String titulo) {
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

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

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
