package interfaces;

/**
 * Interface para verificaciones necesarias al agendar citas
 */
public interface RevisionDeCitas {
    boolean veterinarioDisponible();
    boolean asistenteDisponible();
    boolean mascotaVacunada();
    boolean revisarDisponibilidad();
}