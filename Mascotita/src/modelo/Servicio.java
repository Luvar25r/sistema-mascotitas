package modelo;

import interfaces.RevisionDeCitas;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Clase base para todos los servicios del sistema
 */
public abstract class Servicio implements Comparable<Servicio>, RevisionDeCitas {
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
    public abstract boolean asistenteDisponible(Asistente asistente, Date fechaHoraDate);
    public abstract boolean requiereVeterinario();
    public abstract boolean requiereAsistente();
    public abstract boolean requiereVacunas();

    public boolean veterinarioDisponible(Veterinario veterinario, Date fechaHora) {
        return veterinarioDisponible();
    }

    public boolean revisarDisponibilidad(Date fechaHora) {
        return revisarDisponibilidad(LocalDateTime.ofInstant(
            fechaHora.toInstant(), ZoneId.systemDefault()));
    }

    public abstract boolean mascotaVacunada(Mascota mascota);

    public abstract boolean revisarDisponibilidad(LocalDateTime fechaHora);

    @Override
    public boolean revisarDisponibilidad() {
        return revisarDisponibilidad(LocalDateTime.now());
    }

    @Override
    public boolean mascotaVacunada() {
        return false;
    }
}