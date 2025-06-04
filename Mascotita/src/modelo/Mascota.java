package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Mascota {
    private String id;
    private String nombre;
    private String raza;
    private List<String> vacunas;
    private boolean vacunada;

    public Mascota(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.vacunas = new ArrayList<>();
        this.vacunada = false;
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public List<String> getVacunas() {
        return new ArrayList<>(vacunas);
    }

    public boolean isVacunada() {
        return !vacunas.isEmpty();
    }

    public void agregarVacuna(String vacuna) {
        if (vacuna != null && !vacuna.trim().isEmpty()) {
            vacunas.add(vacuna.trim());
            vacunada = true;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mascota mascota = (Mascota) o;
        return Objects.equals(id, mascota.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean tieneVacunas() {
        return false;
    }
}