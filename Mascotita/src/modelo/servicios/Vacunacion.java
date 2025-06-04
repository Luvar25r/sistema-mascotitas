package modelo.servicios;

import modelo.Servicio;
import modelo.Mascota;
import modelo.personas.Veterinario;
import modelo.Asistente;
import interfaces.RevisionDeCitas;

import java.time.LocalDateTime;
import java.util.Date;

public class Vacunacion extends Servicio implements RevisionDeCitas {
    
    public Vacunacion() {
        super("Vacunación", "Aplicación de vacunas para mascotas", 350.0);
    }

    @Override
    public boolean veterinarioDisponible(Veterinario veterinario, Date fechaHora) {
        return veterinario != null; // Necesario
    }

    @Override
    public boolean asistenteDisponible(Asistente asistente, Date fechaHora) {
        return true; // No necesario
    }

    @Override
    public boolean mascotaVacunada(Mascota mascota) {
        return true; // No necesario para vacunación
    }

    @Override
    public boolean revisarDisponibilidad(Date fechaHora) {
        // Implementar lógica de disponibilidad
        return fechaHora.after(new Date());
    }

    @Override
    public boolean revisarDisponibilidad(LocalDateTime fechaHora) {
        return false;
    }

    @Override
    public boolean veterinarioDisponible() {
        return false;
    }

    @Override
    public boolean mascotaVacunada() {
        return false;
    }

    @Override
    public boolean revisarDisponibilidad() {
        return false;
    }

    @Override
    public boolean requiereVeterinario() {
        return true;
    }

    @Override
    public boolean requiereAsistente() {
        return false;
    }

    @Override
    public boolean requiereVacunas() {
        return true; // Debería ser true para un servicio de vacunación
    }
}