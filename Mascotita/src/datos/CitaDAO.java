package datos;

import modelo.Cita;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class CitaDAO {
    private List<Cita> citasAgendadas;
    private List<Cita> citasPasadas;

    public CitaDAO() {
        this.citasAgendadas = new ArrayList<>();
        this.citasPasadas = new ArrayList<>();
    }

    public void guardarCita(Cita cita) {
        citasAgendadas.add(cita);
    }

    public List<Cita> obtenerCitasAgendadas() {
        return new ArrayList<>(citasAgendadas);
    }

    public List<Cita> obtenerCitasPasadas() {
        return new ArrayList<>(citasPasadas);
    }

    public void actualizarEstadoCitas(LocalDateTime ahora) {
        List<Cita> citasAMover = new ArrayList<>();

        for (Cita cita : citasAgendadas) {
            if (cita.getFechaHora().isBefore(ahora)) {
                citasAMover.add(cita);
            }
        }

        citasAgendadas.removeAll(citasAMover);
        citasPasadas.addAll(citasAMover);
    }

    public boolean verificarDisponibilidadHorario(LocalDateTime fechaHora) {
        for (Cita cita : citasAgendadas) {
            if (cita.getFechaHora().equals(fechaHora)) {
                return false;
            }
        }
        return true;
    }
}
