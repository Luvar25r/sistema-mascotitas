package modelo;

import java.util.Date;

public class Asistente extends Persona {
    private int idAsistente;

    public Asistente(String nombre, String apellidoPaterno, String apellidoMaterno, 
                     Date fechaNacimiento, String curp, int idAsistente) {
        super(nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, curp);
        this.idAsistente = idAsistente;
    }

    // Constructor sin apellido materno (opcional)
    public Asistente(String nombre, String apellidoPaterno, Date fechaNacimiento, 
                     String curp, int idAsistente) {
        super(nombre, apellidoPaterno, fechaNacimiento, curp);
        this.idAsistente = idAsistente;
    }

    // Getters y setters
    public int getIdAsistente() {
        return idAsistente;
    }

    public void setIdAsistente(int idAsistente) {
        this.idAsistente = idAsistente;
    }
}