package excepciones;

public class NoHayVeterinariosException extends RuntimeException {
    public NoHayVeterinariosException(String message) {
        super(message);
    }
}
