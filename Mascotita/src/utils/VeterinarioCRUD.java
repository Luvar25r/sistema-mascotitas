package utils;

import modelo.Veterinario;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class VeterinarioCRUD extends OperacionesCRUD<Veterinario> {
    private SimpleDateFormat formatoFecha;
    
    public VeterinarioCRUD() {
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
        System.out.println("║        CONSULTA DE VETERINARIO       ║");
        System.out.println("╚══════════════════════════════════════╝");
        
        if (elementos.isEmpty()) {
            System.out.println("❌ No hay veterinarios registrados en el sistema.");
            return;
        }
        
        System.out.println("\n¿Cómo desea buscar el veterinario?");
        System.out.println("1. Por número de cédula");
        System.out.println("2. Por nombre/apellido/CURP");
        System.out.println("3. Mostrar todos los veterinarios");
        System.out.print("Seleccione una opción: ");
        
        int opcion = leerEntero("");
        
        switch (opcion) {
            case 1:
                consultarPorCedula();
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
    
    private void consultarPorCedula() {
        int cedula = leerEntero("➤ Ingrese el número de cédula: ");
        
        Optional<Veterinario> veterinarioEncontrado = buscarPorId(cedula);
        
        if (veterinarioEncontrado.isPresent()) {
            System.out.println("\n✅ Veterinario encontrado:");
            mostrarDetalles(veterinarioEncontrado.get());
        } else {
            System.out.println("❌ No se encontró un veterinario con la cédula: " + cedula);
        }
    }
    
    private void consultarPorCriterio() {
        String criterio = leerTexto("➤ Ingrese nombre, apellido o CURP a buscar: ");
        
        if (criterio.trim().isEmpty()) {
            System.out.println("❌ Debe ingresar un criterio de búsqueda.");
            return;
        }
        
        List<Veterinario> veterinariosEncontrados = buscarPorCriterio(criterio);
        
        if (veterinariosEncontrados.isEmpty()) {
            System.out.println("❌ No se encontraron veterinarios que coincidan con: " + criterio);
        } else {
            System.out.println("\n✅ Se encontraron " + veterinariosEncontrados.size() + " veterinario(s):");
            mostrarLista();
        }
    }
    
    @Override
    protected String getId(Veterinario veterinario) {
        if (veterinario == null) {
            return null;
        }
        return String.valueOf(veterinario.getNumeroCedula());
    }
    
    @Override
    protected boolean validarDatos(Veterinario veterinario) {
        return veterinario != null 
            && veterinario.getNombre() != null && !veterinario.getNombre().trim().isEmpty()
            && veterinario.getApellidoPaterno() != null && !veterinario.getApellidoPaterno().trim().isEmpty()
            && veterinario.getCurp() != null && !veterinario.getCurp().trim().isEmpty()
            && veterinario.getFechaNacimiento() != null
            && veterinario.getNumeroCedula() > 0;
    }
    
    @Override
    protected void notificarCambio(String tipoOperacion, Veterinario veterinario) {
        System.out.println("══════════════════════════════════════");
        System.out.println("✓ " + tipoOperacion + " DE VETERINARIO EXITOSA");
        System.out.println("Veterinario: " + veterinario.getNombreCompleto());
        System.out.println("Cédula: " + veterinario.getNumeroCedula());
        System.out.println("ID: " + getId(veterinario));
        System.out.println("══════════════════════════════════════");
    }
    
    @Override
    protected Object obtenerIdElemento(Veterinario veterinario) {
        return veterinario.getNumeroCedula();
    }
    
    @Override
    protected boolean coincideConCriterio(Veterinario veterinario, String criterio) {
        String criterioLower = criterio.toLowerCase();
        return veterinario.getNombre().toLowerCase().contains(criterioLower)
            || veterinario.getApellidoPaterno().toLowerCase().contains(criterioLower)
            || (veterinario.getApellidoMaterno() != null && 
                veterinario.getApellidoMaterno().toLowerCase().contains(criterioLower))
            || veterinario.getCurp().toLowerCase().contains(criterioLower);
    }
    
    @Override
    public Veterinario solicitarDatosAlta() {
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
            
            int numeroCedula = leerEntero("➤ Ingrese el número de cédula: ");
            if (numeroCedula <= 0) {
                System.out.println("❌ El número de cédula debe ser mayor a 0.");
                return null;
            }
            
            if (apellidoMaterno != null && !apellidoMaterno.trim().isEmpty()) {
                return new Veterinario(nombre, apellidoPaterno, apellidoMaterno, 
                                     fechaNacimiento, curp, numeroCedula);
            } else {
                return new Veterinario(nombre, apellidoPaterno, 
                                     fechaNacimiento, curp, numeroCedula);
            }
                             
        } catch (Exception e) {
            System.out.println("❌ Error al solicitar datos: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public Veterinario solicitarDatosEdicion(Veterinario veterinarioExistente) {
        try {
            System.out.println("\n📝 Datos actuales del veterinario:");
            mostrarDetalles(veterinarioExistente);
            System.out.println("\n➤ Ingrese los nuevos datos (presione Enter para mantener el valor actual):");
            
            String nombre = leerTexto("➤ Nombre [" + veterinarioExistente.getNombre() + "]: ");
            if (nombre.isEmpty()) nombre = veterinarioExistente.getNombre();
            
            String apellidoPaterno = leerTexto("➤ Apellido paterno [" + veterinarioExistente.getApellidoPaterno() + "]: ");
            if (apellidoPaterno.isEmpty()) apellidoPaterno = veterinarioExistente.getApellidoPaterno();
            
            String apellidoMaternoActual = veterinarioExistente.getApellidoMaterno() != null ? veterinarioExistente.getApellidoMaterno() : "";
            String apellidoMaterno = leerTexto("➤ Apellido materno [" + apellidoMaternoActual + "]: ");
            if (apellidoMaterno.isEmpty()) apellidoMaterno = veterinarioExistente.getApellidoMaterno();
            
            Date fechaNacimiento = veterinarioExistente.getFechaNacimiento();
            String fechaStr = leerTexto("➤ Fecha de nacimiento [" + formatoFecha.format(fechaNacimiento) + "] (dd/MM/yyyy): ");
            if (!fechaStr.isEmpty()) {
                try {
                    fechaNacimiento = formatoFecha.parse(fechaStr.trim());
                } catch (ParseException e) {
                    System.out.println("❌ Formato inválido, manteniendo fecha actual.");
                }
            }
            
            String curp = leerTexto("➤ CURP [" + veterinarioExistente.getCurp() + "]: ");
            if (curp.isEmpty()) curp = veterinarioExistente.getCurp();
            
            if (apellidoMaterno != null && !apellidoMaterno.trim().isEmpty()) {
                return new Veterinario(nombre, apellidoPaterno, apellidoMaterno, 
                                     fechaNacimiento, curp, veterinarioExistente.getNumeroCedula());
            } else {
                return new Veterinario(nombre, apellidoPaterno, 
                                     fechaNacimiento, curp, veterinarioExistente.getNumeroCedula());
            }
                             
        } catch (Exception e) {
            System.out.println("❌ Error al solicitar datos de edición: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public void mostrarDetalles(Veterinario veterinario) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║        DETALLES DEL VETERINARIO      ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ Cédula: " + String.format("%-30s", veterinario.getNumeroCedula()) + "║");
        System.out.println("║ ID: " + String.format("%-32s", getId(veterinario)) + "║");
        System.out.println("║ Nombre: " + String.format("%-30s", veterinario.getNombreCompleto()) + "║");
        System.out.println("║ CURP: " + String.format("%-32s", veterinario.getCurp()) + "║");
        System.out.println("║ Fecha Nac.: " + String.format("%-25s", formatoFecha.format(veterinario.getFechaNacimiento())) + "║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    public void mostrarLista() {
        if (elementos.isEmpty()) {
            System.out.println("❌ No hay veterinarios registrados.");
            return;
        }
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                      LISTA DE VETERINARIOS                    ║");
        System.out.println("╠════════════════════════════════════════════════════════════════╣");
        System.out.println("║  Cédula  │                 Nombre                │    CURP    ║");
        System.out.println("╠════════════════════════════════════════════════════════════════╣");
        
        for (Veterinario veterinario : elementos) {
            System.out.printf("║ %-8d │ %-33s │ %-10s ║%n", 
                            veterinario.getNumeroCedula(),
                            veterinario.getNombreCompleto(),
                            veterinario.getCurp().substring(0, Math.min(10, veterinario.getCurp().length())));
        }
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
    
    @Override
    protected void mostrarEncabezadoAlta() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         ALTA DE VETERINARIO          ║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    protected void mostrarEncabezadoBaja() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         BAJA DE VETERINARIO          ║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    protected void mostrarEncabezadoEdicion() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║       EDICIÓN DE VETERINARIO         ║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    protected Object solicitarIdParaBaja() {
        return leerEntero("➤ Ingrese el número de cédula del veterinario a eliminar: ");
    }
    
    @Override
    protected Object solicitarIdParaEdicion() {
        return leerEntero("➤ Ingrese el número de cédula del veterinario a editar: ");
    }
}