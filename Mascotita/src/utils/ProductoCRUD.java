package utils;

import modelo.Producto;
import modelo.personas.Cliente;

import java.util.List;
import java.util.Optional;

public class ProductoCRUD extends OperacionesCRUD<Producto> {
    
    @Override
    protected boolean validarDatos(Producto producto) {
        return producto != null 
            && producto.getNombre() != null 
            && !producto.getNombre().trim().isEmpty()
            && producto.getPrecio() >= 0
            && producto.getStock() >= 0
            && producto.getIdProducto() > 0;
    }
    
    @Override
    protected void notificarCambio(String tipoOperacion, Producto producto) {
        System.out.println("══════════════════════════════════════");
        System.out.println("✓ " + tipoOperacion + " DE PRODUCTO EXITOSA");
        System.out.println("Producto: " + producto.getNombre());
        System.out.println("ID: " + producto.getIdProducto());
        System.out.println("Precio: $" + producto.getPrecio());
        System.out.println("══════════════════════════════════════");
    }
    
    @Override
    protected Object obtenerIdElemento(Producto producto) {
        return producto.getIdProducto();
    }
    
    @Override
    protected boolean coincideConCriterio(Producto producto, String criterio) {
        String criterioLower = criterio.toLowerCase();
        return producto.getNombre().toLowerCase().contains(criterioLower)
            || producto.getDescripcion().toLowerCase().contains(criterioLower);
    }
    public void alta() {
        altaInteractiva();
    }

    public void baja() {
        bajaInteractiva();
    }

    public void edicion() {
        edicionInteractiva();
    }

    public void consulta() { consultaInteractiva();}




    public void consultaInteractiva() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║          CONSULTA DE PRODUCTO         ║");
        System.out.println("╚══════════════════════════════════════╝");

        if (elementos.isEmpty()) {
            System.out.println("❌ No hay productos registrados en el sistema.");
            return;
        }

        System.out.println("\n¿Cómo desea buscar el producto?");
        System.out.println("1. Por id de producto");
        System.out.println("2. Por nombre del producto");
        System.out.println("3. Mostrar todos los productos");
        System.out.print("Seleccione una opción: ");

        int opcion = leerEntero("");

        switch (opcion) {
            case 1:
                consultarPorNumero();
                break;
            case 2:
                consultarPorCriterio();
                break;
            case 3:
                mostrarLista();
                break;
            default:
                System.out.println("❌ Opción no válida.");
        }
    }
    private void consultarPorNumero() {
        int idProducto= leerEntero("➤ Ingrese id del Producto: ");

        Optional<Producto> productoEncontrado = buscarPorId(idProducto);

        if (productoEncontrado.isPresent()) {
            System.out.println("\n✅ Producto encontrado:");
            mostrarDetalles(productoEncontrado.get());
        } else {
            System.out.println("❌ No se encontró el Producto con el id : " + idProducto);
        }
    }
    private void consultarPorCriterio() {
        String criterio = leerTexto("➤ Ingrese el nombre del producto: ");

        if (criterio.trim().isEmpty()) {
            System.out.println("❌ Debe ingresar un criterio de búsqueda.");
            return;
        }

        List<Producto> productosEncontrados = buscarPorCriterio(criterio);

        if (productosEncontrados.isEmpty()) {
            System.out.println("❌ No se encontraron productos que coincidan con: " + criterio);
        } else {
            System.out.println("\n✅ Se encontraron " + productosEncontrados.size() + " cliente(s):");
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                    RESULTADOS DE BÚSQUEDA                   ║");
            System.out.println("╠══════════════════════════════════════════════════════════════╣");
            System.out.println("║  Id del Producto  │                 Nombre        │ Precio ║");
            System.out.println("╠══════════════════════════════════════════════════════════════╣");

            for (Producto producto : productosEncontrados) {
                System.out.printf("║ %-8d │ %-33s │ %-8s ║%n",
                        producto.getIdProducto(),
                producto.getNombre(),
                        producto.getPrecio(),
                producto.getStock(),
                producto.getDescripcion() );

            }
            System.out.println("╚══════════════════════════════════════════════════════════════╝");

            if (productosEncontrados.size() == 1) {
                String respuesta = leerTexto("\n¿Desea ver los detalles completos? (s/n): ");
                if (respuesta.toLowerCase().startsWith("s")) {
                    mostrarDetalles(productosEncontrados.get(0));
                }
            } else {
                String respuesta = leerTexto("\n¿Desea ver los detalles de algún producto? (s/n): ");
                if (respuesta.toLowerCase().startsWith("s")) {
                    int numero = leerEntero("➤ Ingrese el id del Producto: ");
                    Optional<Producto> producto = productosEncontrados.stream()
                            .filter(c -> c.getIdProducto() == numero)
                            .findFirst();

                    if (producto.isPresent()) {
                        mostrarDetalles(producto.get());
                    } else {
                        System.out.println("❌ El número ingresado no está en los resultados.");
                    }
                }
            }
        }
    }
    @Override
    public Producto solicitarDatosAlta() {
        try {
            int idProducto = leerEntero("➤ Ingrese el ID del producto: ");
            
            if (existeById(idProducto)) {
                System.out.println("❌ Ya existe un producto con ese ID.");
                return null;
            }
            
            String nombre = leerTexto("➤ Ingrese el nombre del producto: ");
            if (nombre.isEmpty()) {
                System.out.println("❌ El nombre es obligatorio.");
                return null;
            }
            
            double precio = leerDecimal("➤ Ingrese el precio: $");
            if (precio < 0) {
                System.out.println("❌ El precio no puede ser negativo.");
                return null;
            }
            
            int stock = leerEntero("➤ Ingrese el stock inicial: ");
            if (stock < 0) {
                System.out.println("❌ El stock no puede ser negativo.");
                return null;
            }
            
            String descripcion = leerTexto("➤ Ingrese la descripción: ");
            if (descripcion.isEmpty()) {
                System.out.println("❌ La descripción es obligatoria.");
                return null;
            }
            
            return new Producto(idProducto, nombre, precio, stock, descripcion);
            
        } catch (Exception e) {
            System.out.println("❌ Error al solicitar datos: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public Producto solicitarDatosEdicion(Producto productoExistente) {
        try {
            System.out.println("\n📝 Datos actuales del producto:");
            mostrarDetalles(productoExistente);
            System.out.println("\n➤ Ingrese los nuevos datos (presione Enter para mantener el valor actual):");
            
            String nombre = leerTexto("➤ Nombre [" + productoExistente.getNombre() + "]: ");
            if (nombre.isEmpty()) nombre = productoExistente.getNombre();
            
            String precioStr = leerTexto("➤ Precio [$" + productoExistente.getPrecio() + "]: ");
            double precio = productoExistente.getPrecio();
            if (!precioStr.isEmpty()) {
                try {
                    precio = Double.parseDouble(precioStr);
                    if (precio < 0) {
                        System.out.println("❌ Precio inválido, manteniendo precio actual.");
                        precio = productoExistente.getPrecio();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("❌ Formato inválido, manteniendo precio actual.");
                }
            }
            
            String stockStr = leerTexto("➤ Stock [" + productoExistente.getStock() + "]: ");
            int stock = productoExistente.getStock();
            if (!stockStr.isEmpty()) {
                try {
                    stock = Integer.parseInt(stockStr);
                    if (stock < 0) {
                        System.out.println("❌ Stock inválido, manteniendo stock actual.");
                        stock = productoExistente.getStock();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("❌ Formato inválido, manteniendo stock actual.");
                }
            }
            
            String descripcion = leerTexto("➤ Descripción [" + productoExistente.getDescripcion() + "]: ");
            if (descripcion.isEmpty()) descripcion = productoExistente.getDescripcion();
            
            return new Producto(productoExistente.getIdProducto(), nombre, precio, stock, descripcion);
            
        } catch (Exception e) {
            System.out.println("❌ Error al solicitar datos de edición: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public void mostrarDetalles(Producto producto) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║         DETALLES DEL PRODUCTO        ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ ID: " + String.format("%-34s", producto.getIdProducto()) + "║");
        System.out.println("║ Nombre: " + String.format("%-30s", producto.getNombre()) + "║");
        System.out.println("║ Precio: $" + String.format("%-29s", producto.getPrecio()) + "║");
        System.out.println("║ Stock: " + String.format("%-31s", producto.getStock()) + "║");
        System.out.println("║ Descripción: " + String.format("%-25s", 
                          producto.getDescripcion().length() > 25 ? 
                          producto.getDescripcion().substring(0, 22) + "..." : 
                          producto.getDescripcion()) + "║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    public void mostrarLista() {
        if (elementos.isEmpty()) {
            System.out.println("❌ No hay productos registrados.");
            return;
        }
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                       LISTA DE PRODUCTOS                      ║");
        System.out.println("╠════════════════════════════════════════════════════════════════╣");
        System.out.println("║   ID   │           Nombre           │  Precio  │    Stock    ║");
        System.out.println("╠════════════════════════════════════════════════════════════════╣");
        
        for (Producto producto : elementos) {
            System.out.printf("║ %-6d │ %-25s │ $%-7.2f │ %-11d ║%n", 
                            producto.getIdProducto(),
                            producto.getNombre().length() > 25 ? 
                            producto.getNombre().substring(0, 22) + "..." : 
                            producto.getNombre(),
                            producto.getPrecio(),
                            producto.getStock());
        }
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
    
    @Override
    protected void mostrarEncabezadoAlta() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║          ALTA DE PRODUCTO            ║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    protected void mostrarEncabezadoBaja() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║          BAJA DE PRODUCTO            ║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    protected void mostrarEncabezadoEdicion() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║        EDICIÓN DE PRODUCTO           ║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    protected Object solicitarIdParaBaja() {
        return leerEntero("➤ Ingrese el ID del producto a eliminar: ");
    }
    
    @Override
    protected Object solicitarIdParaEdicion() {
        return leerEntero("➤ Ingrese el ID del producto a editar: ");
    }

    @Override
    protected String getId(Producto elemento) {
        return "";
    }

    // Métodos específicos para Producto
    public boolean actualizarStock(int idProducto, int nuevoStock) {
        var producto = buscarPorId(idProducto);
        if (producto.isPresent() && nuevoStock >= 0) {
            Producto p = producto.get();
            p.setStock(nuevoStock);
            notificarCambio("ACTUALIZACIÓN_STOCK", p);
            return true;
        }
        return false;
    }
    
    public boolean actualizarPrecio(int idProducto, double nuevoPrecio) {
        var producto = buscarPorId(idProducto);
        if (producto.isPresent() && nuevoPrecio >= 0) {
            Producto p = producto.get();
            p.setPrecio(nuevoPrecio);
            notificarCambio("ACTUALIZACIÓN_PRECIO", p);
            return true;
        }
        return false;
    }
    
    public boolean actualizarStockInteractivo() {
        try {
            if (elementos.isEmpty()) {
                System.out.println("❌ No hay productos registrados.");
                return false;
            }
            
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║      ACTUALIZACIÓN DE STOCK          ║");
            System.out.println("╚══════════════════════════════════════╝");
            
            mostrarLista();
            int idProducto = leerEntero("➤ Ingrese el ID del producto: ");
            int nuevoStock = leerEntero("➤ Ingrese el nuevo stock: ");
            
            return actualizarStock(idProducto, nuevoStock);
            
        } catch (Exception e) {
            System.out.println("❌ Error durante la actualización: " + e.getMessage());
            return false;
        }
    }
}