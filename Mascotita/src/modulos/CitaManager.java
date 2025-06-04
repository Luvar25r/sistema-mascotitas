package modulos;

import modelo.*;
import interfaces.RevisionDeCitas;
import excepciones.*;
import modelo.personas.Veterinario;

import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Clase para administración completa de citas
 */
public class CitaManager {
    private List<Cita> citasAgendadas;
    private List<Cita> citasPasadas;
    private List<Veterinario> veterinariosDisponibles;
    private List<Asistente> asistentesDisponibles;

    public enum OrdenCitas {
        FECHA_ASC, FECHA_DESC, 
        CLIENTE_NOMBRE_ASC, CLIENTE_NOMBRE_DESC,
        APELLIDO_PATERNO_ASC, APELLIDO_PATERNO_DESC,
        APELLIDO_MATERNO_ASC, APELLIDO_MATERNO_DESC
    }

    public CitaManager() {
        this.citasAgendadas = new ArrayList<>();
        this.citasPasadas = new ArrayList<>();
        this.veterinariosDisponibles = new ArrayList<>();
        this.asistentesDisponibles = new ArrayList<>();
    }

    public void registrarCita(Cita cita) throws CitaOcupadaException {
        
        for (Servicio servicio : cita.getServicios()) {
            if (servicio instanceof RevisionDeCitas) {
                RevisionDeCitas revision = (RevisionDeCitas) servicio;
                
                // Convertir LocalDateTime a Date para compatibilidad
                Date fechaHoraDate = Date.from(cita.getFechaHora().atZone(ZoneId.systemDefault()).toInstant());
                
                // Verificar disponibilidad de fecha y hora
                if (!revision.revisarDisponibilidad(fechaHoraDate)) {
                    throw new CitaOcupadaException();
                }
                
                // Verificar veterinario si es necesario
                if (cita.getVeterinario() != null) {
                    if (!revision.veterinarioDisponible(cita.getVeterinario(), fechaHoraDate)) {
                        throw new RuntimeException("No hay veterinarios disponibles");
                    }
                }
                
                // Verificar asistente si es necesario
                if (cita.getAsistente() != null) {
                    if (!revision.asistenteDisponible(cita.getAsistente(), fechaHoraDate)) {
                        throw new RuntimeException("No hay asistentes disponibles");
                    }
                }
                
                // Verificar vacunas de mascota
                if (!revision.mascotaVacunada(cita.getMascota())) {
                    throw new RuntimeException("La mascota no tiene las vacunas necesarias para este servicio");
                }
            }
        }
        
        citasAgendadas.add(cita);
        Collections.sort(citasAgendadas, Comparator.comparing(Cita::getFechaHora));
    }

    public void moverCitasPasadas() {
        LocalDateTime ahora = LocalDateTime.now();
        Iterator<Cita> iterator = citasAgendadas.iterator();
        
        while (iterator.hasNext()) {
            Cita cita = iterator.next();
            if (cita.getFechaHora().isBefore(ahora)) {
                citasPasadas.add(cita);
                iterator.remove();
            }
        }
    }

    public List<Cita> obtenerCitasAgendadas() {
        moverCitasPasadas();
        return new ArrayList<>(citasAgendadas);
    }

    public List<Cita> listarCitasPasadas(OrdenCitas orden) {
        List<Cita> lista = new ArrayList<>(citasPasadas);
        
        switch (orden) {
            case FECHA_ASC:
                lista.sort(Comparator.comparing(Cita::getFechaHora));
                break;
            case FECHA_DESC:
                lista.sort(Comparator.comparing(Cita::getFechaHora).reversed());
                break;
            case CLIENTE_NOMBRE_ASC:
                lista.sort(Comparator.comparing(c -> c.getCliente().getNombre()));
                break;
            case CLIENTE_NOMBRE_DESC:
                lista.sort(Comparator.comparing((Cita c) -> c.getCliente().getNombre()).reversed());
                break;
            case APELLIDO_PATERNO_ASC:
                lista.sort(Comparator.comparing(c -> c.getCliente().getApellidoPaterno()));
                break;
            case APELLIDO_PATERNO_DESC:
                lista.sort(Comparator.comparing((Cita c) -> c.getCliente().getApellidoPaterno()).reversed());
                break;
            case APELLIDO_MATERNO_ASC:
                lista.sort(Comparator.comparing(c -> c.getCliente().getApellidoMaterno() != null ? 
                    c.getCliente().getApellidoMaterno() : ""));
                break;
            case APELLIDO_MATERNO_DESC:
                lista.sort(Comparator.comparing((Cita c) -> c.getCliente().getApellidoMaterno() != null ? 
                    c.getCliente().getApellidoMaterno() : "").reversed());
                break;
            default:
                // Orden por defecto
                lista.sort(Comparator.comparing(Cita::getFechaHora));
                break;
        }
        
        return lista;
    }

    public void exportarCitasAArchivo(List<Cita> citas, OrdenCitas orden, String formato) throws IOException {
        String nombreArchivo = generarNombreArchivo(orden, formato);
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            if (formato.equalsIgnoreCase("csv")) {
                writer.println("Numero Cita,Fecha,Cliente,Mascota,Servicios,Veterinario,Asistente");
                for (Cita cita : citas) {
                    writer.println(formatearCitaCSV(cita));
                }
            } else {
                writer.println("=== REGISTRO DE CITAS MASCOTITAS ===");
                writer.println("Orden: " + orden);
                writer.println("Fecha de generación: " + new Date());
                writer.println("========================================");
                
                for (Cita cita : citas) {
                    writer.println(cita.toString());
                    writer.println("----------------------------------------");
                }
            }
        }
        
        System.out.println("Archivo exportado: " + nombreArchivo);
    }

    private String generarNombreArchivo(OrdenCitas orden, String formato) {
        Date fecha = new Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("ddMMyyyy");
        String fechaStr = sdf.format(fecha);
        
        return fechaStr + "_RegistroCitasMascotitas_" + orden.name() + "." + formato.toLowerCase();
    }

    private String formatearCitaCSV(Cita cita) {
        StringBuilder sb = new StringBuilder();
        sb.append(cita.getNumeroCita()).append(",");
        sb.append(cita.getFechaHora()).append(",");
        sb.append(cita.getCliente().getNombreCompleto()).append(",");
        sb.append(cita.getMascota().getNombre()).append(",");
        sb.append("\"").append(cita.getServicios().toString()).append("\",");
        sb.append(cita.getVeterinario() != null ? cita.getVeterinario().getNombreCompleto() : "N/A").append(",");
        sb.append(cita.getAsistente() != null ? cita.getAsistente().getNombreCompleto() : "N/A");
        
        return sb.toString();
    }

    // Getters para listas
    public List<Veterinario> getVeterinariosDisponibles() { return veterinariosDisponibles; }
    public List<Asistente> getAsistentesDisponibles() { return asistentesDisponibles; }
    
    public void agregarVeterinario(Veterinario veterinario) { veterinariosDisponibles.add(veterinario); }
    public void agregarAsistente(Asistente asistente) { asistentesDisponibles.add(asistente); }
}