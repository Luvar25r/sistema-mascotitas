package modelo;

import java.time.LocalDateTime;

public class Cita {
    private int numeroCita;
    private LocalDateTime fechaHora;
    private Cliente cliente;
    private Mascota mascota;
    private Servicio[] serviciosContratados;
    private Veterinario veterinario;
    private Asistente asistente;
    private String descripcionServicio;

    public Cita(int numeroCita, LocalDateTime fechaHora, Cliente cliente, Mascota mascota) {
        this.numeroCita = numeroCita;
        this.fechaHora = fechaHora;
        this.cliente = cliente;
        this.mascota = mascota;
    }

    // Getters y setters
    public int getNumeroCita() {
        return numeroCita;
    }

    public void setNumeroCita(int numeroCita) {
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

    public Servicio[] getServiciosContratados() {
        return serviciosContratados;
    }

    public void setServiciosContratados(Servicio[] serviciosContratados) {
        this.serviciosContratados = serviciosContratados;
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

    public String getDescripcionServicio() {
        return descripcionServicio;
    }

    public void setDescripcionServicio(String descripcionServicio) {
        this.descripcionServicio = descripcionServicio;
    }
}
