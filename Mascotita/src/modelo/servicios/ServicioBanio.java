package modelo.servicios;

import modelo.Mascota;
import modelo.Servicio;
import java.time.LocalDateTime;

public class ServicioBanio extends Servicio {

    public ServicioBanio() {
        super("Baño", "Servicio de baño para mascotas", 120.0);
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
        return mascota.tieneVacunas();
    }

    @Override
    public boolean revisarDisponibilidad(LocalDateTime fechaHora) {
        // Implementación para verificar disponibilidad de horario
        return true; // Placeholder
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
}
