package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import modelo.personas.Cliente;
/**
 * Clase para administración de adopciones
 * Representa una adopción que incluye los datos del cliente, la mascota y la fecha
 */
public class Adopcion implements Comparable<Adopcion> {
    private static int contadorAdopciones = 1;
    
    private int idAdopcion;
    private Cliente cliente;
    private Mascota mascota;
    private LocalDate fechaAdopcion;
    private String observaciones;
    
    public Adopcion(Cliente cliente, Mascota mascota, LocalDate fechaAdopcion, String observaciones) {
        this.idAdopcion = generarIdAdopcion();
        this.cliente = cliente;
        this.mascota = mascota;
        this.fechaAdopcion = fechaAdopcion;
        this.observaciones = observaciones != null ? observaciones : "";
    }
    
    public Adopcion(Cliente cliente, Mascota mascota, LocalDate fechaAdopcion) {
        this(cliente, mascota, fechaAdopcion, "");
    }
    
    // Constructor para casos donde ya se tiene un ID específico
    public Adopcion(int idAdopcion, Cliente cliente, Mascota mascota, LocalDate fechaAdopcion, String observaciones) {
        this.idAdopcion = idAdopcion;
        this.cliente = cliente;
        this.mascota = mascota;
        this.fechaAdopcion = fechaAdopcion;
        this.observaciones = observaciones != null ? observaciones : "";
        
        // Actualizar contador si es necesario
        if (idAdopcion >= contadorAdopciones) {
            contadorAdopciones = idAdopcion + 1;
        }
    }
    
    /**
     * Genera automáticamente un ID único para la adopción
     */
    private static synchronized int generarIdAdopcion() {
        return contadorAdopciones++;
    }
    
    // Getters y Setters
    public int getIdAdopcion() {
        return idAdopcion;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Mascota getMascota() {
        return mascota;
    }
    
    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }
    
    public LocalDate getFechaAdopcion() {
        return fechaAdopcion;
    }
    
    public void setFechaAdopcion(LocalDate fechaAdopcion) {
        this.fechaAdopcion = fechaAdopcion;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones != null ? observaciones : "";
    }
    
    /**
     * Obtiene la fecha de adopción formateada
     */
    public String getFechaFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fechaAdopcion.format(formatter);
    }
    
    /**
     * Verifica si la mascota está vacunada (requisito para adopción)
     */
    public boolean mascotaVacunada() {
        return mascota != null && mascota.isVacunada();
    }
    
    /**
     * Obtiene resumen de la adopción
     */
    public String getResumen() {
        return String.format("Adopción #%d - Cliente: %s - Mascota: %s - Fecha: %s",
                idAdopcion,
                cliente != null ? cliente.getNombreCompleto() : "N/A",
                mascota != null ? mascota.getNombre() : "N/A",
                getFechaFormateada());
    }
    
    @Override
    public int compareTo(Adopcion otra) {
        return Integer.compare(this.idAdopcion, otra.idAdopcion);
    }
    
    @Override
    public String toString() {
        return String.format("Adopcion{idAdopcion=%d, cliente='%s', mascota='%s', fechaAdopcion=%s, observaciones='%s'}", 
                           idAdopcion, 
                           cliente != null ? cliente.getNombreCompleto() : "null",
                           mascota != null ? mascota.getNombre() : "null",
                           fechaAdopcion, 
                           observaciones);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Adopcion adopcion = (Adopcion) obj;
        return idAdopcion == adopcion.idAdopcion;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(idAdopcion);
    }
    
    // Métodos estáticos de utilidad
    public static int getSiguienteId() {
        return contadorAdopciones;
    }
    
    public static void setContadorAdopciones(int nuevoContador) {
        contadorAdopciones = Math.max(nuevoContador, 1);
    }
    
    public static void reiniciarContador() {
        contadorAdopciones = 1;
    }
    
    public static int getTotalAdopciones() {
        return contadorAdopciones - 1;
    }
}