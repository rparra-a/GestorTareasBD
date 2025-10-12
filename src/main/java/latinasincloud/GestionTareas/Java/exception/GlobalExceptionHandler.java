package latinasincloud.GestionTareas.Java.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

// @ControllerAdvice centraliza el manejo de excepciones en toda la aplicación
@ControllerAdvice
public class GlobalExceptionHandler {

    // Este método manejará las excepciones de tipo RecursoNoEncontradoException (resp 404 Not Found)
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<?> handleResourceNotFoundException(RecursoNoEncontradoException ex, WebRequest request) {
        // Objeto para el cuerpo de la respuesta de error ( crear una clase de error más sofisticada)
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getDescription(false)
        );
        // Devuelve una respuesta con código de estado 404
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // Nuevo: Maneja Estado Inválido (409 Conflict)
    @ExceptionHandler(EstadoInvalidoException.class)
    public ResponseEntity<?> handleEstadoInvalidoException(EstadoInvalidoException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.CONFLICT.value(), // Código 409
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    // Clase simple para estructurar la respuesta de error
    class ErrorDetails {
        private int status;
        private String message;
        private String details;

        public ErrorDetails(int status, String message, String details) {
            this.status = status;
            this.message = message;
            this.details = details;
        }

        // Añade Getters y Setters
        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }

}