package modelo;

public enum Zona {
    NORTE("Norte"),
    SUR("Sur"),
    ESTE("Este"),
    OESTE("Oeste"),
    CENTRO("Centro"),
    METROPOLITANA("Área Metropolitana");

    private final String descripcion;

    Zona(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}