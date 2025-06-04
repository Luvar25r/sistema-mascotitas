package modulos;

import modelo.Sucursal;
import modelo.Zona;
import utils.OperacionesCRUD;
import java.util.*;

/**
 * Gestor para consulta de sucursales (no permite modificaciones ya que son predefinidas)
 */
public class SucursalManager extends OperacionesCRUD<Sucursal> {

    public SucursalManager() {
        // Las sucursales están predefinidas en el enum, no se necesita inicialización
    }

    @Override
    public boolean alta(Sucursal sucursal) {
        System.out.println("❌ No se pueden crear nuevas sucursales. Las sucursales están predefinidas en el sistema.");
        return false;
    }

    @Override
    public boolean baja(Sucursal sucursal) {
        System.out.println("❌ No se pueden eliminar sucursales. Las sucursales están predefinidas en el sistema.");
        return false;
    }

    @Override
    public boolean edicion(Sucursal sucursalAntigua, Sucursal sucursalNueva) {
        System.out.println("❌ No se pueden editar sucursales. Las sucursales están predefinidas en el sistema.");
        return false;
    }

    @Override
    public void alta() {
        System.out.println("❌ No se pueden crear nuevas sucursales. Las sucursales están predefinidas en el sistema.");
    }

    @Override
    public void baja() {
        System.out.println("❌ No se pueden eliminar sucursales. Las sucursales están predefinidas en el sistema.");
    }

    @Override
    public void edicion() {
        System.out.println("❌ No se pueden editar sucursales. Las sucursales están predefinidas en el sistema.");
    }

    @Override
    public Sucursal solicitarDatosAlta() {
        System.out.println("❌ Operación no permitida. Las sucursales están predefinidas.");
        return null;
    }

    @Override
    public Sucursal solicitarDatosEdicion(Sucursal elementoExistente) {
        System.out.println("❌ Operación no permitida. Las sucursales están predefinidas.");
        return null;
    }

    @Override
    public void mostrarDetalles(Sucursal elemento) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║        DETALLES DE SUCURSAL          ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ Código: " + String.format("%-29s", elemento.getCodigoSucursal()) + "║");
        System.out.println("║ Nombre: " + String.format("%-29s", elemento.getNombre()) + "║");
        System.out.println("║ Zona: " + String.format("%-31s", elemento.getZona().getDescripcion()) + "║");
        System.out.println("╚══════════════════════════════════════╝");
    }

    @Override
    public void mostrarLista() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                          SUCURSALES DISPONIBLES                          ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║  Código  │              Nombre              │         Zona              ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════════╣");

        for (Sucursal sucursal : Sucursal.values()) {
            System.out.printf("║ %-8s │ %-30s │ %-25s ║%n", 
                            sucursal.getCodigoSucursal(),
                            sucursal.getNombre(),
                            sucursal.getZona().getDescripcion());
        }
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════╝");
    }

    @Override
    protected void mostrarEncabezadoAlta() {
        System.out.println("❌ Operación no disponible.");
    }

    @Override
    protected void mostrarEncabezadoBaja() {
        System.out.println("❌ Operación no disponible.");
    }

    @Override
    protected void mostrarEncabezadoEdicion() {
        System.out.println("❌ Operación no disponible.");
    }

    @Override
    protected Object solicitarIdParaBaja() {
        System.out.println("❌ Operación no permitida.");
        return null;
    }

    @Override
    protected Object solicitarIdParaEdicion() {
        System.out.println("❌ Operación no permitida.");
        return null;
    }

    @Override
    protected String getId(Sucursal elemento) {
        return elemento.getCodigoSucursal();
    }

    @Override
    protected boolean validarDatos(Sucursal sucursal) {
        // No aplicable para enum predefinido
        return sucursal != null;
    }

    @Override
    protected void notificarCambio(String tipoOperacion, Sucursal sucursal) {
        // No aplicable ya que no se permiten cambios
        System.out.println("❌ Las sucursales no pueden ser modificadas.");
    }

    @Override
    protected Object obtenerIdElemento(Sucursal elemento) {
        return elemento.getCodigoSucursal();
    }

    @Override
    protected boolean coincideConCriterio(Sucursal elemento, String criterio) {
        String criterioLower = criterio.toLowerCase();
        return elemento.getNombre().toLowerCase().contains(criterioLower)
            || elemento.getCodigoSucursal().toLowerCase().contains(criterioLower)
            || elemento.getZona().getDescripcion().toLowerCase().contains(criterioLower);
    }

    public List<Sucursal> listarSucursales() {
        return Arrays.asList(Sucursal.values());
    }

    public List<Sucursal> buscarPorZona(Zona zona) {
        return Arrays.asList(Sucursal.buscarPorZona(zona));
    }

    public Sucursal buscarPorCodigo(String codigo) {
        return Sucursal.buscarPorCodigo(codigo);
    }

    public Sucursal buscarPorNombre(String nombre) {
        for (Sucursal sucursal : Sucursal.values()) {
            if (sucursal.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                return sucursal;
            }
        }
        return null;
    }

    // Métodos públicos para CRUD (deshabilitados)
    public boolean crearSucursal(Sucursal sucursal) { 
        System.out.println("❌ No se pueden crear sucursales. Están predefinidas.");
        return false; 
    }

    public boolean eliminarSucursal(Sucursal sucursal) { 
        System.out.println("❌ No se pueden eliminar sucursales. Están predefinidas.");
        return false; 
    }

    public boolean actualizarSucursal(Sucursal antigua, Sucursal nueva) { 
        System.out.println("❌ No se pueden editar sucursales. Están predefinidas.");
        return false; 
    }
}