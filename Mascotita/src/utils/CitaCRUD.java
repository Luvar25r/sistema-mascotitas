package utils;

import modelo.Cita;
import modelo.personas.Cliente;
import modelo.Mascota;
import modelo.personas.Veterinario;
import modelo.personas.Asistente;  // ✅ Import correcto
import modelo.Servicio;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;


public class CitaCRUD extends OperacionesCRUD<Cita> {
    private DateTimeFormatter formatoFechaHora;
    private DateTimeFormatter formatoFecha;
    private DateTimeFormatter formatoHora;

    // Referencias a otros CRUDs para validaciones
    private ClienteCRUD clienteCRUD;
    private MascotaCRUD mascotaCRUD;
    private VeterinarioCRUD veterinarioCRUD;
    private AsistenteCRUD asistenteCRUD;
    private ServicioCRUD servicioCRUD;

    public CitaCRUD() {
        super();
        this.formatoFechaHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        this.formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.formatoHora = DateTimeFormatter.ofPattern("HH:mm");
    }

    // Constructor con dependencias de otros CRUDs
    public CitaCRUD(ClienteCRUD clienteCRUD, MascotaCRUD mascotaCRUD,
                   VeterinarioCRUD veterinarioCRUD, AsistenteCRUD asistenteCRUD,
                   ServicioCRUD servicioCRUD) {
        this();
        this.clienteCRUD = clienteCRUD;
        this.mascotaCRUD = mascotaCRUD;
        this.veterinarioCRUD = veterinarioCRUD;
        this.asistenteCRUD = asistenteCRUD;
        this.servicioCRUD = servicioCRUD;
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
        System.out.println("║          CONSULTA DE CITAS           ║");
        System.out.println("╚══════════════════════════════════════╝");

        if (elementos.isEmpty()) {
            System.out.println("❌ No hay citas registradas en el sistema.");
            return;
        }

        System.out.println("\n¿Cómo desea buscar la cita?");
        System.out.println("1. Por número de cita");
        System.out.println("2. Por cliente o mascota");
        System.out.println("3. Por fecha");
        System.out.println("4. Mostrar todas las citas");
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
                consultarPorFecha();
                break;
            case 4:
                mostrarLista();
                break;
            default:
                System.out.println("❌ Opción no válida.");
        }
    }

    private void consultarPorNumero() {
        String numeroCita = leerTexto("➤ Ingrese el número de cita: ");

        Optional<Cita> citaEncontrada = buscarPorId(numeroCita);

        if (citaEncontrada.isPresent()) {
            System.out.println("\n✅ Cita encontrada:");
            mostrarDetalles(citaEncontrada.get());
        } else {
            System.out.println("❌ No se encontró una cita con el número: " + numeroCita);
        }
    }

    private void consultarPorCriterio() {
        String criterio = leerTexto("➤ Ingrese nombre del cliente o mascota a buscar: ");

        if (criterio.trim().isEmpty()) {
            System.out.println("❌ Debe ingresar un criterio de búsqueda.");
            return;
        }

        List<Cita> citasEncontradas = buscarPorCriterio(criterio);

        if (citasEncontradas.isEmpty()) {
            System.out.println("❌ No se encontraron citas que coincidan con: " + criterio);
        } else {
            System.out.println("\n✅ Se encontraron " + citasEncontradas.size() + " cita(s):");
            for (Cita cita : citasEncontradas) {
                mostrarDetalles(cita);
                System.out.println("────────────────────────────────────────");
            }
        }
    }

    private void consultarPorFecha() {
        try {
            String fechaStr = leerTexto("➤ Ingrese la fecha (dd/MM/yyyy): ");
            LocalDateTime fechaBusqueda = LocalDateTime.parse(fechaStr + " 00:00", formatoFechaHora);

            List<Cita> citasDelDia = elementos.stream()
                .filter(cita -> cita.getFechaHora().toLocalDate().equals(fechaBusqueda.toLocalDate()))
                .sorted((c1, c2) -> c1.getFechaHora().compareTo(c2.getFechaHora()))
                .toList();

            if (citasDelDia.isEmpty()) {
                System.out.println("❌ No hay citas programadas para el " + fechaStr);
            } else {
                System.out.println("\n✅ Citas para el " + fechaStr + ":");
                for (Cita cita : citasDelDia) {
                    mostrarResumen(cita);
                }
            }
        } catch (DateTimeParseException e) {
            System.out.println("❌ Formato de fecha inválido. Use dd/MM/yyyy");
        }
    }

    // ========== IMPLEMENTACIÓN DEL MÉTODO getId() ==========

    @Override
    protected String getId(Cita cita) {
        if (cita == null) {
            return null;
        }
        return cita.getNumeroCita();
    }

    // ========== MÉTODOS HEREDADOS Y SOBRESCRITOS ==========

    @Override
    protected boolean validarDatos(Cita cita) {
        if (cita == null) return false;

        // Validaciones básicas
        if (cita.getNumeroCita() == null || cita.getNumeroCita().trim().isEmpty()) {
            System.out.println("❌ El número de cita es obligatorio.");
            return false;
        }

        if (cita.getFechaHora() == null) {
            System.out.println("❌ La fecha y hora son obligatorias.");
            return false;
        }

        if (cita.getCliente() == null) {
            System.out.println("❌ El cliente es obligatorio.");
            return false;
        }

        if (cita.getMascota() == null) {
            System.out.println("❌ La mascota es obligatoria.");
            return false;
        }

        if (cita.getServicios() == null || cita.getServicios().isEmpty()) {
            System.out.println("❌ Debe seleccionar al menos un servicio.");
            return false;
        }

        // Validar que la fecha no sea en el pasado
        if (cita.getFechaHora().isBefore(LocalDateTime.now())) {
            System.out.println("❌ No se pueden agendar citas en fechas pasadas.");
            return false;
        }

        // Validar disponibilidad de horario
        if (existeCitaEnHorario(cita.getFechaHora(), cita.getNumeroCita())) {
            System.out.println("❌ Ya existe una cita programada para esa fecha y hora.");
            return false;
        }

        return true;
    }

    private boolean existeCitaEnHorario(LocalDateTime fechaHora, String numeroCitaExcluir) {
        return elementos.stream()
            .anyMatch(cita -> !cita.getNumeroCita().equals(numeroCitaExcluir)
                         && cita.getFechaHora().equals(fechaHora));
    }

    @Override
    protected void notificarCambio(String tipoOperacion, Cita cita) {
        System.out.println("══════════════════════════════════════");
        System.out.println("✓ " + tipoOperacion + " DE CITA EXITOSA");
        System.out.println("Número de Cita: " + cita.getNumeroCita());
        System.out.println("Cliente: " + (cita.getCliente() != null ? cita.getCliente().getNombreCompleto() : "N/A"));
        System.out.println("Mascota: " + (cita.getMascota() != null ? cita.getMascota().getNombre() : "N/A"));
        System.out.println("Fecha: " + cita.getFechaHora().format(formatoFechaHora));
        System.out.println("══════════════════════════════════════");
    }

    @Override
    protected Object obtenerIdElemento(Cita cita) {
        return cita.getNumeroCita();
    }

    @Override
    protected boolean coincideConCriterio(Cita cita, String criterio) {
        String criterioLower = criterio.toLowerCase();

        // Buscar en cliente
        if (cita.getCliente() != null) {
            String nombreCliente = cita.getCliente().getNombreCompleto().toLowerCase();
            if (nombreCliente.contains(criterioLower)) {
                return true;
            }
        }

        // Buscar en mascota
        if (cita.getMascota() != null) {
            String nombreMascota = cita.getMascota().getNombre().toLowerCase();
            if (nombreMascota.contains(criterioLower)) {
                return true;
            }
        }

        // Buscar en número de cita
        if (cita.getNumeroCita().toLowerCase().contains(criterioLower)) {
            return true;
        }

        return false;
    }
    private modelo.Asistente seleccionarAsistente() { // ✅ Cambiar tipo de retorno
        if (asistenteCRUD == null) {
            System.out.println("⚠️ Sistema de asistentes no disponible.");
            return null;
        }

        boolean necesitaAsistente = leerBooleano("➤ ¿Esta cita requiere asistente?");

        if (necesitaAsistente) {
            asistenteCRUD.mostrarLista();
            int idAsistente = leerEntero("➤ Ingrese el ID del asistente: ");

            // ✅ SOLUCIÓN: Convertir de modelo.personas.Asistente a modelo.Asistente
            Optional<modelo.personas.Asistente> asistentePersonas = asistenteCRUD.buscarPorId(idAsistente);
            if (asistentePersonas.isPresent()) {
                modelo.personas.Asistente asistenteOriginal = asistentePersonas.get();

                // Crear una instancia de modelo.Asistente con los datos de modelo.personas.Asistente
                return new modelo.Asistente(
                        asistenteOriginal.getNombre(),
                        asistenteOriginal.getApellidoPaterno(),
                        asistenteOriginal.getApellidoMaterno(),
                        asistenteOriginal.getFechaNacimiento(),
                        asistenteOriginal.getCurp(),
                        asistenteOriginal.getIdAsistente()
                );
            } else {
                System.out.println("❌ Asistente no encontrado.");
                return null;
            }
        }

        return null;
    }





    @Override
    public Cita solicitarDatosAlta() {
        try {
            // Generar número de cita automático
            String numeroCita = generarNumeroCita();
            System.out.println("📝 Número de cita asignado: " + numeroCita);

            // Solicitar fecha y hora
            LocalDateTime fechaHora = null;
            while (fechaHora == null) {
                try {
                    String fechaStr = leerTexto("➤ Ingrese fecha (dd/MM/yyyy): ");
                    String horaStr = leerTexto("➤ Ingrese hora (HH:mm): ");
                    fechaHora = LocalDateTime.parse(fechaStr + " " + horaStr, formatoFechaHora);

                    if (fechaHora.isBefore(LocalDateTime.now())) {
                        System.out.println("❌ No se pueden agendar citas en fechas pasadas.");
                        fechaHora = null;
                        continue;
                    }

                    if (existeCitaEnHorario(fechaHora, numeroCita)) {
                        System.out.println("❌ Ya existe una cita en esa fecha y hora.");
                        fechaHora = null;
                        continue;
                    }

                    System.out.println("✅ Fecha y hora válidas: " + fechaHora.format(formatoFechaHora));
                } catch (DateTimeParseException e) {
                    System.out.println("❌ Formato inválido. Use dd/MM/yyyy para fecha y HH:mm para hora");
                }
            }

            // Seleccionar cliente
            Cliente cliente = seleccionarCliente();
            if (cliente == null) return null;

            // Seleccionar mascota
            Mascota mascota = seleccionarMascota();
            if (mascota == null) return null;

            // Seleccionar servicios
            List<Servicio> servicios = seleccionarServicios();
            if (servicios == null || servicios.isEmpty()) return null;

            // Seleccionar veterinario y asistente según servicios
            Veterinario veterinario = seleccionarVeterinario();
            modelo.Asistente asistente = seleccionarAsistente(); // ✅ Ahora usando el tipo correcto

            return new Cita(numeroCita, fechaHora, cliente, mascota, servicios, veterinario, asistente);

        } catch (Exception e) {
            System.out.println("❌ Error al solicitar datos: " + e.getMessage());
            return null;
        }
    }

    private String generarNumeroCita() {
        int maxNumero = elementos.stream()
            .mapToInt(cita -> {
                try {
                    String numero = cita.getNumeroCita().replace("CT-", "");
                    return Integer.parseInt(numero);
                } catch (NumberFormatException e) {
                    return 0;
                }
            })
            .max()
            .orElse(1000);

        return "CT-" + String.format("%04d", maxNumero + 1);
    }

    private Cliente seleccionarCliente() {
        if (clienteCRUD == null) {
            System.out.println("❌ Sistema de clientes no disponible.");
            return null;
        }

        clienteCRUD.mostrarLista();
        int numeroCliente = leerEntero("➤ Ingrese el número del cliente: ");

        Optional<Cliente> cliente = clienteCRUD.buscarPorId(numeroCliente);
        if (cliente.isPresent()) {
            return cliente.get();
        } else {
            System.out.println("❌ Cliente no encontrado.");
            return null;
        }
    }

    private Mascota seleccionarMascota() {
        if (mascotaCRUD == null) {
            System.out.println("❌ Sistema de mascotas no disponible.");
            return null;
        }

        mascotaCRUD.mostrarLista();
        String idMascota = leerTexto("➤ Ingrese el número de la mascota: ");

        Optional<Mascota> mascota = mascotaCRUD.buscarPorId(idMascota);
        if (mascota.isPresent()) {
            return mascota.get();
        } else {
            System.out.println("❌ Mascota no encontrada.");
            return null;
        }
    }

    private List<Servicio> seleccionarServicios() {
        if (servicioCRUD == null) {
            System.out.println("❌ Sistema de servicios no disponible.");
            return null;
        }

        List<Servicio> serviciosSeleccionados = new ArrayList<>();
        double costoTotal = 0;

        while (true) {
            servicioCRUD.mostrarLista();
            System.out.println("\nServicios seleccionados: " + serviciosSeleccionados.size());
            System.out.println("Costo acumulado: $" + costoTotal);
            String opcion = leerTexto("➤ Ingrese nombre del servicio ('0' para terminar): ");

            if ("0".equals(opcion)) {
                break;
            }

            if (opcion.trim().isEmpty()) {
                System.out.println("❌ No puede estar vacío. Ingrese un nombre válido o '0' para salir.");
                continue;
            }

            Optional<Servicio> servicio = servicioCRUD.consultarPorNombre(opcion);

            if (servicio.isPresent()) {
                if (!serviciosSeleccionados.contains(servicio.get())) {
                    serviciosSeleccionados.add(servicio.get());
                    costoTotal += servicio.get().getPrecio(); // ✅ Acumular precio
                    System.out.println("✅ Servicio agregado: " + servicio.get().getNombre() + " | Costo: $" + servicio.get().getPrecio());
                } else {
                    System.out.println("❌ El servicio ya está seleccionado.");
                }
            } else {
                System.out.println("❌ Servicio no encontrado. Intente nuevamente.");
            }
        }

        if (serviciosSeleccionados.isEmpty()) {
            System.out.println("❌ Debe seleccionar al menos un servicio.");
            return null;
        }

        System.out.println("💰 Total de servicios seleccionados: $" + costoTotal);
        return serviciosSeleccionados;
    }
    private Veterinario seleccionarVeterinario() {
        if (veterinarioCRUD == null) {
            System.out.println("⚠️ Sistema de veterinarios no disponible.");
            return null;
        }

        boolean necesitaVeterinario = leerBooleano("➤ ¿Esta cita requiere veterinario?");

        if (necesitaVeterinario) {
            veterinarioCRUD.mostrarLista();
            int cedula = leerEntero("➤ Ingrese la cédula del veterinario: ");

            Optional<Veterinario> veterinario = veterinarioCRUD.buscarPorId(cedula);
            if (veterinario.isPresent()) {
                return veterinario.get();
            } else {
                System.out.println("❌ Veterinario no encontrado.");
                return null;
            }
        }

        return null;
    }


    @Override
    public Cita solicitarDatosEdicion(Cita citaExistente) {
        try {
            System.out.println("\n📝 Datos actuales de la cita:");
            mostrarDetalles(citaExistente);
            System.out.println("\n➤ Ingrese los nuevos datos (presione Enter para mantener el valor actual):");

            // Fecha y hora
            LocalDateTime fechaHora = citaExistente.getFechaHora();
            String fechaStr = leerTexto("➤ Nueva fecha [" + fechaHora.format(formatoFecha) + "] (dd/MM/yyyy): ");
            String horaStr = leerTexto("➤ Nueva hora [" + fechaHora.format(formatoHora) + "] (HH:mm): ");

            if (!fechaStr.isEmpty() || !horaStr.isEmpty()) {
                try {
                    String fechaCompleta = fechaStr.isEmpty() ? fechaHora.format(formatoFecha) : fechaStr;
                    String horaCompleta = horaStr.isEmpty() ? fechaHora.format(formatoHora) : horaStr;

                    LocalDateTime nuevaFechaHora = LocalDateTime.parse(fechaCompleta + " " + horaCompleta, formatoFechaHora);

                    if (nuevaFechaHora.isBefore(LocalDateTime.now())) {
                        System.out.println("❌ No se pueden programar citas en fechas pasadas. Manteniendo fecha actual.");
                    } else if (existeCitaEnHorario(nuevaFechaHora, citaExistente.getNumeroCita())) {
                        System.out.println("❌ Ya existe una cita en esa fecha y hora. Manteniendo fecha actual.");
                    } else {
                        fechaHora = nuevaFechaHora;
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("❌ Formato inválido, manteniendo fecha actual.");
                }
            }

            // Mantener otros datos o permitir cambiarlos
            String cambiarDatos = leerTexto("➤ ¿Desea cambiar cliente, mascota o servicios? (s/n): ");

            Cliente cliente = citaExistente.getCliente();
            Mascota mascota = citaExistente.getMascota();
            List<Servicio> servicios = citaExistente.getServicios();
            Veterinario veterinario = citaExistente.getVeterinario();
            modelo.Asistente asistente = citaExistente.getAsistente(); // ✅ Tipo correcto

            if (cambiarDatos.toLowerCase().startsWith("s")) {
                Cliente nuevoCliente = seleccionarCliente();
                if (nuevoCliente != null) cliente = nuevoCliente;

                Mascota nuevaMascota = seleccionarMascota();
                if (nuevaMascota != null) mascota = nuevaMascota;

                List<Servicio> nuevosServicios = seleccionarServicios();
                if (nuevosServicios != null && !nuevosServicios.isEmpty()) {
                    servicios = nuevosServicios;
                }

                Veterinario nuevoVeterinario = seleccionarVeterinario();
                veterinario = nuevoVeterinario;

                modelo.Asistente nuevoAsistente = seleccionarAsistente(); // ✅ Tipo correcto
                asistente = nuevoAsistente;
            }

            return new Cita(citaExistente.getNumeroCita(), fechaHora, cliente, mascota, servicios, veterinario, asistente);

        } catch (Exception e) {
            System.out.println("❌ Error al solicitar datos de edición: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void mostrarDetalles(Cita cita) {
        System.out.println("╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                          DETALLES DE LA CITA                        ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════╣");
        System.out.printf("║ Número: %-60s ║%n", cita.getNumeroCita());
        System.out.printf("║ Fecha y Hora: %-55s ║%n", cita.getFechaHora().format(formatoFechaHora));
        System.out.printf("║ Cliente: %-59s ║%n",
            cita.getCliente() != null ? cita.getCliente().getNombreCompleto() : "N/A");
        System.out.printf("║ Mascota: %-59s ║%n",
            cita.getMascota() != null ? cita.getMascota().getNombre() : "N/A");
        System.out.printf("║ Servicios: %-57s ║%n", cita.getDescripcionServicio());
        System.out.printf("║ Veterinario: %-56s ║%n",
            cita.getVeterinario() != null ? cita.getVeterinario().getNombreCompleto() : "No asignado");
        System.out.printf("║ Asistente: %-58s ║%n",
            cita.getAsistente() != null ? cita.getAsistente().getNombreCompleto() : "No asignado");
        System.out.println("╚══════════════════════════════════════════════════════════════════════╝");
    }

    private void mostrarResumen(Cita cita) {
        System.out.printf("🕐 %s | %s | %s (%s)%n",
            cita.getFechaHora().format(formatoHora),
            cita.getNumeroCita(),
            cita.getCliente() != null ? cita.getCliente().getNombreCompleto() : "N/A",
            cita.getMascota() != null ? cita.getMascota().getNombre() : "N/A");
    }

    @Override
    public void mostrarLista() {
        if (elementos.isEmpty()) {
            System.out.println("❌ No hay citas registradas.");
            return;
        }

        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                    LISTA DE CITAS                                        ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║  Número  │      Fecha/Hora      │        Cliente        │     Mascota     │   Estado   ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════════════╣");

        List<Cita> citasOrdenadas = elementos.stream()
            .sorted((c1, c2) -> c1.getFechaHora().compareTo(c2.getFechaHora()))
            .toList();

        for (Cita cita : citasOrdenadas) {
            String estado = cita.getFechaHora().isBefore(LocalDateTime.now()) ? "Pasada" : "Programada";
            String cliente = cita.getCliente() != null ?
                limitarTexto(cita.getCliente().getNombreCompleto(), 20) : "N/A";
            String mascota = cita.getMascota() != null ?
                limitarTexto(cita.getMascota().getNombre(), 15) : "N/A";

            System.out.printf("║ %-8s │ %-20s │ %-20s │ %-15s │ %-10s ║%n",
                cita.getNumeroCita(),
                cita.getFechaHora().format(formatoFechaHora),
                cliente,
                mascota,
                estado);
        }
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println("Total de citas: " + elementos.size());
    }

    private String limitarTexto(String texto, int limite) {
        if (texto == null) return "";
        return texto.length() > limite ? texto.substring(0, limite - 3) + "..." : texto;
    }

    @Override
    protected void mostrarEncabezadoAlta() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║           AGENDAR NUEVA CITA         ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("Complete la información para agendar");
        System.out.println("una nueva cita veterinaria.");
    }

    @Override
    protected void mostrarEncabezadoBaja() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║           CANCELAR CITA              ║");
        System.out.println("╚══════════════════════════════════════╝");
    }

    @Override
    protected void mostrarEncabezadoEdicion() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║          MODIFICAR CITA              ║");
        System.out.println("╚══════════════════════════════════════╝");
    }

    @Override
    protected Object solicitarIdParaBaja() {
        return leerTexto("➤ Ingrese el número de la cita a cancelar: ");
    }

    @Override
    protected Object solicitarIdParaEdicion() {
        return leerTexto("➤ Ingrese el número de la cita a modificar: ");
    }

    // ========== MÉTODOS ADICIONALES ESPECÍFICOS PARA CITAS ==========

    /**
     * Obtiene las citas programadas (futuras)
     */
    public List<Cita> getCitasProgramadas() {
        return elementos.stream()
            .filter(cita -> cita.getFechaHora().isAfter(LocalDateTime.now()))
            .sorted((c1, c2) -> c1.getFechaHora().compareTo(c2.getFechaHora()))
            .toList();
    }

    /**
     * Obtiene las citas pasadas
     */
    public List<Cita> getCitasPasadas() {
        return elementos.stream()
            .filter(cita -> cita.getFechaHora().isBefore(LocalDateTime.now()))
            .sorted((c1, c2) -> c2.getFechaHora().compareTo(c1.getFechaHora()))
            .toList();
    }

    /**
     * Obtiene las citas de hoy
     */
    public List<Cita> getCitasHoy() {
        LocalDateTime ahora = LocalDateTime.now();
        return elementos.stream()
            .filter(cita -> cita.getFechaHora().toLocalDate().equals(ahora.toLocalDate()))
            .sorted((c1, c2) -> c1.getFechaHora().compareTo(c2.getFechaHora()))
            .toList();
    }

    /**
     * Verifica si hay conflictos de horario para un veterinario
     */
    public boolean hayConflictoVeterinario(Veterinario veterinario, LocalDateTime fechaHora, String numeroCitaExcluir) {
        return elementos.stream()
            .anyMatch(cita -> !cita.getNumeroCita().equals(numeroCitaExcluir)
                         && cita.getVeterinario() != null
                         && cita.getVeterinario().equals(veterinario)
                         && cita.getFechaHora().equals(fechaHora));
    }

    /**
     * Verifica si hay conflictos de horario para un asistente
     */
    public boolean hayConflictoAsistente(Asistente asistente, LocalDateTime fechaHora, String numeroCitaExcluir) {
        return elementos.stream()
            .anyMatch(cita -> !cita.getNumeroCita().equals(numeroCitaExcluir)
                         && cita.getAsistente() != null
                         && cita.getAsistente().equals(asistente)
                         && cita.getFechaHora().equals(fechaHora));
    }

    // Setters para inyección de dependencias
    public void setClienteCRUD(ClienteCRUD clienteCRUD) {
        this.clienteCRUD = clienteCRUD;
    }

    public void setMascotaCRUD(MascotaCRUD mascotaCRUD) {
        this.mascotaCRUD = mascotaCRUD;
    }

    public void setVeterinarioCRUD(VeterinarioCRUD veterinarioCRUD) {
        this.veterinarioCRUD = veterinarioCRUD;
    }

    public void setAsistenteCRUD(AsistenteCRUD asistenteCRUD) {
        this.asistenteCRUD = asistenteCRUD;
    }

    public void setServicioCRUD(ServicioCRUD servicioCRUD) {
        this.servicioCRUD = servicioCRUD;
    }
}