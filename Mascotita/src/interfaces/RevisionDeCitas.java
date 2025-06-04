package interfaces;

import modelo.Asistente;
import modelo.Mascota;
import modelo.personas.Veterinario;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Interface para verificaciones necesarias al agendar citas
 */
public interface RevisionDeCitas {
    boolean veterinarioDisponible();
    boolean asistenteDisponible(Asistente asistente, Date fechaHoraDate);
    boolean mascotaVacunada();
    boolean revisarDisponibilidad();

    boolean revisarDisponibilidad(Date fechaHoraDate);

    boolean veterinarioDisponible(Veterinario veterinario, Date fechaHoraDate);

    boolean mascotaVacunada(Mascota mascota);

    boolean requiereVeterinario();

    boolean requiereAsistente();

    boolean requiereVacunas();

    boolean revisarDisponibilidad(LocalDateTime fechaHora);
}