package modelo;

import java.time.LocalDateTime;

/**
 * Clase base para todos los servicios del sistema
 */
public abstract class Servicio implements Comparable<Servicio>, interfaces.RevisionDeCitas {
    protected String nombre;
    protected double precio;
    protected String descripcion;

    public Servicio(String nombre, String descripcion, double precio) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public int compareTo(Servicio otro) {
        return this.nombre.compareTo(otro.nombre);
    }

    @Override
    public String toString() {
        return "Servicio{" +
                "nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    // Métodos abstractos adicionales requeridos por las implementaciones
    public abstract boolean veterinarioDisponible();
    public abstract boolean asistenteDisponible();
    public abstract boolean requiereVeterinario();
    public abstract boolean requiereAsistente();
    public abstract boolean requiereVacunas();

    @Override
    public boolean veterinarioDisponible(modelo.Veterinario veterinario, java.util.Date fechaHora) {
        return veterinarioDisponible();
    }

    @Override
    public boolean asistenteDisponible(modelo.Asistente asistente, java.util.Date fechaHora) {
        return asistenteDisponible();
    }

    @Override
    public boolean revisarDisponibilidad(java.util.Date fechaHora) {
        return revisarDisponibilidad(java.time.LocalDateTime.ofInstant(
            fechaHora.toInstant(), java.time.ZoneId.systemDefault()));
    }

    public abstract boolean revisarDisponibilidad(LocalDateTime fechaHora);
}