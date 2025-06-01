package modulos;

import modelo.*;
import modelo.servicios.*;
import excepciones.*;
import interfaces.RevisionDeCitas;
import datos.CitaDAO;
import util.GestorArchivos;
import utils.OrdenadorBase;
import java.util.*;
import java.time.LocalDateTime;

public class AdministracionCitas {
    private CitaDAO citaDAO;

    public AdministracionCitas() {
        this.citaDAO = new CitaDAO();
    }

    public void registrarCita(Cita cita, String tipoServicio) throws CitaOcupadaException {
        RevisionDeCitas revision = obtenerRevisionPorServicio(tipoServicio);

        if (!revision.revisarDisponibilidad(cita.getFechaHora())) {
            throw new CitaOcupadaException("No puede agendar la cita, ya se encuentra ocupada");
        }

        if (revision.requiereVeterinario() && !revision.veterinarioDisponible()) {
            throw new NoHayVeterinariosException("No hay veterinarios disponibles");
        }

        if (revision.requiereAsistente() && !revision.asistenteDisponible()) {
            throw new NoHayAsistentesException("No hay asistentes disponibles");
        }

        if (revision.requiereVacunas() && !revision.mascotaVacunada(cita.getMascota())) {
            throw new MascotaSinVacunasException("La mascota debe estar vacunada para este servicio");
        }

        citaDAO.guardarCita(cita);
        actualizarEstadoCitas();
    }

    public List<Cita> consultarCitasAgendadas() {
        List<Cita> citas = citaDAO.obtenerCitasAgendadas();
        citas.sort(OrdenadorBase.crearComparador(Cita::getFechaHora, true));
        return citas;
    }

    public List<Cita> consultarCitasPasadas(String criterioOrden, boolean ascendente) {
        List<Cita> citasPasadas = citaDAO.obtenerCitasPasadas();

        Comparator<Cita> comparador = obtenerComparadorPorCriterio(criterioOrden, ascendente);
        citasPasadas.sort(comparador);

        return citasPasadas;
    }

    public boolean exportarCitasPasadas(String criterioOrden, boolean ascendente, String extension) {
        List<Cita> citasPasadas = consultarCitasPasadas(criterioOrden, ascendente);
        return GestorArchivos.exportarCitasPasadas(citasPasadas, criterioOrden, extension);
    }

    private void actualizarEstadoCitas() {
        LocalDateTime ahora = LocalDateTime.now();
        citaDAO.actualizarEstadoCitas(ahora);
    }

    private Comparator<Cita> obtenerComparadorPorCriterio(String criterioOrden, boolean ascendente) {
        switch(criterioOrden.toLowerCase()) {
            case "fecha":
                return OrdenadorBase.crearComparador(Cita::getFechaHora, ascendente);
                
            case "nombre":
                return OrdenadorBase.crearComparadorStringInsensitive(
                    cita -> cita.getCliente().getNombre(), ascendente);
                
            case "apellidopaterno":
                return OrdenadorBase.crearComparadorStringInsensitive(
                    cita -> cita.getCliente().getApellidoPaterno(), ascendente);
                
            case "apellidomaterno":
                return OrdenadorBase.crearComparadorConDefault(
                    cita -> cita.getCliente().getApellidoMaterno(), "", ascendente);
                
            default:
                // Por defecto ordenar por fecha
                return OrdenadorBase.crearComparador(Cita::getFechaHora, true);
        }
    }

    private RevisionDeCitas obtenerRevisionPorServicio(String tipoServicio) {
        switch(tipoServicio.toLowerCase()) {
            case "corte":
                return new ServicioCorte();
            case "baño":
            case "banio":
                return new ServicioBanio();
            case "desparasitacion":
            case "desparasitación":
                return new ServicioDesparasitacion();
            case "esterilizacion":
            case "esterilización":
                return new ServicioEsterilizacion();
            case "vacunacion":
            case "vacunación":
                return new ServicioVacunacion();
            case "consulta":
            case "consulta medica":
            case "consulta médica":
                return new ServicioConsultaMedica();
            default:
                throw new IllegalArgumentException("Tipo de servicio no reconocido: " + tipoServicio);
        }
    }
}