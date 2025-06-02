package excepciones;

public class CitaOcupadaException extends Exception {
    public CitaOcupadaException() {
        super("No puede agendar la cita, ya se encuentra ocupada");
    }

    public CitaOcupadaException(String mensaje) {
        super(mensaje);
    }
}