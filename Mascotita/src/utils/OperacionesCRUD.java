package utils;

public abstract class OperacionesCRUD<T> {
    protected abstract boolean alta(T elemento);
    protected abstract boolean baja(T elemento);
    protected abstract boolean edicion(T elementoAntiguo, T elementoNuevo);

    // Métodos comunes que pueden ser utilizados por todas las clases
    protected abstract boolean validarDatos(T elemento);
    protected abstract void notificarCambio(String tipoOperacion, T elemento);
}