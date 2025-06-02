package modelo.personas;

import modelo.Persona;
import java.util.Date;

/**
 * Clase para representar a los asistentes del personal
 */
public class Asistente extends Persona {
    private int idAsistente;

    public Asistente(String nombre, String apellidoPaterno, String apellidoMaterno, 
                   Date fechaNacimiento, String curp, int idAsistente) {
        super(nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, curp);
        this.idAsistente = idAsistente;
    }

    public Asistente(String nombre, String apellidoPaterno, Date fechaNacimiento, 
                    String curp, int idAsistente) {
        super(nombre, apellidoPaterno, fechaNacimiento, curp);
        this.idAsistente = idAsistente;
    }

    public int getIdAsistente() {
        return idAsistente;
    }

    public void setIdAsistente(int idAsistente) {
        this.idAsistente = idAsistente;
    }

    @Override
    public String toString() {
        return "Asistente{" +
                "idAsistente=" + idAsistente +
                ", nombre='" + nombre + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", apellidoMaterno='" + apellidoMaterno + '\'' +
                '}';
    }
}
