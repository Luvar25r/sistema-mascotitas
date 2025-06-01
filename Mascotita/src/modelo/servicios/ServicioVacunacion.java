package modelo.servicios;

import modelo.Mascota;
import modelo.Servicio;
import java.time.LocalDateTime;

public class ServicioVacunacion extends Servicio {

    public ServicioVacunacion() {
        super("Vacunación", "Servicio de vacunación para mascotas", 180.0);
    }

    @Override
    public boolean veterinarioDisponible() {
        // Implementación para verificar disponibilidad de veterinarios
        return true; // Placeholder
    }

    @Override
    public boolean asistenteDisponible() {
        // Implementación para verificar disponibilidad de asistentes
        return true; // Placeholder
    }

    @Override
    public boolean mascotaVacunada(Mascota mascota) {
        // No es necesario que la mascota esté vacunada para recibir vacunación
        return true;
    }

    @Override
    public boolean revisarDisponibilidad(LocalDateTime fechaHora) {
        // Implementación para verificar disponibilidad de horario
        return true; // Placeholder
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
