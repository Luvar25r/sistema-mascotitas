package modulos;

import modelo.Producto;
import utils.OperacionesCRUD;
import java.util.*;

/**
 * Gestor para CRUD de productos
 */
public class ProductoManager extends OperacionesCRUD<Producto> {
    private List<Producto> productos;

    public enum OrdenProducto {
        ID_ASC, ID_DESC, NOMBRE_ASC, NOMBRE_DESC
    }

    public ProductoManager() {
        this.productos = new ArrayList<>();
    }

    @Override
    public boolean alta(Producto producto) {
        if (validarDatos(producto)) {
            productos.add(producto);
            notificarCambio("ALTA", producto);
            return true;
        }
        return false;
    }

    @Override
    public boolean baja(Producto producto) {
        boolean eliminado = productos.remove(producto);
        if (eliminado) {
            notificarCambio("BAJA", producto);
        }
        return eliminado;
    }

    @Override
    public boolean edicion(Producto productoAntiguo, Producto productoNuevo) {
        int index = productos.indexOf(productoAntiguo);
        if (index != -1 && validarDatos(productoNuevo)) {
            productos.set(index, productoNuevo);
            notificarCambio("EDICION", productoNuevo);
            return true;
        }
        return false;
    }

    @Override
    public void alta() {

    }

    @Override
    public void baja() {

    }

    @Override
    public void edicion() {

    }

    @Override
    public Producto solicitarDatosAlta() {
        return null;
    }

    @Override
    public Producto solicitarDatosEdicion(Producto elementoExistente) {
        return null;
    }

    @Override
    public void mostrarDetalles(Producto elemento) {

    }

    @Override
    public void mostrarLista() {

    }

    @Override
    protected void mostrarEncabezadoAlta() {

    }

    @Override
    protected void mostrarEncabezadoBaja() {

    }

    @Override
    protected void mostrarEncabezadoEdicion() {

    }

    @Override
    protected Object solicitarIdParaBaja() {
        return null;
    }

    @Override
    protected Object solicitarIdParaEdicion() {
        return null;
    }

    @Override
    protected String getId(Producto elemento) {
        return "";
    }

    @Override
    protected boolean validarDatos(Producto producto) {
        return producto != null && 
               producto.getNombre() != null && !producto.getNombre().trim().isEmpty() &&
               producto.getPrecio() >= 0 &&
               producto.getStock() >= 0;
    }

    @Override
    protected void notificarCambio(String tipoOperacion, Producto producto) {
        System.out.println("Operación " + tipoOperacion + " realizada en producto: " + producto.getNombre());
    }

    @Override
    protected Object obtenerIdElemento(Producto elemento) {
        return null;
    }

    @Override
    protected boolean coincideConCriterio(Producto elemento, String criterio) {
        return false;
    }

    public List<Producto> listarProductos(OrdenProducto orden) {
        List<Producto> lista = new ArrayList<>(productos);
        
        switch (orden) {
            case ID_ASC:
                lista.sort(Comparator.comparing(Producto::getIdProducto));
                break;
            case ID_DESC:
                lista.sort(Comparator.comparing(Producto::getIdProducto).reversed());
                break;
            case NOMBRE_ASC:
                lista.sort(Comparator.comparing(Producto::getNombre));
                break;
            case NOMBRE_DESC:
                lista.sort(Comparator.comparing(Producto::getNombre).reversed());
                break;
        }
        
        return lista;
    }

    public Producto buscarPorId(int id) {
        return productos.stream()
                .filter(p -> p.getIdProducto() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return productos.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    // Métodos públicos para CRUD
    public boolean crearProducto(Producto producto) { return alta(producto); }
    public boolean eliminarProducto(Producto producto) { return baja(producto); }
    public boolean actualizarProducto(Producto antiguo, Producto nuevo) { return edicion(antiguo, nuevo); }
}