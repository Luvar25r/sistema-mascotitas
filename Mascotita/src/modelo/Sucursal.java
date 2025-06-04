package modelo;

/**
 * Clase para representar una sucursal de la cadena
 */
public class Sucursal {
    private String nombre;
    private String codigoSucursal;
    private Zona zona;

    public Sucursal(String nombre, String codigoSucursal, Zona zona) {
        this.nombre = nombre;
        this.codigoSucursal = codigoSucursal;
        this.zona = zona;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCodigoSucursal() { return codigoSucursal; }
    public void setCodigoSucursal(String codigoSucursal) { this.codigoSucursal = codigoSucursal; }

    public Zona getZona() { return zona; }
    public void setZona(Zona zona) { this.zona = zona; }

    @Override
    public String toString() {
        return "Sucursal{" +
                "nombre='" + nombre + '\'' +
                ", codigoSucursal='" + codigoSucursal + '\'' +
                ", zona=" + zona +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sucursal sucursal = (Sucursal) o;
        return codigoSucursal.equals(sucursal.codigoSucursal);
    }

    @Override
    public int hashCode() {
        return codigoSucursal.hashCode();
    }
}
