package modelo.servicios;

import modelo.Servicio;
import modelo.Mascota;
import modelo.Veterinario;
import modelo.Asistente;
import interfaces.RevisionDeCitas;

import java.time.LocalDateTime;
import java.util.Date;

public class ConsultaMedica extends Servicio implements RevisionDeCitas {
    
    public ConsultaMedica() {
        super("Consulta Médica", "Consulta médica veterinaria general", 500.0);
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
        return true; // No necesario
    }

    @Override
    public boolean revisarDisponibilidad(Date fechaHora) {
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
    public boolean asistenteDisponible() {
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
        return false;
    }
}