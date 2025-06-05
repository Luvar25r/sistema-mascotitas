package utils;

import modelo.personas.Cliente;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ClienteCRUD extends OperacionesCRUD<Cliente> {
    private SimpleDateFormat formatoFecha;
    
    public ClienteCRUD() {
        super();
        this.formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        this.formatoFecha.setLenient(false);
    }
    
    // ========== MÉTODOS WRAPPER PARA EL MENÚ ==========
    
    public void alta() {
        altaInteractiva();
    }
    
    public void baja() {
        bajaInteractiva();
    }
    
    public void edicion() {
        edicionInteractiva();
    }
    
    public void consulta() {
        consultaInteractiva();
    }
    
    public void consultaInteractiva() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║          CONSULTA DE CLIENTE         ║");
        System.out.println("╚══════════════════════════════════════╝");
        
        if (elementos.isEmpty()) {
            System.out.println("❌ No hay clientes registrados en el sistema.");
            return;
        }
        
        System.out.println("\n¿Cómo desea buscar el cliente?");
        System.out.println("1. Por número de cliente");
        System.out.println("2. Por nombre/apellido/CURP");
        System.out.println("3. Mostrar todos los clientes");
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
        int numeroCliente = leerEntero("➤ Ingrese el número de cliente: ");
        
        Optional<Cliente> clienteEncontrado = buscarPorId(numeroCliente);
        
        if (clienteEncontrado.isPresent()) {
            System.out.println("\n✅ Cliente encontrado:");
            mostrarDetalles(clienteEncontrado.get());
        } else {
            System.out.println("❌ No se encontró un cliente con el número: " + numeroCliente);
        }
    }
    
    private void consultarPorCriterio() {
        String criterio = leerTexto("➤ Ingrese nombre, apellido o CURP a buscar: ");
        
        if (criterio.trim().isEmpty()) {
            System.out.println("❌ Debe ingresar un criterio de búsqueda.");
            return;
        }
        
        List<Cliente> clientesEncontrados = buscarPorCriterio(criterio);
        
        if (clientesEncontrados.isEmpty()) {
            System.out.println("❌ No se encontraron clientes que coincidan con: " + criterio);
        } else {
            System.out.println("\n✅ Se encontraron " + clientesEncontrados.size() + " cliente(s):");
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                    RESULTADOS DE BÚSQUEDA                   ║");
            System.out.println("╠══════════════════════════════════════════════════════════════╣");
            System.out.println("║  Número  │                 Nombre                │ Teléfono ║");
            System.out.println("╠══════════════════════════════════════════════════════════════╣");
            
            for (Cliente cliente : clientesEncontrados) {
                System.out.printf("║ %-8d │ %-33s │ %-8s ║%n", 
                                cliente.getNumeroCliente(),
                                cliente.getNombreCompleto(),
                                cliente.getTelefono());
            }
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            
            if (clientesEncontrados.size() == 1) {
                String respuesta = leerTexto("\n¿Desea ver los detalles completos? (s/n): ");
                if (respuesta.toLowerCase().startsWith("s")) {
                    mostrarDetalles(clientesEncontrados.get(0));
                }
            } else {
                String respuesta = leerTexto("\n¿Desea ver los detalles de algún cliente? (s/n): ");
                if (respuesta.toLowerCase().startsWith("s")) {
                    int numero = leerEntero("➤ Ingrese el número del cliente: ");
                    Optional<Cliente> cliente = clientesEncontrados.stream()
                        .filter(c -> c.getNumeroCliente() == numero)
                        .findFirst();
                    
                    if (cliente.isPresent()) {
                        mostrarDetalles(cliente.get());
                    } else {
                        System.out.println("❌ El número ingresado no está en los resultados.");
                    }
                }
            }
        }
    }
    
    // ========== MÉTODOS HEREDADOS Y SOBRESCRITOS ==========
    
    @Override
    protected String getId(Cliente cliente) {
        return cliente.getId();
    }
    
    @Override
    protected boolean validarDatos(Cliente cliente) {
        return cliente != null 
            && cliente.getNombre() != null && !cliente.getNombre().trim().isEmpty()
            && cliente.getApellidoPaterno() != null && !cliente.getApellidoPaterno().trim().isEmpty()
            && cliente.getCurp() != null && !cliente.getCurp().trim().isEmpty()
            && cliente.getFechaNacimiento() != null
            && cliente.getNumeroCliente() > 0;
    }
    
    @Override
    protected void notificarCambio(String tipoOperacion, Cliente cliente) {
        System.out.println("══════════════════════════════════════");
        System.out.println("✓ " + tipoOperacion + " DE CLIENTE EXITOSA");
        System.out.println("Cliente: " + cliente.getNombreCompleto());
        System.out.println("Número: " + cliente.getNumeroCliente());
        System.out.println("ID: " + getId(cliente));
        System.out.println("══════════════════════════════════════");
    }
    
    @Override
    protected Object obtenerIdElemento(Cliente cliente) {
        return cliente.getNumeroCliente();
    }
    
    @Override
    protected boolean coincideConCriterio(Cliente cliente, String criterio) {
        String criterioLower = criterio.toLowerCase();
        return cliente.getNombre().toLowerCase().contains(criterioLower)
            || cliente.getApellidoPaterno().toLowerCase().contains(criterioLower)
            || (cliente.getApellidoMaterno() != null && 
                cliente.getApellidoMaterno().toLowerCase().contains(criterioLower))
            || cliente.getCurp().toLowerCase().contains(criterioLower);
    }
    
    @Override
    public Cliente solicitarDatosAlta() {
        try {
            String nombre = leerTexto("➤ Ingrese el nombre: ");
            if (nombre.isEmpty()) {
                System.out.println("❌ El nombre es obligatorio.");
                return null;
            }
            
            String apellidoPaterno = leerTexto("➤ Ingrese el apellido paterno: ");
            if (apellidoPaterno.isEmpty()) {
                System.out.println("❌ El apellido paterno es obligatorio.");
                return null;
            }
            
            String apellidoMaterno = leerTextoOpcional("➤ Ingrese el apellido materno (opcional): ");
            
            Date fechaNacimiento = null;
            while (fechaNacimiento == null) {
                try {
                    String fechaStr = leerTexto("➤ Ingrese la fecha de nacimiento (dd/MM/yyyy): ");
                    if (fechaStr.trim().isEmpty()) {
                        System.out.println("❌ La fecha es obligatoria.");
                        continue;
                    }
                    fechaNacimiento = formatoFecha.parse(fechaStr.trim());
                    System.out.println("✅ Fecha válida: " + formatoFecha.format(fechaNacimiento));
                } catch (ParseException e) {
                    System.out.println("❌ Formato de fecha inválido. Use el formato exacto: dd/MM/yyyy");
                    System.out.println("   Ejemplo: 15/03/1990");
                }
            }
            
            String curp = leerTexto("➤ Ingrese el CURP: ");
            if (curp.isEmpty()) {
                System.out.println("❌ El CURP es obligatorio.");
                return null;
            }
            
            String telefono = leerTexto("➤ Ingrese el teléfono: ");
            String email = leerTexto("➤ Ingrese el email: ");
            boolean tieneMascota = leerBooleano("➤ ¿Tiene mascota?");
            
            return new Cliente(nombre, apellidoPaterno, apellidoMaterno, 
                             fechaNacimiento, curp, telefono, email, tieneMascota);
                             
        } catch (Exception e) {
            System.out.println("❌ Error al solicitar datos: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public Cliente solicitarDatosEdicion(Cliente clienteExistente) {
        try {
            System.out.println("\n📝 Datos actuales del cliente:");
            mostrarDetalles(clienteExistente);
            System.out.println("\n➤ Ingrese los nuevos datos (presione Enter para mantener el valor actual):");
            
            String nombre = leerTexto("➤ Nombre [" + clienteExistente.getNombre() + "]: ");
            if (nombre.isEmpty()) nombre = clienteExistente.getNombre();
            
            String apellidoPaterno = leerTexto("➤ Apellido paterno [" + clienteExistente.getApellidoPaterno() + "]: ");
            if (apellidoPaterno.isEmpty()) apellidoPaterno = clienteExistente.getApellidoPaterno();
            
            String apellidoMaternoActual = clienteExistente.getApellidoMaterno() != null ? clienteExistente.getApellidoMaterno() : "";
            String apellidoMaterno = leerTexto("➤ Apellido materno [" + apellidoMaternoActual + "]: ");
            if (apellidoMaterno.isEmpty()) apellidoMaterno = clienteExistente.getApellidoMaterno();
            
            Date fechaNacimiento = clienteExistente.getFechaNacimiento();
            String fechaStr = leerTexto("➤ Fecha de nacimiento [" + formatoFecha.format(fechaNacimiento) + "] (dd/MM/yyyy): ");
            if (!fechaStr.isEmpty()) {
                try {
                    fechaNacimiento = formatoFecha.parse(fechaStr.trim());
                } catch (ParseException e) {
                    System.out.println("❌ Formato inválido, manteniendo fecha actual.");
                }
            }
            
            String curp = leerTexto("➤ CURP [" + clienteExistente.getCurp() + "]: ");
            if (curp.isEmpty()) curp = clienteExistente.getCurp();
            
            String telefono = leerTexto("➤ Teléfono [" + clienteExistente.getTelefono() + "]: ");
            if (telefono.isEmpty()) telefono = clienteExistente.getTelefono();
            
            String email = leerTexto("➤ Email [" + clienteExistente.getEmail() + "]: ");
            if (email.isEmpty()) email = clienteExistente.getEmail();
            
            String tieneMascotaStr = leerTexto("➤ ¿Tiene mascota? [" + (clienteExistente.isTieneMascota() ? "Sí" : "No") + "] (s/n): ");
            boolean tieneMascota = clienteExistente.isTieneMascota();
            if (!tieneMascotaStr.isEmpty()) {
                tieneMascota = tieneMascotaStr.toLowerCase().startsWith("s");
            }
            
            return new Cliente(clienteExistente.getNumeroCliente(), nombre, apellidoPaterno, 
                             apellidoMaterno, fechaNacimiento, curp, telefono, email, tieneMascota);
                             
        } catch (Exception e) {
            System.out.println("❌ Error al solicitar datos de edición: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public void mostrarDetalles(Cliente cliente) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║           DETALLES DEL CLIENTE       ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ Número: " + String.format("%-28s", cliente.getNumeroCliente()) + "║");
        System.out.println("║ ID: " + String.format("%-32s", getId(cliente)) + "║");
        System.out.println("║ Nombre: " + String.format("%-30s", cliente.getNombreCompleto()) + "║");
        System.out.println("║ CURP: " + String.format("%-32s", cliente.getCurp()) + "║");
        System.out.println("║ Teléfono: " + String.format("%-28s", cliente.getTelefono()) + "║");
        System.out.println("║ Email: " + String.format("%-31s", cliente.getEmail()) + "║");
        System.out.println("║ Fecha Nac.: " + String.format("%-25s", formatoFecha.format(cliente.getFechaNacimiento())) + "║");
        System.out.println("║ Tiene Mascota: " + String.format("%-23s", cliente.isTieneMascota() ? "Sí" : "No") + "║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    public void mostrarLista() {
        if (elementos.isEmpty()) {
            System.out.println("❌ No hay clientes registrados.");
            return;
        }
        
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                        LISTA DE CLIENTES                    ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║  Número  │                 Nombre                │ Teléfono ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        
        for (Cliente cliente : elementos) {
            System.out.printf("║ %-8d │ %-33s │ %-8s ║%n", 
                            cliente.getNumeroCliente(),
                            cliente.getNombreCompleto(),
                            cliente.getTelefono());
        }
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }
    
    @Override
    protected void mostrarEncabezadoAlta() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║           ALTA DE CLIENTE            ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("Próximo número de cliente: " + Cliente.getSiguienteNumeroCliente());
    }
    
    @Override
    protected void mostrarEncabezadoBaja() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║           BAJA DE CLIENTE            ║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    protected void mostrarEncabezadoEdicion() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         EDICIÓN DE CLIENTE           ║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    protected Object solicitarIdParaBaja() {
        return leerEntero("➤ Ingrese el número de cliente a eliminar: ");
    }
    
    @Override
    protected Object solicitarIdParaEdicion() {
        return leerEntero("➤ Ingrese el número de cliente a editar: ");
    }

    // Método para agregar en la clase ClienteCRUD
    public void cargarDatosDeEjemplo() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║       CARGANDO DATOS DE EJEMPLO      ║");
        System.out.println("╚══════════════════════════════════════╝");

        try {
            // Cliente 1
            Date fecha1 = formatoFecha.parse("15/03/1990");
            Cliente cliente1 = new Cliente(
                    "María Elena",
                    "García",
                    "López",
                    fecha1,
                    "GALM900315MDFPPR01",
                    "55-1234-5678",
                    "maria.garcia@email.com",
                    true
            );

            // Cliente 2
            Date fecha2 = formatoFecha.parse("22/07/1985");
            Cliente cliente2 = new Cliente(
                    "Juan Carlos",
                    "Rodríguez",
                    "Hernández",
                    fecha2,
                    "ROHJ850722HDFRNN05",
                    "55-2345-6789",
                    "juan.rodriguez@email.com",
                    false
            );

            // Cliente 3
            Date fecha3 = formatoFecha.parse("08/12/1992");
            Cliente cliente3 = new Cliente(
                    "Ana Sofía",
                    "Martínez",
                    "Flores",
                    fecha3,
                    "MAFA921208MDFRLNN08",
                    "55-3456-7890",
                    "ana.martinez@email.com",
                    true
            );

            // Cliente 4
            Date fecha4 = formatoFecha.parse("30/05/1988");
            Cliente cliente4 = new Cliente(
                    "Carlos Alberto",
                    "Sánchez",
                    "Morales",
                    fecha4,
                    "SAMC880530HDFNRL02",
                    "55-4567-8901",
                    "carlos.sanchez@email.com",
                    false
            );

            // Cliente 5
            Date fecha5 = formatoFecha.parse("14/09/1995");
            Cliente cliente5 = new Cliente(
                    "Laura Patricia",
                    "Jiménez",
                    null, // Sin apellido materno
                    fecha5,
                    "JILL950914MDFMRR04",
                    "55-5678-9012",
                    "laura.jimenez@email.com",
                    true
            );

            // Agregar los clientes a la lista
            elementos.add(cliente1);
            elementos.add(cliente2);
            elementos.add(cliente3);
            elementos.add(cliente4);
            elementos.add(cliente5);

            System.out.println("✅ Se han cargado 5 clientes de ejemplo exitosamente:");
            System.out.println("   • María Elena García López (Con mascota)");
            System.out.println("   • Juan Carlos Rodríguez Hernández (Sin mascota)");
            System.out.println("   • Ana Sofía Martínez Flores (Con mascota)");
            System.out.println("   • Carlos Alberto Sánchez Morales (Sin mascota)");
            System.out.println("   • Laura Patricia Jiménez (Con mascota)");

        } catch (ParseException e) {
            System.out.println("❌ Error al cargar datos de ejemplo: " + e.getMessage());
        }
    }

    // Método alternativo para usar en el menú principal
    public void menuCargarEjemplos() {
        if (!elementos.isEmpty()) {
            String respuesta = leerTexto("⚠️  Ya existen clientes en el sistema. ¿Desea agregar los ejemplos de todas formas? (s/n): ");
            if (!respuesta.toLowerCase().startsWith("s")) {
                System.out.println("❌ Operación cancelada.");
                return;
            }
        }
        cargarDatosDeEjemplo();
    }
}