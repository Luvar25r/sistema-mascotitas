package datos;

import modelo.Cita;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    public List<Cita> obtenerCitasOrdenadasPorFecha(boolean ascendente) {
        List<Cita> citas = new ArrayList<>(citasPasadas);
        citas.sort((c1, c2) -> ascendente ? 
            c1.getFechaHora().compareTo(c2.getFechaHora()) :
            c2.getFechaHora().compareTo(c1.getFechaHora()));
        return citas;
    }

    public List<Cita> obtenerCitasOrdenadasPorNombreCliente(boolean ascendente) {
        List<Cita> citas = new ArrayList<>(citasPasadas);
        citas.sort((c1, c2) -> ascendente ?
            c1.getCliente().getNombre().compareTo(c2.getCliente().getNombre()) :
            c2.getCliente().getNombre().compareTo(c1.getCliente().getNombre()));
        return citas;
    }

    public List<Cita> obtenerCitasOrdenadasPorApellidoPaterno(boolean ascendente) {
        List<Cita> citas = new ArrayList<>(citasPasadas);
        citas.sort((c1, c2) -> ascendente ?
            c1.getCliente().getApellidoPaterno().compareTo(c2.getCliente().getApellidoPaterno()) :
            c2.getCliente().getApellidoPaterno().compareTo(c1.getCliente().getApellidoPaterno()));
        return citas;
    }

    public void exportarListadoCitas(List<Cita> citas, String criterioOrden) throws IOException {
        String fechaActual = LocalDateTime.now().toString().substring(0, 10);
        String nombreArchivo = fechaActual + "_RegistroCitasMascotitas_" + criterioOrden + ".csv";
        
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("Fecha,Cliente,Mascota,Servicio\n");
            for (Cita cita : citas) {
                writer.write(String.format("%s,%s,%s,%s\n",
                    cita.getFechaHora(),
                    cita.getCliente().getNombre(),
                    cita.getMascota().getNombre(),
                    cita.getDescripcionServicio()));
            }
        }
    }
}