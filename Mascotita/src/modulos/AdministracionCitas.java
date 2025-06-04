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
import java.time.ZoneId;

public class AdministracionCitas {
    private final CitaDAO citaDAO;

    public AdministracionCitas() {
        this.citaDAO = new CitaDAO();
    }

    /**
     * Registra una nueva cita verificando todas las condiciones necesarias.
     * @throws CitaOcupadaException si la fecha y hora ya están ocupadas
     * @throws NoHayVeterinariosDisponiblesException si se requiere veterinario y no hay disponible
     * @throws NoHayAsistentesDisponiblesException si se requiere asistente y no hay disponible
     * @throws MascotaSinVacunasException si se requieren vacunas y la mascota no está vacunada
     * @throws IllegalArgumentException si los parámetros son inválidos
     */
    public void registrarCita(Cita cita, String tipoServicio) 
            throws CitaOcupadaException, NoHayVeterinariosDisponiblesException, 
                   NoHayAsistentesDisponiblesException, MascotaSinVacunasException {
        
        if (cita == null || tipoServicio == null || tipoServicio.trim().isEmpty()) {
            throw new IllegalArgumentException("La cita y el tipo de servicio son obligatorios");
        }

        RevisionDeCitas revision = obtenerRevisionPorServicio(tipoServicio);
        
        // Convertir LocalDateTime a Date para compatibilidad
        Date fechaHoraDate = Date.from(cita.getFechaHora().atZone(ZoneId.systemDefault()).toInstant());

        if (!revision.revisarDisponibilidad(fechaHoraDate)) {
            throw new CitaOcupadaException("La fecha y hora seleccionadas no están disponibles");
        }

        if (revision.requiereVeterinario()) {
            if (cita.getVeterinario() == null) {
                throw new IllegalArgumentException("Se requiere asignar un veterinario para este servicio");
            }
            if (!revision.veterinarioDisponible(cita.getVeterinario(), fechaHoraDate)) {
                throw new NoHayVeterinariosDisponiblesException();
            }
        }

        if (revision.requiereAsistente()) {
            if (cita.getAsistente() == null) {
                throw new IllegalArgumentException("Se requiere asignar un asistente para este servicio");
            }
            if (!revision.asistenteDisponible(cita.getAsistente(), fechaHoraDate)) {
                throw new NoHayAsistentesDisponiblesException();
            }
        }

        if (revision.requiereVacunas() && !revision.mascotaVacunada(cita.getMascota())) {
            throw new MascotaSinVacunasException("La mascota debe estar vacunada para este servicio");
        }

        citaDAO.guardarCita(cita);
        actualizarEstadoCitas();
    }

    /**
     * Obtiene la lista de citas agendadas ordenadas por fecha más cercana
     */
    public List<Cita> consultarCitasAgendadas() {
        List<Cita> citas = citaDAO.obtenerCitasAgendadas();
        Collections.sort(citas, OrdenadorBase.crearComparador(Cita::getFechaHora, true));
        return Collections.unmodifiableList(citas);
    }

    /**
     * Obtiene la lista de citas pasadas ordenadas según el criterio especificado
     */
    public List<Cita> consultarCitasPasadas(String criterioOrden, boolean ascendente) {
        if (criterioOrden == null || criterioOrden.trim().isEmpty()) {
            criterioOrden = "fecha"; // criterio por defecto
        }
        
        List<Cita> citasPasadas = citaDAO.obtenerCitasPasadas();
        Comparator<Cita> comparador = obtenerComparadorPorCriterio(criterioOrden, ascendente);
        citasPasadas.sort(comparador);
        
        return Collections.unmodifiableList(citasPasadas);
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
            case "baño":
            case "banio":
                return new Banio();
            case "vacunacion":
            case "vacunación":
                return new Vacunacion();
            case "consulta":
            case "consulta medica":
            case "consulta médica":
                return new ConsultaMedica();
            default:
                throw new IllegalArgumentException("Tipo de servicio no reconocido: " + tipoServicio);
        }
    }

    public void actualizarCita(Cita cita) {
        if (cita == null) {
            throw new IllegalArgumentException("La cita no puede ser nula");
        }
        citaDAO.actualizarCita(cita);
    }

    public void cancelarCita(int idCita) {
        citaDAO.eliminarCita(idCita);
    }

    public Cita buscarCitaPorId(int idCita) {
        return citaDAO.obtenerCitaPorId(idCita);
    }
}