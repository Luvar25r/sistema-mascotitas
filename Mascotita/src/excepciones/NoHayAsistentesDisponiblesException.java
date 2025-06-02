package excepciones;

public class NoHayAsistentesDisponiblesException extends Exception {
    public NoHayAsistentesDisponiblesException() {
        super("No hay asistentes disponibles");
    }
    
    public NoHayAsistentesDisponiblesException(String mensaje) {
        super(mensaje);
    }
}