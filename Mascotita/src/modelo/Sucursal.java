package modelo;

/**
 * Enum para representar las sucursales predefinidas de la cadena
 */
public enum Sucursal {
    SUCURSAL_NORTE("Mascotitas Norte", "SUC001", Zona.NORTE),
    SUCURSAL_SUR("Mascotitas Sur", "SUC002", Zona.SUR),
    SUCURSAL_ESTE("Mascotitas Este", "SUC003", Zona.ESTE),
    SUCURSAL_OESTE("Mascotitas Oeste", "SUC004", Zona.OESTE),
    SUCURSAL_CENTRO("Mascotitas Centro", "SUC005", Zona.CENTRO),
    SUCURSAL_METROPOLITANA("Mascotitas Metropolitana", "SUC006", Zona.METROPOLITANA);

    private final String nombre;
    private final String codigoSucursal;
    private final Zona zona;

    Sucursal(String nombre, String codigoSucursal, Zona zona) {
        this.nombre = nombre;
        this.codigoSucursal = codigoSucursal;
        this.zona = zona;
    }

    // Getters (sin setters ya que es inmutable)
    public String getNombre() { 
        return nombre; 
    }

    public String getCodigoSucursal() { 
        return codigoSucursal; 
    }

    public Zona getZona() { 
        return zona; 
    }

    @Override
    public String toString() {
        return nombre + " (" + codigoSucursal + ") - " + zona.getDescripcion();
    }

    // Métodos de utilidad para buscar sucursales
    public static Sucursal buscarPorCodigo(String codigo) {
        for (Sucursal sucursal : values()) {
            if (sucursal.getCodigoSucursal().equalsIgnoreCase(codigo)) {
                return sucursal;
            }
        }
        return null;
    }

    public static Sucursal[] buscarPorZona(Zona zona) {
        return java.util.Arrays.stream(values())
                .filter(s -> s.getZona() == zona)
                .toArray(Sucursal[]::new);
    }
}
