package interfaces;

import modelo.Mascota;
import java.time.LocalDateTime;

public interface RevisionDeCitas {
    boolean veterinarioDisponible();
    boolean asistenteDisponible();
    boolean mascotaVacunada(Mascota mascota);
    boolean revisarDisponibilidad(LocalDateTime fechaHora);

    // Métodos auxiliares para verificar requisitos
    boolean requiereVeterinario();
    boolean requiereAsistente();
    boolean requiereVacunas();

}
