package utils;


import modelo.Adopcion;
import modelo.personas.Cliente;
import modelo.Mascota;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Clase CRUD para administración de adopciones
 * Permite gestionar las adopciones de mascotas por parte de clientes
 */
public class AdopcionesCRUD extends OperacionesCRUD<Adopcion> {
    
    private ClienteCRUD clienteCRUD;
    private MascotaCRUD mascotaCRUD;
    
    public AdopcionesCRUD(ClienteCRUD clienteCRUD, MascotaCRUD mascotaCRUD) {
        super();
        this.clienteCRUD = clienteCRUD;
        this.mascotaCRUD = mascotaCRUD;
    }
    
    @Override
    protected boolean validarDatos(Adopcion adopcion) {
        if (adopcion == null) {
            System.out.println("❌ Los datos de adopción no pueden ser nulos.");
            return false;
        }
        
        if (adopcion.getCliente() == null) {
            System.out.println("❌ Debe especificar un cliente para la adopción.");
            return false;
        }
        
        if (adopcion.getMascota() == null) {
            System.out.println("❌ Debe especificar una mascota para la adopción.");
            return false;
        }
        
        if (adopcion.getFechaAdopcion() == null) {
            System.out.println("❌ Debe especificar una fecha de adopción.");
            return false;
        }
        
        // Verificar que la mascota esté vacunada
        if (!adopcion.getMascota().isVacunada()) {
            System.out.println("❌ La mascota debe estar vacunada para poder ser adoptada.");
            return false;
        }
        
        // Verificar que la fecha no sea futura
        if (adopcion.getFechaAdopcion().isAfter(LocalDate.now())) {
            System.out.println("❌ La fecha de adopción no puede ser futura.");
            return false;
        }
        
        return true;
    }
    
    @Override
    protected void notificarCambio(String tipoOperacion, Adopcion adopcion) {
        System.out.println("══════════════════════════════════════");
        System.out.println("✓ " + tipoOperacion + " DE ADOPCIÓN EXITOSA");
        System.out.println("ID Adopción: " + adopcion.getIdAdopcion());
        System.out.println("Cliente: " + adopcion.getCliente().getNombreCompleto());
        System.out.println("Mascota: " + adopcion.getMascota().getNombre());
        System.out.println("Fecha: " + adopcion.getFechaFormateada());
        System.out.println("══════════════════════════════════════");
    }
    
    @Override
    protected Object obtenerIdElemento(Adopcion adopcion) {
        return adopcion.getIdAdopcion();
    }
    
    @Override
    protected boolean coincideConCriterio(Adopcion adopcion, String criterio) {
        String criterioLower = criterio.toLowerCase();
        return adopcion.getCliente().getNombreCompleto().toLowerCase().contains(criterioLower)
            || adopcion.getCliente().getCurp().toLowerCase().contains(criterioLower)
            || adopcion.getMascota().getNombre().toLowerCase().contains(criterioLower)
            || String.valueOf(adopcion.getIdAdopcion()).contains(criterio);
    }
    
    @Override
    public void alta() {
        altaInteractiva();
    }
    
    @Override
    public void baja() {
        bajaInteractiva();
    }
    
    @Override
    public void edicion() {
        edicionInteractiva();
    }
    
    public void consulta() {
        consultaInteractiva();
    }
    
    public void consultaInteractiva() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         CONSULTA DE ADOPCIONES       ║");
        System.out.println("╚══════════════════════════════════════╝");

        if (elementos.isEmpty()) {
            System.out.println("❌ No hay adopciones registradas en el sistema.");
            return;
        }

        System.out.println("\n¿Cómo desea buscar la adopción?");
        System.out.println("1. Por ID de adopción");
        System.out.println("2. Por nombre del cliente");
        System.out.println("3. Por nombre de la mascota");
        System.out.println("4. Mostrar todas las adopciones");
        System.out.print("Seleccione una opción: ");

        int opcion = leerEntero("");

        switch (opcion) {
            case 1:
                consultarPorId();
                break;
            case 2:
                consultarPorCliente();
                break;
            case 3:
                consultarPorMascota();
                break;
            case 4:
                mostrarLista();
                break;
            default:
                System.out.println("❌ Opción no válida.");
        }
    }
    
    private void consultarPorId() {
        int idAdopcion = leerEntero("➤ Ingrese el ID de adopción: ");
        
        Optional<Adopcion> adopcionEncontrada = buscarPorId(idAdopcion);
        
        if (adopcionEncontrada.isPresent()) {
            System.out.println("\n✅ Adopción encontrada:");
            mostrarDetalles(adopcionEncontrada.get());
        } else {
            System.out.println("❌ No se encontró adopción con ID: " + idAdopcion);
        }
    }
    
    private void consultarPorCliente() {
        String criterio = leerTexto("➤ Ingrese el nombre del cliente: ");
        
        if (criterio.trim().isEmpty()) {
            System.out.println("❌ Debe ingresar un criterio de búsqueda.");
            return;
        }
        
        List<Adopcion> adopcionesEncontradas = buscarPorCriterio(criterio);
        mostrarResultadosBusqueda(adopcionesEncontradas, "cliente: " + criterio);
    }
    
    private void consultarPorMascota() {
        String criterio = leerTexto("➤ Ingrese el nombre de la mascota: ");
        
        if (criterio.trim().isEmpty()) {
            System.out.println("❌ Debe ingresar un criterio de búsqueda.");
            return;
        }
        
        List<Adopcion> adopcionesEncontradas = elementos.stream()
                .filter(a -> a.getMascota().getNombre().toLowerCase().contains(criterio.toLowerCase()))
                .collect(Collectors.toList());
                
        mostrarResultadosBusqueda(adopcionesEncontradas, "mascota: " + criterio);
    }
    
    private void mostrarResultadosBusqueda(List<Adopcion> adopcionesEncontradas, String criterio) {
        if (adopcionesEncontradas.isEmpty()) {
            System.out.println("❌ No se encontraron adopciones que coincidan con: " + criterio);
        } else {
            System.out.println("\n✅ Se encontraron " + adopcionesEncontradas.size() + " adopción(es):");
            System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                           RESULTADOS DE BÚSQUEDA                             ║");
            System.out.println("╠═══════════════════════════════════════════════════════════════════════════════╣");
            System.out.println("║  ID  │                Cliente               │      Mascota      │    Fecha    ║");
            System.out.println("╠═══════════════════════════════════════════════════════════════════════════════╣");

            for (Adopcion adopcion : adopcionesEncontradas) {
                System.out.printf("║ %-4d │ %-33s │ %-17s │ %-11s ║%n",
                        adopcion.getIdAdopcion(),
                        truncarTexto(adopcion.getCliente().getNombreCompleto(), 33),
                        truncarTexto(adopcion.getMascota().getNombre(), 17),
                        adopcion.getFechaFormateada());
            }
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝");

            if (adopcionesEncontradas.size() == 1) {
                String respuesta = leerTexto("\n¿Desea ver los detalles completos? (s/n): ");
                if (respuesta.toLowerCase().startsWith("s")) {
                    mostrarDetalles(adopcionesEncontradas.get(0));
                }
            } else {
                String respuesta = leerTexto("\n¿Desea ver los detalles de alguna adopción? (s/n): ");
                if (respuesta.toLowerCase().startsWith("s")) {
                    int id = leerEntero("➤ Ingrese el ID de adopción: ");
                    Optional<Adopcion> adopcion = adopcionesEncontradas.stream()
                            .filter(a -> a.getIdAdopcion() == id)
                            .findFirst();

                    if (adopcion.isPresent()) {
                        mostrarDetalles(adopcion.get());
                    } else {
                        System.out.println("❌ El ID ingresado no está en los resultados.");
                    }
                }
            }
        }
    }
    
    @Override
    public Adopcion solicitarDatosAlta() {
        try {
            System.out.println("\n--- Datos del Cliente ---");
            
            // Buscar cliente existente o crear nuevo
            Cliente cliente = buscarOCrearCliente();
            if (cliente == null) {
                System.out.println("❌ No se pudo obtener los datos del cliente.");
                return null;
            }
            
            System.out.println("\n--- Datos de la Mascota ---");
            
            // Buscar mascota disponible para adopción
            Mascota mascota = buscarMascotaDisponible();
            if (mascota == null) {
                System.out.println("❌ No se pudo obtener los datos de la mascota.");
                return null;
            }
            
            // Verificar que la mascota esté vacunada
            if (!mascota.isVacunada()) {
                System.out.println("❌ La mascota seleccionada no está vacunada y no puede ser adoptada.");
                return null;
            }
            
            System.out.println("\n--- Datos de la Adopción ---");
            
            // Fecha de adopción
            LocalDate fechaAdopcion = solicitarFechaAdopcion();
            if (fechaAdopcion == null) {
                return null;
            }
            
            // Observaciones opcionales
            String observaciones = leerTextoOpcional("➤ Observaciones (opcional): ");
            
            return new Adopcion(cliente, mascota, fechaAdopcion, observaciones);
            
        } catch (Exception e) {
            System.out.println("❌ Error al solicitar datos: " + e.getMessage());
            return null;
        }
    }
    
    private Cliente buscarOCrearCliente() {
        System.out.println("1. Buscar cliente existente");
        System.out.println("2. Crear nuevo cliente");
        int opcion = leerEntero("➤ Seleccione una opción: ");
        
        switch (opcion) {
            case 1:
                return buscarClienteExistente();

            case 2:
                return clienteCRUD.solicitarDatosAlta();
            default:
                System.out.println("❌ Opción no válida.");
                return null;
        }
    }
    
    private Cliente buscarClienteExistente() {
        if (clienteCRUD.contarElementos() == 0) {
            System.out.println("❌ No hay clientes registrados en el sistema.");
            return null;
        }
        
        String criterio = leerTexto("➤ Ingrese nombre, apellido o CURP del cliente: ");
        List<Cliente> clientesEncontrados = clienteCRUD.buscarPorCriterio(criterio);
        
        if (clientesEncontrados.isEmpty()) {
            System.out.println("❌ No se encontraron clientes con ese criterio.");
            return null;
        }
        
        if (clientesEncontrados.size() == 1) {
            return clientesEncontrados.get(0);
        }
        
        // Mostrar lista de clientes encontrados
        System.out.println("\nClientes encontrados:");
        for (int i = 0; i < clientesEncontrados.size(); i++) {
            Cliente cliente = clientesEncontrados.get(i);
            System.out.printf("%d. %s (ID: %s)\n", 
                    i + 1, 
                    cliente.getNombreCompleto(), 
                    cliente.getId());
        }
        
        int seleccion = leerEntero("➤ Seleccione un cliente (número): ") - 1;
        
        if (seleccion >= 0 && seleccion < clientesEncontrados.size()) {
            return clientesEncontrados.get(seleccion);
        } else {
            System.out.println("❌ Selección no válida.");
            return null;
        }
    }
    
    private Mascota buscarOCrearMascota() {
        System.out.println("1. Buscar mascota existente");
        System.out.println("2. Crear nueva mascota");
        int opcion = leerEntero("➤ Seleccione una opción: ");

        switch (opcion) {
            case 1:
                return buscarMascotaDisponible();

            case 2:
                return mascotaCRUD.solicitarDatosAlta();
            default:
                System.out.println("❌ Opción no válida.");
                return null;
        }
    }

    private Mascota buscarMascotaDisponible() {

        if (mascotaCRUD.contarElementos() == 0) {
            System.out.println("❌ No hay mascotas registradas en el sistema.");
            return null;
        }
        
        // Mostrar mascotas disponibles (no adoptadas)
        List<Mascota> mascotasDisponibles = mascotaCRUD.listarTodos().stream()
                .filter(m -> !estaAdoptada(m))
                .collect(Collectors.toList());
        
        if (mascotasDisponibles.isEmpty()) {
            System.out.println("❌ No hay mascotas disponibles para adopción.");
            return null;
        }
        
        System.out.println("\nMascotas disponibles para adopción:");
        for (int i = 0; i < mascotasDisponibles.size(); i++) {
            Mascota mascota = mascotasDisponibles.get(i);
            System.out.printf("%d. %s (ID: %s) - Vacunada: %s\n", 
                    i + 1, 
                    mascota.getNombre(), 
                    mascota.getId(),
                    mascota.isVacunada() ? "Sí" : "No");
        }
        
        int seleccion = leerEntero("➤ Seleccione una mascota (número): ") - 1;
        
        if (seleccion >= 0 && seleccion < mascotasDisponibles.size()) {
            return mascotasDisponibles.get(seleccion);
        } else {
            System.out.println("❌ Selección no válida.");
            return null;
        }
    }
    
    private boolean estaAdoptada(Mascota mascota) {
        return elementos.stream()
                .anyMatch(adopcion -> adopcion.getMascota().getId().equals(mascota.getId()));
    }
    
    private LocalDate solicitarFechaAdopcion() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        while (true) {
            String fechaStr = leerTexto("➤ Ingrese la fecha de adopción (dd/MM/yyyy): ");
            
            try {
                LocalDate fecha = LocalDate.parse(fechaStr, formatter);
                
                if (fecha.isAfter(LocalDate.now())) {
                    System.out.println("❌ La fecha no puede ser futura.");
                    continue;
                }
                
                return fecha;
                
            } catch (DateTimeParseException e) {
                System.out.println("❌ Formato de fecha inválido. Use dd/MM/yyyy");
            }
        }
    }
    
    @Override
    public Adopcion solicitarDatosEdicion(Adopcion adopcionExistente) {
        System.out.println("\nEditando adopción: " + adopcionExistente.getResumen());
        System.out.println("Presione Enter para mantener el valor actual:");
        
        // Cliente (no editable por seguridad)
        Cliente cliente = adopcionExistente.getCliente();
        System.out.println("Cliente actual: " + cliente.getNombreCompleto() + " (no editable)");
        
        // Mascota (no editable por seguridad)  
        Mascota mascota = adopcionExistente.getMascota();
        System.out.println("Mascota actual: " + mascota.getNombre() + " (no editable)");
        
        // Fecha de adopción
        String nuevaFechaStr = leerTextoOpcional("➤ Nueva fecha de adopción (" + 
                adopcionExistente.getFechaFormateada() + "): ");
        
        LocalDate nuevaFecha = adopcionExistente.getFechaAdopcion();
        if (!nuevaFechaStr.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                nuevaFecha = LocalDate.parse(nuevaFechaStr, formatter);
                
                if (nuevaFecha.isAfter(LocalDate.now())) {
                    System.out.println("⚠️ La fecha no puede ser futura. Manteniendo fecha original.");
                    nuevaFecha = adopcionExistente.getFechaAdopcion();
                }
            } catch (DateTimeParseException e) {
                System.out.println("⚠️ Formato de fecha inválido. Manteniendo fecha original.");
                nuevaFecha = adopcionExistente.getFechaAdopcion();
            }
        }
        
        // Observaciones
        String observacionesActuales = adopcionExistente.getObservaciones();
        String nuevasObservaciones = leerTextoOpcional("➤ Nuevas observaciones (" + 
                (observacionesActuales.isEmpty() ? "sin observaciones" : observacionesActuales) + "): ");
        
        if (nuevasObservaciones.isEmpty()) {
            nuevasObservaciones = observacionesActuales;
        }
        
        return new Adopcion(adopcionExistente.getIdAdopcion(), cliente, mascota, nuevaFecha, nuevasObservaciones);
    }
    
    @Override
    public void mostrarDetalles(Adopcion adopcion) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    DETALLES DE ADOPCIÓN                     ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║ ID Adopción: " + String.format("%-48s", adopcion.getIdAdopcion()) + "║");
        System.out.println("║ Fecha: " + String.format("%-54s", adopcion.getFechaFormateada()) + "║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║                      DATOS DEL CLIENTE                      ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        Cliente cliente = adopcion.getCliente();
        System.out.println("║ Cliente: " + String.format("%-52s", cliente.getNombreCompleto()) + "║");
        System.out.println("║ ID Cliente: " + String.format("%-48s", cliente.getId()) + "║");
        System.out.println("║ Teléfono: " + String.format("%-50s", cliente.getTelefono()) + "║");
        System.out.println("║ Email: " + String.format("%-53s", cliente.getEmail()) + "║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║                      DATOS DE LA MASCOTA                    ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        Mascota mascota = adopcion.getMascota();
        System.out.println("║ Mascota: " + String.format("%-51s", mascota.getNombre()) + "║");
        System.out.println("║ ID Mascota: " + String.format("%-48s", mascota.getId()) + "║");
        System.out.println("║ Raza: " + String.format("%-54s", mascota.getRaza() != null ? mascota.getRaza() : "N/A") + "║");
        System.out.println("║ Vacunada: " + String.format("%-50s", mascota.isVacunada() ? "Sí" : "No") + "║");
        if (!adopcion.getObservaciones().isEmpty()) {
            System.out.println("╠══════════════════════════════════════════════════════════════╣");
            System.out.println("║ Observaciones: " + String.format("%-45s", 
                              truncarTexto(adopcion.getObservaciones(), 45)) + "║");
        }
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }
    
    @Override
    public void mostrarLista() {
        if (elementos.isEmpty()) {
            System.out.println("❌ No hay adopciones registradas en el sistema.");
            return;
        }
        
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                              LISTA DE ADOPCIONES                             ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║  ID  │                Cliente               │      Mascota      │    Fecha    ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════╣");
        
        // Ordenar por ID de adopción
        elementos.stream()
                .sorted()
                .forEach(adopcion -> {
                    System.out.printf("║ %-4d │ %-33s │ %-17s │ %-11s ║%n",
                            adopcion.getIdAdopcion(),
                            truncarTexto(adopcion.getCliente().getNombreCompleto(), 33),
                            truncarTexto(adopcion.getMascota().getNombre(), 17),
                            adopcion.getFechaFormateada());
                });
        
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝");
        System.out.println("Total de adopciones: " + elementos.size());
    }
    
    @Override
    protected void mostrarEncabezadoAlta() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║          REGISTRAR ADOPCIÓN          ║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    protected void mostrarEncabezadoBaja() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║          ELIMINAR ADOPCIÓN           ║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    protected void mostrarEncabezadoEdicion() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║          EDITAR ADOPCIÓN             ║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    protected Object solicitarIdParaBaja() {
        return leerEntero("➤ Ingrese el ID de la adopción a eliminar: ");
    }
    
    @Override
    protected Object solicitarIdParaEdicion() {
        return leerEntero("➤ Ingrese el ID de la adopción a editar: ");
    }
    
    @Override
    protected String getId(Adopcion elemento) {
        return String.valueOf(elemento.getIdAdopcion());
    }
    
    // Métodos adicionales específicos para adopciones
    
    /**
     * Lista adopciones por cliente
     */
    public List<Adopcion> listarAdopcionesPorCliente(String idCliente) {
        return elementos.stream()
                .filter(adopcion -> adopcion.getCliente().getId().equals(idCliente))
                .collect(Collectors.toList());
    }
    
    /**
     * Lista adopciones en un rango de fechas
     */
    public List<Adopcion> listarAdopcionesPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        return elementos.stream()
                .filter(adopcion -> {
                    LocalDate fecha = adopcion.getFechaAdopcion();
                    return !fecha.isBefore(fechaInicio) && !fecha.isAfter(fechaFin);
                })
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene estadísticas de adopciones
     */
    public void mostrarEstadisticas() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║        ESTADÍSTICAS ADOPCIONES       ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ Total adopciones: " + String.format("%-18d", elementos.size()) + "║");
        
        long adopcionesEsteMes = elementos.stream()
                .filter(a -> a.getFechaAdopcion().getMonth() == LocalDate.now().getMonth() &&
                           a.getFechaAdopcion().getYear() == LocalDate.now().getYear())
                .count();
        
        System.out.println("║ Adopciones este mes: " + String.format("%-13d", adopcionesEsteMes) + "║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    /**
     * Método auxiliar para truncar texto
     */
    private String truncarTexto(String texto, int longitud) {
        if (texto == null) return "";
        return texto.length() > longitud ? texto.substring(0, longitud - 3) + "..." : texto;
    }
}