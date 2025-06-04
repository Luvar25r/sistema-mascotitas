package modulos;

import modelo.*;
import modelo.personas.Cliente;

import java.util.*;

/**
 * Gestor para registro de ventas
 */
public class VentaManager {
    private List<Venta> ventasPasadas;
    private int contadorVentas;

    public enum OrdenVenta {
        NOMBRE_ASC, NOMBRE_DESC, FECHA_ASC, FECHA_DESC
    }

    public VentaManager() {
        this.ventasPasadas = new ArrayList<>();
        this.contadorVentas = 1;
    }

    public Venta crearNuevaVenta(Cliente cliente) {
        return new Venta(contadorVentas++, cliente);
    }

    public void procesarPago(Venta venta, Date fechaVencimientoTarjeta) {
        double total = venta.getTotal();
        Tarjeta tarjeta = new Tarjeta(fechaVencimientoTarjeta, total);
        venta.asignarTarjeta(tarjeta);
        
        // Registrar venta como completada
        ventasPasadas.add(venta);
        
        System.out.println("Pago procesado exitosamente");
        System.out.println("Número de tarjeta: " + tarjeta.getNumeroTarjeta());
        System.out.println("CVC: " + tarjeta.getNumeroCVC());
        System.out.println("Total cargado: $" + total);
    }

    public List<Venta> listarVentasPasadas(OrdenVenta orden) {
        List<Venta> lista = new ArrayList<>(ventasPasadas);
        
        switch (orden) {
            case NOMBRE_ASC:
                lista.sort(Comparator.comparing(v -> obtenerNombreProductoServicio(v)));
                break;
            case NOMBRE_DESC:
                lista.sort(Comparator.comparing((Venta v) -> obtenerNombreProductoServicio(v)).reversed());
                break;
            case FECHA_ASC:
                lista.sort(Comparator.comparing(Venta::getFechaVenta));
                break;
            case FECHA_DESC:
                lista.sort(Comparator.comparing(Venta::getFechaVenta).reversed());
                break;
        }
        
        return lista;
    }

    private String obtenerNombreProductoServicio(Venta venta) {
        // Obtener el primer producto o servicio para ordenamiento
        if (!venta.getProductos().isEmpty()) {
            return venta.getProductos().get(0).getNombre();
        } else if (!venta.getServicios().isEmpty()) {
            return venta.getServicios().get(0).getNombre();
        }
        return "";
    }

    public double calcularTotalVentas() {
        return ventasPasadas.stream()
                .mapToDouble(Venta::getTotal)
                .sum();
    }

    public List<Venta> getVentasPorCliente(Cliente cliente) {
        return ventasPasadas.stream()
                .filter(v -> v.getCliente().equals(cliente))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}