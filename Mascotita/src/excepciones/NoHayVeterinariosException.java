package excepciones;

public class NoHayVeterinariosException extends Exception {
    public NoHayVeterinariosException() {
        super("No hay veterinarios disponibles");
    }

    public NoHayVeterinariosException(String mensaje) {
        super(mensaje);
    }
}