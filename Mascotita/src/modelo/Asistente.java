package modelo;

public class Asistente extends Persona {
    private int idAsistente;

    public Asistente(String nombre, String apellidoPaterno, String apellidoMaterno, String curp, int idAsistente) {
        super(nombre, apellidoPaterno, apellidoMaterno, curp);
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
