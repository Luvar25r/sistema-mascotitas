package modelo.servicios;

import interfaces.RevisionDeCitas;
import modelo.Mascota;
import modelo.personas.Veterinario;
import modelo.Asistente;
import java.util.Date;
import java.time.LocalDateTime;

public class Banio implements RevisionDeCitas {
    private String descripcion;
    
    public Banio() {
        this.descripcion = "Servicio de baño para mascotas";
    }
    
    @Override
    public boolean veterinarioDisponible(Veterinario veterinario, Date fechaHora) {
        // Para baño, no se requiere veterinario específico
        return true;
    }
    
    @Override
    public boolean asistenteDisponible(Asistente asistente, Date fechaHora) {
        // Para baño, se requiere asistente disponible
        return asistente != null;
    }
    
    @Override
    public boolean mascotaVacunada(Mascota mascota) {
        // Para baño, se requiere que la mascota esté vacunada
        return mascota != null && mascota.isVacunada();
    }

    @Override
    public boolean veterinarioDisponible() {
        return false;
    }

    @Override
    public boolean mascotaVacunada() {
        return true;
    }

    @Override
    public boolean revisarDisponibilidad() {
        return false;
    }

    @Override
    public boolean revisarDisponibilidad(Date fechaHora) {
        // Método obligatorio para verificar disponibilidad general
        return fechaHora != null && fechaHora.after(new Date());
    }

    @Override
    public boolean requiereVeterinario() {
        return false;
    }

    @Override
    public boolean requiereAsistente() {
        return true;
    }

    @Override
    public boolean requiereVacunas() {
        return true;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        if (descripcion != null && !descripcion.trim().isEmpty()) {
            this.descripcion = descripcion;
        }
    }

    @Override
    public boolean revisarDisponibilidad(LocalDateTime fechaHora) {
        return fechaHora != null && fechaHora.isAfter(LocalDateTime.now());
    }
}