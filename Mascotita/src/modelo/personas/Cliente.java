package modelo.personas;

import modelo.Persona;
import java.util.Date;

public class Cliente extends Persona implements Comparable<Cliente> {
    private String id;
    private String telefono;
    private String email;

    public Cliente(String id, String nombre, String apellidoPaterno, String apellidoMaterno, 
                 Date fechaNacimiento, String curp, String telefono, String email) {
        super(nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, curp);
        this.id = id;
        this.telefono = telefono;
        this.email = email;
    }

    public Cliente(String id, String nombre, String apellidoPaterno, 
                 Date fechaNacimiento, String curp, String telefono, String email) {
        super(nombre, apellidoPaterno, fechaNacimiento, curp);
        this.id = id;
        this.telefono = telefono;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int compareTo(Cliente otro) {
        return this.id.compareTo(otro.id);
    }

    @Override
    public String getNombreCompleto() {
        StringBuilder sb = new StringBuilder();
        sb.append(nombre != null ? nombre : "");
        if (apellidoPaterno != null && !apellidoPaterno.isEmpty()) {
            sb.append(" ").append(apellidoPaterno);
        }
        if (apellidoMaterno != null && !apellidoMaterno.isEmpty()) {
            sb.append(" ").append(apellidoMaterno);
        }
        return sb.toString().trim();
    }
}