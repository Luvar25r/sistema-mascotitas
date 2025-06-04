package modelo;

import modelo.personas.Cliente;
import modelo.personas.Veterinario;

import java.time.LocalDateTime;
import java.util.List;

public class Cita {
    private String numeroCita;
    private LocalDateTime fechaHora;
    private Cliente cliente;
    private Mascota mascota;
    private List<Servicio> servicios;
    private Veterinario veterinario;
    private Asistente asistente;
    
    public Cita() {}
    
                    public Cita(String numeroCita, LocalDateTime fechaHora, Cliente cliente, Mascota mascota, 
                List<Servicio> servicios, Veterinario veterinario, Asistente asistente) {
        this.numeroCita = numeroCita;
        this.fechaHora = fechaHora;
        this.cliente = cliente;
        this.mascota = mascota;
        this.servicios = servicios;
        this.veterinario = veterinario;
        this.asistente = asistente;
    }
    
    public String getNumeroCita() {
        return numeroCita;
    }
    
    public void setNumeroCita(String numeroCita) {
        this.numeroCita = numeroCita;
    }
    
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Mascota getMascota() {
        return mascota;
    }
    
    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }
    
    public List<Servicio> getServicios() {
        return servicios;
    }
    
    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }
    
    public Veterinario getVeterinario() {
        return veterinario;
    }
    
    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }
    
    public Asistente getAsistente() {
        return asistente;
    }
    
    public void setAsistente(Asistente asistente) {
        this.asistente = asistente;
    }
    
    @Override
    public String toString() {
        return "Cita{" +
                "numeroCita='" + numeroCita + '\'' +
                ", fechaHora=" + fechaHora +
                ", cliente=" + (cliente != null ? cliente.getNombreCompleto() : "N/A") +
                ", mascota=" + (mascota != null ? mascota.getNombre() : "N/A") +
                ", servicios=" + servicios +
                ", veterinario=" + (veterinario != null ? veterinario.getNombreCompleto() : "N/A") +
                ", asistente=" + (asistente != null ? asistente.getNombreCompleto() : "N/A") +
                '}';
    }

    public String getDescripcionServicio() {
        if (servicios == null || servicios.isEmpty()) {
            return "Sin servicios";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < servicios.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(servicios.get(i).getNombre());
        }
        return sb.toString();
    }
}