package interfaces;

import modelo.Mascota;
import modelo.Veterinario;
import modelo.Asistente;
import java.util.Date;

/**
 * Interface para verificaciones necesarias al agendar citas
 */
public interface RevisionDeCitas {
    boolean veterinarioDisponible(Veterinario veterinario, Date fechaHora);
    boolean asistenteDisponible(Asistente asistente, Date fechaHora);
    boolean mascotaVacunada(Mascota mascota);
    boolean revisarDisponibilidad(Date fechaHora); // Obligatorio
    boolean requiereVeterinario();
    boolean requiereAsistente();
    boolean requiereVacunas();
}