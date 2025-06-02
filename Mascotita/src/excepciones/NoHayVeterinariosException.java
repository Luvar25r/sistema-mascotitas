package excepciones;

public class NoHayVeterinariosException extends Exception {
    public NoHayVeterinariosException(String mensaje) {
        super(mensaje);
    }
}
