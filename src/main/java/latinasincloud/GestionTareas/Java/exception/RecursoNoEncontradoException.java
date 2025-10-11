package latinasincloud.GestionTareas.Java.exception; // recordar para exception Crear un nuevo paquete 'exception'

// Extiende RuntimeException para ser una excepción no chequeada
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String message) {
        super(message);
    }
}
