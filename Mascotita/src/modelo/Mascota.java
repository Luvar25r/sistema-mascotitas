package modelo;

import java.util.List;
import java.util.ArrayList;

public class Mascota {
    private String id;
    private String nombre;
    private List<String> vacunas;

    public Mascota(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.vacunas = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<String> getVacunas() {
        return vacunas;
    }

    public void agregarVacuna(String vacuna) {
        if (!vacunas.contains(vacuna)) {
            vacunas.add(vacuna);
        }
    }

    @Override 
    public String toString() {
        return "Mascota{id='" + id + "', nombre='" + nombre + "', vacunas=" + vacunas + "}";
    }

    public boolean isVacunada() {
        return this.vacunas != null && !this.vacunas.isEmpty();
    }

    public boolean tieneVacunas() {
        return isVacunada();
    }
}