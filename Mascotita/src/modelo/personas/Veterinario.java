package modelo.personas;

import modelo.Persona;
import utils.OrdenadorBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Veterinario extends Persona {
    private String cedula;
    private String especialidad;
    private Date fechaContratacion;
    private double salario;

    public Veterinario() {
        super();
    }

    public Veterinario(String nombre, String apellidoPaterno, String apellidoMaterno, 
                       Date fechaNacimiento, String curp, String cedula, 
                       String especialidad, Date fechaContratacion, double salario) {
        super(nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, curp);
        this.cedula = cedula;
        this.especialidad = especialidad;
        this.fechaContratacion = fechaContratacion;
        this.salario = salario;
    }

    // Getters y setters
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    // Clases internas para los comparadores
    public static class ComparadorNombreAsc implements Comparator<Veterinario> {
        @Override
        public int compare(Veterinario v1, Veterinario v2) {
            return v1.getNombre().compareToIgnoreCase(v2.getNombre());
        }
    }

    public static class ComparadorNombreDesc implements Comparator<Veterinario> {
        @Override
        public int compare(Veterinario v1, Veterinario v2) {
            return v2.getNombre().compareToIgnoreCase(v1.getNombre());
        }
    }

    // Métodos de ejemplo para utilizar OrdenadorBase con Veterinario
    public static void ejemploOrdenamiento() {
        List<Veterinario> listaVeterinarios = new ArrayList<>();

        // Ejemplo usando clases comparador explícitas
        // Orden ascendente
        Collections.sort(listaVeterinarios, new ComparadorNombreAsc());

        // Orden descendente
        Collections.sort(listaVeterinarios, new ComparadorNombreDesc());

        // Ejemplos usando OrdenadorBase (más flexible y conciso)
        // Ordenar por nombre ascendente
        listaVeterinarios.sort(OrdenadorBase.crearComparador(Veterinario::getNombre, true));

        // Ordenar por nombre descendente
        listaVeterinarios.sort(OrdenadorBase.crearComparador(Veterinario::getNombre, false));

        // Ordenar por apellido paterno (insensible a mayúsculas/minúsculas)
        listaVeterinarios.sort(OrdenadorBase.crearComparadorStringInsensitive(
                Veterinario::getApellidoPaterno, true));

        // Ordenar por especialidad y luego por fecha de contratación
        listaVeterinarios.sort(OrdenadorBase.crearComparadorCompuesto(
                OrdenadorBase.crearComparador(Veterinario::getEspecialidad, true),
                OrdenadorBase.crearComparador(Veterinario::getFechaContratacion, false)
        ));

        // Ordenar por salario descendente
        listaVeterinarios.sort(OrdenadorBase.crearComparador(Veterinario::getSalario, false));
    }
}

