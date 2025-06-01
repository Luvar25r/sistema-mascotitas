package excepciones;

public class NoHayAsistentesException extends RuntimeException {
    public NoHayAsistentesException(String message) {
        super(message);
    }
}
