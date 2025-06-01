package modelo;

public class Veterinario extends Persona {
    private int numeroCedula;

    public Veterinario(String nombre, String apellidoPaterno, String apellidoMaterno, String curp, int numeroCedula) {
        super(nombre, apellidoPaterno, apellidoMaterno, curp);
        this.numeroCedula = numeroCedula;
    }

    // Getters y setters
    public int getNumeroCedula() {
        return numeroCedula;
    }

    public void setNumeroCedula(int numeroCedula) {
        this.numeroCedula = numeroCedula;
    }
}
