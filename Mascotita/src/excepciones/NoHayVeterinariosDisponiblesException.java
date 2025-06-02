package excepciones;

public class NoHayVeterinariosDisponiblesException extends Exception {
    public NoHayVeterinariosDisponiblesException() {
        super("No hay veterinarios disponibles");
    }
    
    public NoHayVeterinariosDisponiblesException(String mensaje) {
        super(mensaje);
    }
}