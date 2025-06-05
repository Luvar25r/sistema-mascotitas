package utils;

import modelo.personas.Asistente;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AsistenteCRUD extends OperacionesCRUD<Asistente> {
    private SimpleDateFormat formatoFecha;
    
    public AsistenteCRUD() {
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
        System.out.println("║         CONSULTA DE ASISTENTE        ║");
        System.out.println("╚══════════════════════════════════════╝");
        
        if (elementos.isEmpty()) {
            System.out.println("❌ No hay asistentes registrados en el sistema.");
            return;
        }
        
        System.out.println("\n¿Cómo desea buscar el asistente?");
        System.out.println("1. Por ID de asistente");
        System.out.println("2. Por nombre/apellido/CURP");
        System.out.println("3. Mostrar todos los asistentes");
        System.out.print("Seleccione una opción: ");
        
        int opcion = leerEntero("");
        
        switch (opcion) {
            case 1:
                consultarPorId();
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
    
    private void consultarPorId() {
        int idAsistente = leerEntero("➤ Ingrese el ID del asistente: ");
        
        Optional<Asistente> asistenteEncontrado = buscarPorId(idAsistente);
        
        if (asistenteEncontrado.isPresent()) {
            System.out.println("\n✅ Asistente encontrado:");
            mostrarDetalles(asistenteEncontrado.get());
        } else {
            System.out.println("❌ No se encontró un asistente con el ID: " + idAsistente);
        }
    }
    
    private void consultarPorCriterio() {
        String criterio = leerTexto("➤ Ingrese nombre, apellido o CURP a buscar: ");
        
        if (criterio.trim().isEmpty()) {
            System.out.println("❌ Debe ingresar un criterio de búsqueda.");
            return;
        }
        
        List<Asistente> asistentesEncontrados = buscarPorCriterio(criterio);
        
        if (asistentesEncontrados.isEmpty()) {
            System.out.println("❌ No se encontraron asistentes que coincidan con: " + criterio);
        } else {
            System.out.println("\n✅ Se encontraron " + asistentesEncontrados.size() + " asistente(s):");
            for (Asistente asistente : asistentesEncontrados) {
                mostrarDetalles(asistente);
                System.out.println("────────────────────────────────────────");
            }
        }
    }
    
    // ========== IMPLEMENTACIÓN DEL MÉTODO getId() ==========
    
    @Override
    protected String getId(Asistente asistente) {
        if (asistente == null) {
            return null;
        }
        return String.valueOf(asistente.getIdAsistente());
    }
    
    // ========== MÉTODOS HEREDADOS Y SOBRESCRITOS ==========
    
    @Override
    protected boolean validarDatos(Asistente asistente) {
        return asistente != null 
            && asistente.getNombre() != null && !asistente.getNombre().trim().isEmpty()
            && asistente.getApellidoPaterno() != null && !asistente.getApellidoPaterno().trim().isEmpty()
            && asistente.getCurp() != null && !asistente.getCurp().trim().isEmpty()
            && asistente.getFechaNacimiento() != null
            && asistente.getIdAsistente() > 0;
    }
    
    @Override
    protected void notificarCambio(String tipoOperacion, Asistente asistente) {
        System.out.println("══════════════════════════════════════");
        System.out.println("✓ " + tipoOperacion + " DE ASISTENTE EXITOSA");
        System.out.println("Asistente: " + asistente.getNombreCompleto());
        System.out.println("ID: " + asistente.getIdAsistente());
        System.out.println("CURP: " + asistente.getCurp());
        System.out.println("══════════════════════════════════════");
    }
    
    @Override
    protected Object obtenerIdElemento(Asistente asistente) {
        return asistente.getIdAsistente();
    }
    
    @Override
    protected boolean coincideConCriterio(Asistente asistente, String criterio) {
        String criterioLower = criterio.toLowerCase();
        return asistente.getNombre().toLowerCase().contains(criterioLower)
            || asistente.getApellidoPaterno().toLowerCase().contains(criterioLower)
            || (asistente.getApellidoMaterno() != null && 
                asistente.getApellidoMaterno().toLowerCase().contains(criterioLower))
            || asistente.getCurp().toLowerCase().contains(criterioLower);
    }
    
    @Override
    public Asistente solicitarDatosAlta() {
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
            
            int idAsistente = leerEntero("➤ Ingrese el ID del asistente: ");
            if (idAsistente <= 0) {
                System.out.println("❌ El ID del asistente debe ser mayor a 0.");
                return null;
            }
            
            // Verificar si ya existe un asistente con ese ID
            if (existeById(idAsistente)) {
                System.out.println("❌ Ya existe un asistente con el ID: " + idAsistente);
                return null;
            }
            
            if (apellidoMaterno != null && !apellidoMaterno.trim().isEmpty()) {
                return new Asistente(nombre, apellidoPaterno, apellidoMaterno, 
                                   fechaNacimiento, curp, idAsistente);
            } else {
                return new Asistente(nombre, apellidoPaterno, 
                                   fechaNacimiento, curp, idAsistente);
            }
                             
        } catch (Exception e) {
            System.out.println("❌ Error al solicitar datos: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public Asistente solicitarDatosEdicion(Asistente asistenteExistente) {
        try {
            System.out.println("\n📝 Datos actuales del asistente:");
            mostrarDetalles(asistenteExistente);
            System.out.println("\n➤ Ingrese los nuevos datos (presione Enter para mantener el valor actual):");
            
            String nombre = leerTexto("➤ Nombre [" + asistenteExistente.getNombre() + "]: ");
            if (nombre.isEmpty()) nombre = asistenteExistente.getNombre();
            
            String apellidoPaterno = leerTexto("➤ Apellido paterno [" + asistenteExistente.getApellidoPaterno() + "]: ");
            if (apellidoPaterno.isEmpty()) apellidoPaterno = asistenteExistente.getApellidoPaterno();
            
            String apellidoMaternoActual = asistenteExistente.getApellidoMaterno() != null ? 
                asistenteExistente.getApellidoMaterno() : "";
            String apellidoMaterno = leerTexto("➤ Apellido materno [" + apellidoMaternoActual + "]: ");
            if (apellidoMaterno.isEmpty()) apellidoMaterno = asistenteExistente.getApellidoMaterno();
            
            Date fechaNacimiento = asistenteExistente.getFechaNacimiento();
            String fechaStr = leerTexto("➤ Fecha de nacimiento [" + formatoFecha.format(fechaNacimiento) + "] (dd/MM/yyyy): ");
            if (!fechaStr.isEmpty()) {
                try {
                    fechaNacimiento = formatoFecha.parse(fechaStr.trim());
                } catch (ParseException e) {
                    System.out.println("❌ Formato inválido, manteniendo fecha actual.");
                }
            }
            
            String curp = leerTexto("➤ CURP [" + asistenteExistente.getCurp() + "]: ");
            if (curp.isEmpty()) curp = asistenteExistente.getCurp();
            
            if (apellidoMaterno != null && !apellidoMaterno.trim().isEmpty()) {
                return new Asistente(nombre, apellidoPaterno, apellidoMaterno, 
                                   fechaNacimiento, curp, asistenteExistente.getIdAsistente());
            } else {
                return new Asistente(nombre, apellidoPaterno, 
                                   fechaNacimiento, curp, asistenteExistente.getIdAsistente());
            }
                             
        } catch (Exception e) {
            System.out.println("❌ Error al solicitar datos de edición: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public void mostrarDetalles(Asistente asistente) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║          DETALLES DEL ASISTENTE      ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ ID: " + String.format("%-34s", asistente.getIdAsistente()) + "║");
        System.out.println("║ Nombre: " + String.format("%-30s", asistente.getNombreCompleto()) + "║");
        System.out.println("║ CURP: " + String.format("%-32s", asistente.getCurp()) + "║");
        System.out.println("║ Fecha Nac.: " + String.format("%-25s", formatoFecha.format(asistente.getFechaNacimiento())) + "║");
        System.out.println("║ Funciones: Apoyo en consultas       ║");
        System.out.println("║           Asistencia médica          ║");
        System.out.println("║           Cuidado de pacientes       ║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    public void mostrarLista() {
        if (elementos.isEmpty()) {
            System.out.println("❌ No hay asistentes registrados.");
            return;
        }
        
        System.out.println("\n╔═════════════════════════════════════════════════════════════════╗");
        System.out.println("║                        LISTA DE ASISTENTES                     ║");
        System.out.println("╠═════════════════════════════════════════════════════════════════╣");
        System.out.println("║  ID  │                Nombre Completo               │   CURP     ║");
        System.out.println("╠═════════════════════════════════════════════════════════════════╣");
        
        for (Asistente asistente : elementos) {
            String curpCorto = asistente.getCurp().length() > 10 ? 
                asistente.getCurp().substring(0, 10) + "..." : asistente.getCurp();
            System.out.printf("║ %-4d │ %-43s │ %-10s ║%n", 
                            asistente.getIdAsistente(),
                            asistente.getNombreCompleto(),
                            curpCorto);
        }
        System.out.println("╚═════════════════════════════════════════════════════════════════╝");
        System.out.println("Total de asistentes: " + elementos.size());
    }
    
    @Override
    protected void mostrarEncabezadoAlta() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║          ALTA DE ASISTENTE           ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("Los asistentes brindan apoyo fundamental");
        System.out.println("en las consultas veterinarias y el");
        System.out.println("cuidado de los pacientes.");
    }
    
    @Override
    protected void mostrarEncabezadoBaja() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║          BAJA DE ASISTENTE           ║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    protected void mostrarEncabezadoEdicion() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║        EDICIÓN DE ASISTENTE          ║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    protected Object solicitarIdParaBaja() {
        return leerEntero("➤ Ingrese el ID del asistente a eliminar: ");
    }
    
    @Override
    protected Object solicitarIdParaEdicion() {
        return leerEntero("➤ Ingrese el ID del asistente a editar: ");
    }


}