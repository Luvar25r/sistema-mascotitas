package modelo;

public class Mascota {
    private String nombre;
    private String raza;
    private int numeroMascota;
    private String[] vacunasAplicadas;

    public Mascota(String nombre, String raza, int numeroMascota) {
        this.nombre = nombre;
        this.raza = raza;
        this.numeroMascota = numeroMascota;
        this.vacunasAplicadas = new String[0];
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public int getNumeroMascota() {
        return numeroMascota;
    }

    public void setNumeroMascota(int numeroMascota) {
        this.numeroMascota = numeroMascota;
    }

    public String[] getVacunasAplicadas() {
        return vacunasAplicadas;
    }

    public void setVacunasAplicadas(String[] vacunasAplicadas) {
        this.vacunasAplicadas = vacunasAplicadas;
    }

    public boolean tieneVacunas() {
        return vacunasAplicadas != null && vacunasAplicadas.length > 0;
    }
}
