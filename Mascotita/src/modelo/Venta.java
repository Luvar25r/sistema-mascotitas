package modelo;

import modelo.personas.Cliente;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * 7
 * Clase para registro de ventas de productos y servicios
 */
public class Venta implements Comparable<Venta> {
    private int numeroVenta;
    private Date fechaVenta;
    private Cliente cliente;
    private List<Producto> productos;
    private List<Servicio> servicios;
    private double total;
    private Tarjeta tarjeta;

    public Venta(int numeroVenta, Cliente cliente) {
        this.numeroVenta = numeroVenta;
        this.cliente = cliente;
        this.fechaVenta = new Date();
        this.productos = new ArrayList<>();
        this.servicios = new ArrayList<>();
        this.total = 0.0;
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
        calcularTotal();
    }

    public void agregarServicio(Servicio servicio) {
        servicios.add(servicio);
        calcularTotal();
    }

    private void calcularTotal() {
        total = 0.0;
        for (Producto producto : productos) {
            total += producto.getPrecio();
        }
        for (Servicio servicio : servicios) {
            total += servicio.getPrecio();
        }
    }

    public void asignarTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
        tarjeta.setCargo(this.total);
    }

    // Getters y Setters
    public int getNumeroVenta() { return numeroVenta; }
    public Date getFechaVenta() { return fechaVenta; }
    public Cliente getCliente() { return cliente; }
    public List<Producto> getProductos() { return productos; }
    public List<Servicio> getServicios() { return servicios; }
    public double getTotal() { return total; }
    public Tarjeta getTarjeta() { return tarjeta; }

    @Override
    public int compareTo(Venta otra) {
        return this.fechaVenta.compareTo(otra.fechaVenta);
    }

    @Override
    public String toString() {
        return "Venta{" +
                "numeroVenta=" + numeroVenta +
                ", fechaVenta=" + fechaVenta +
                ", cliente=" + cliente.getNombre() +
                ", total=" + total +
                '}';
    }
}