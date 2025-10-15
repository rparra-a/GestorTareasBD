package latinasincloud.GestionTareas.Java.model; // capa de modelo defino las entidades

import jakarta.persistence.*;

@Entity
@Table(name = "estados")

public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String nombre;

    public Estado() {}
    public Estado (int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // solo necesito get de los atributos para el estado de la tarea
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

}