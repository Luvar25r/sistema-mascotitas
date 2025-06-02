package modulos;

import modelo.Sucursal;
import modelo.Zona;
import utils.OperacionesCRUD;
import java.util.*;

/**
 * Gestor para CRUD de sucursales
 */
public class SucursalManager extends OperacionesCRUD<Sucursal> {
    private List<Sucursal> sucursales;

    public SucursalManager() {
        this.sucursales = new ArrayList<>();
    }

    @Override
    protected boolean alta(Sucursal sucursal) {
        if (validarDatos(sucursal)) {
            sucursales.add(sucursal);
            notificarCambio("ALTA", sucursal);
            return true;
        }
        return false;
    }

    @Override
    protected boolean baja(Sucursal sucursal) {
        boolean eliminado = sucursales.remove(sucursal);
        if (eliminado) {
            notificarCambio("BAJA", sucursal);
        }
        return eliminado;
    }

    @Override
    protected boolean edicion(Sucursal sucursalAntigua, Sucursal sucursalNueva) {
        int index = sucursales.indexOf(sucursalAntigua);
        if (index != -1 && validarDatos(sucursalNueva)) {
            sucursales.set(index, sucursalNueva);
            notificarCambio("EDICION", sucursalNueva);
            return true;
        }
        return false;
    }

    @Override
    protected boolean validarDatos(Sucursal sucursal) {
        return sucursal != null && 
               sucursal.getNombre() != null && !sucursal.getNombre().trim().isEmpty() &&
               sucursal.getCodigoSucursal() != null && !sucursal.getCodigoSucursal().trim().isEmpty() &&
               sucursal.getZona() != null;
    }

    @Override
    protected void notificarCambio(String tipoOperacion, Sucursal sucursal) {
        System.out.println("Operación " + tipoOperacion + " realizada en sucursal: " + sucursal.getNombre());
    }

    public List<Sucursal> listarSucursales() {
        return new ArrayList<>(sucursales);
    }

    public List<Sucursal> buscarPorZona(Zona zona) {
        return sucursales.stream()
                .filter(s -> s.getZona().equals(zona))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public Sucursal buscarPorCodigo(String codigo) {
        return sucursales.stream()
                .filter(s -> s.getCodigoSucursal().equals(codigo))
                .findFirst()
                .orElse(null);
    }

    // Métodos públicos para CRUD
    public boolean crearSucursal(Sucursal sucursal) { return alta(sucursal); }
    public boolean eliminarSucursal(Sucursal sucursal) { return baja(sucursal); }
    public boolean actualizarSucursal(Sucursal antigua, Sucursal nueva) { return edicion(antigua, nueva); }
}