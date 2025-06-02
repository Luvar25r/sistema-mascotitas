package modelo.personas;

import modelo.Persona;
import java.util.Date;

public class Gerente extends Persona {
    private int numeroGerente;
    private boolean tieneSucursal;
    private String sucursal; // En caso de que tieneSucursal sea true
    
    // Constructor completo
    public Gerente(String nombre, String apellidoPaterno, String apellidoMaterno, 
                   Date fechaNacimiento, String curp, int numeroGerente, 
                   boolean tieneSucursal, String sucursal) {
        super(nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, curp);
        this.numeroGerente = numeroGerente;
        this.tieneSucursal = tieneSucursal;
        this.sucursal = sucursal;
    }
    
    // Constructor sin apellido materno
    public Gerente(String nombre, String apellidoPaterno, 
                   Date fechaNacimiento, String curp, int numeroGerente, 
                   boolean tieneSucursal, String sucursal) {
        super(nombre, apellidoPaterno, fechaNacimiento, curp);
        this.numeroGerente = numeroGerente;
        this.tieneSucursal = tieneSucursal;
        this.sucursal = sucursal;
    }
    
    // Getters y Setters
    public int getNumeroGerente() {
        return numeroGerente;
    }
    
    public void setNumeroGerente(int numeroGerente) {
        this.numeroGerente = numeroGerente;
    }
    
    public boolean isTieneSucursal() {
        return tieneSucursal;
    }
    
    public void setTieneSucursal(boolean tieneSucursal) {
        this.tieneSucursal = tieneSucursal;
    }
    
    public String getSucursal() {
        return sucursal;
    }
    
    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }
    
    @Override
    public String toString() {
        return "Gerente{" +
                "numeroGerente=" + numeroGerente +
                ", tieneSucursal=" + tieneSucursal +
                ", sucursal='" + sucursal + '\'' +
                ", " + super.toString() +
                '}';
    }
}