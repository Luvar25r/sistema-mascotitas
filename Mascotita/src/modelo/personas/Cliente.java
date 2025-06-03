package modelo.personas;

import modelo.Persona;
import java.util.Date;

public class Cliente extends Persona implements Comparable<Cliente> {
    // Contador estático para autoincrementar números de cliente
    private static int contadorClientes = 1000; // Inicia en 1000 para tener números más profesionales
    
    private String id;
    private int numeroCliente; // Número autoincrementable
    private String telefono;
    private String email;
    private boolean tieneMascota;

    // Constructor completo
    public Cliente(String nombre, String apellidoPaterno, String apellidoMaterno, 
                 Date fechaNacimiento, String curp, String telefono, String email, boolean tieneMascota) {
        super(nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, curp);
        this.numeroCliente = generarNumeroCliente();
        this.id = "CLI-" + this.numeroCliente; // Formato: CLI-1001, CLI-1002, etc.
        this.telefono = telefono;
        this.email = email;
        this.tieneMascota = tieneMascota;
    }

    // Constructor sin apellido materno
    public Cliente(String nombre, String apellidoPaterno, 
                 Date fechaNacimiento, String curp, String telefono, String email, boolean tieneMascota) {
        super(nombre, apellidoPaterno, fechaNacimiento, curp);
        this.numeroCliente = generarNumeroCliente();
        this.id = "CLI-" + this.numeroCliente;
        this.telefono = telefono;
        this.email = email;
        this.tieneMascota = tieneMascota;
    }

    // Constructor para migración de datos existentes (cuando ya tienes un número específico)
    public Cliente(int numeroClienteEspecifico, String nombre, String apellidoPaterno, String apellidoMaterno, 
                 Date fechaNacimiento, String curp, String telefono, String email, boolean tieneMascota) {
        super(nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, curp);
        this.numeroCliente = numeroClienteEspecifico;
        this.id = "CLI-" + this.numeroCliente;
        this.telefono = telefono;
        this.email = email;
        this.tieneMascota = tieneMascota;
        
        // Actualizar el contador si el número específico es mayor
        if (numeroClienteEspecifico >= contadorClientes) {
            contadorClientes = numeroClienteEspecifico + 1;
        }
    }

    /**
     * Genera automáticamente un número de cliente único y autoincrementable
     * @return nuevo número de cliente
     */
    public static synchronized int generarNumeroCliente() {
        return contadorClientes++;
    }

    /**
     * Obtiene el siguiente número de cliente que se asignará (sin incrementar)
     * @return próximo número de cliente
     */
    public static int getSiguienteNumeroCliente() {
        return contadorClientes;
    }

    /**
     * Establece el contador de clientes (útil para inicializar desde base de datos)
     * @param nuevoContador valor inicial del contador
     */
    public static void setContadorClientes(int nuevoContador) {
        contadorClientes = Math.max(nuevoContador, 1000); // Mínimo 1000
    }

    /**
     * Reinicia el contador de clientes (usar con precaución)
     */
    public static void reiniciarContador() {
        contadorClientes = 1000;
    }

    /**
     * Obtiene estadísticas de clientes registrados
     * @return número total de clientes registrados
     */
    public static int getTotalClientesRegistrados() {
        return contadorClientes - 1000; // Restamos el valor inicial
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public int getNumeroCliente() {
        return numeroCliente;
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

    public boolean isTieneMascota() {
        return tieneMascota;
    }

    public void setTieneMascota(boolean tieneMascota) {
        this.tieneMascota = tieneMascota;
    }

    @Override
    public int compareTo(Cliente otro) {
        return Integer.compare(this.numeroCliente, otro.numeroCliente);
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

    @Override
    public String toString() {
        return String.format("Cliente{numeroCliente=%d, id='%s', nombre='%s', telefono='%s', email='%s', tieneMascota=%s}", 
                           numeroCliente, id, getNombreCompleto(), telefono, email, tieneMascota);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cliente cliente = (Cliente) obj;
        return numeroCliente == cliente.numeroCliente;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(numeroCliente);
    }
}