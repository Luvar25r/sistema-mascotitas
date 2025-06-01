package modelo;

public class Cliente extends Persona {
    private int numeroCliente;
    private boolean tieneMascota;

    public Cliente(String nombre, String apellidoPaterno, String apellidoMaterno, String curp, int numeroCliente) {
        super(nombre, apellidoPaterno, apellidoMaterno, curp);
        this.numeroCliente = numeroCliente;
        this.tieneMascota = false;
    }

    // Getters y setters
    public int getNumeroCliente() {
        return numeroCliente;
    }

    public void setNumeroCliente(int numeroCliente) {
        this.numeroCliente = numeroCliente;
    }

    public boolean tieneMascota() {
        return tieneMascota;
    }

    public void setTieneMascota(boolean tieneMascota) {
        this.tieneMascota = tieneMascota;
    }
}
