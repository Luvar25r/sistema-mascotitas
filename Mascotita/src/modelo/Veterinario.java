package modelo;

import java.util.Date;

public class Veterinario extends Persona {
    private int numeroCedula;

    public Veterinario(String nombre, String apellidoPaterno, String apellidoMaterno, 
                       Date fechaNacimiento, String curp, int numeroCedula) {
        super(nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, curp);
        this.numeroCedula = numeroCedula;
    }

    // Constructor sin apellido materno (opcional)
    public Veterinario(String nombre, String apellidoPaterno, Date fechaNacimiento, 
                       String curp, int numeroCedula) {
        super(nombre, apellidoPaterno, fechaNacimiento, curp);
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