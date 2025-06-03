package utils;

import modelo.personas.Gerente;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class GerenteCRUD extends OperacionesCRUD<Gerente> {
    private SimpleDateFormat formatoFecha;
    
    public GerenteCRUD() {
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
        System.out.println("║          CONSULTA DE GERENTE         ║");
        System.out.println("╚══════════════════════════════════════╝");
        
        if (elementos.isEmpty()) {
            System.out.println("❌ No hay gerentes registrados en el sistema.");
            return;
        }
        
        System.out.println("\n¿Cómo desea buscar el gerente?");
        System.out.println("1. Por número de gerente");
        System.out.println("2. Por nombre/apellido/CURP");
        System.out.println("3. Mostrar todos los gerentes");
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
        int numeroGerente = leerEntero("➤ Ingrese el número de gerente: ");
        
        Optional<Gerente> gerenteEncontrado = buscarPorId(numeroGerente);
        
        if (gerenteEncontrado.isPresent()) {
            System.out.println("\n✅ Gerente encontrado:");
            mostrarDetalles(gerenteEncontrado.get());
        } else {
            System.out.println("❌ No se encontró un gerente con el número: " + numeroGerente);
        }
    }
    
    private void consultarPorCriterio() {
        String criterio = leerTexto("➤ Ingrese nombre, apellido o CURP a buscar: ");
        
        if (criterio.trim().isEmpty()) {
            System.out.println("❌ Debe ingresar un criterio de búsqueda.");
            return;
        }
        
        List<Gerente> gerentesEncontrados = buscarPorCriterio(criterio);
        
        if (gerentesEncontrados.isEmpty()) {
            System.out.println("❌ No se encontraron gerentes que coincidan con: " + criterio);
        } else {
            System.out.println("\n✅ Se encontraron " + gerentesEncontrados.size() + " gerente(s):");
            mostrarLista();
        }
    }
    
    // ========== IMPLEMENTACIÓN DEL NUEVO MÉTODO getId() ==========
    
    protected String getId(Gerente gerente) {
        if (gerente == null) {
            return null;
        }
        return String.valueOf(gerente.getNumeroGerente());
    }
    
    // ========== MÉTODOS HEREDADOS Y SOBRESCRITOS ==========
    
    @Override
    protected boolean validarDatos(Gerente gerente) {
        return gerente != null 
            && gerente.getNombre() != null && !gerente.getNombre().trim().isEmpty()
            && gerente.getApellidoPaterno() != null && !gerente.getApellidoPaterno().trim().isEmpty()
            && gerente.getCurp() != null && !gerente.getCurp().trim().isEmpty()
            && gerente.getFechaNacimiento() != null
            && gerente.getNumeroGerente() > 0;
    }
    
    @Override
    protected void notificarCambio(String tipoOperacion, Gerente gerente) {
        System.out.println("══════════════════════════════════════");
        System.out.println("✓ " + tipoOperacion + " DE GERENTE EXITOSA");
        System.out.println("Gerente: " + gerente.getNombreCompleto());
        System.out.println("Número: " + gerente.getNumeroGerente());
        System.out.println("ID: " + getId(gerente));
        System.out.println("Sucursal: " + (gerente.isTieneSucursal() ? gerente.getSucursal() : "Sin sucursal"));
        System.out.println("══════════════════════════════════════");
    }
    
    @Override
    protected Object obtenerIdElemento(Gerente gerente) {
        return gerente.getNumeroGerente();
    }
    
    @Override
    protected boolean coincideConCriterio(Gerente gerente, String criterio) {
        String criterioLower = criterio.toLowerCase();
        return gerente.getNombre().toLowerCase().contains(criterioLower)
            || gerente.getApellidoPaterno().toLowerCase().contains(criterioLower)
            || (gerente.getApellidoMaterno() != null && 
                gerente.getApellidoMaterno().toLowerCase().contains(criterioLower))
            || gerente.getCurp().toLowerCase().contains(criterioLower)
            || (gerente.getSucursal() != null && 
                gerente.getSucursal().toLowerCase().contains(criterioLower));
    }
    
    @Override
    public Gerente solicitarDatosAlta() {
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
            
            int numeroGerente = leerEntero("➤ Ingrese el número de gerente: ");
            if (numeroGerente <= 0) {
                System.out.println("❌ El número de gerente debe ser mayor a 0.");
                return null;
            }
            
            boolean tieneSucursal = leerBooleano("➤ ¿Tiene sucursal asignada?");
            String sucursal = null;
            
            if (tieneSucursal) {
                sucursal = leerTexto("➤ Ingrese el nombre de la sucursal: ");
                if (sucursal.isEmpty()) {
                    System.out.println("❌ Debe especificar el nombre de la sucursal.");
                    return null;
                }
            }
            
            if (apellidoMaterno != null && !apellidoMaterno.trim().isEmpty()) {
                return new Gerente(nombre, apellidoPaterno, apellidoMaterno, 
                                 fechaNacimiento, curp, numeroGerente, tieneSucursal, sucursal);
            } else {
                return new Gerente(nombre, apellidoPaterno, 
                                 fechaNacimiento, curp, numeroGerente, tieneSucursal, sucursal);
            }
                             
        } catch (Exception e) {
            System.out.println("❌ Error al solicitar datos: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public Gerente solicitarDatosEdicion(Gerente gerenteExistente) {
        try {
            System.out.println("\n📝 Datos actuales del gerente:");
            mostrarDetalles(gerenteExistente);
            System.out.println("\n➤ Ingrese los nuevos datos (presione Enter para mantener el valor actual):");
            
            String nombre = leerTexto("➤ Nombre [" + gerenteExistente.getNombre() + "]: ");
            if (nombre.isEmpty()) nombre = gerenteExistente.getNombre();
            
            String apellidoPaterno = leerTexto("➤ Apellido paterno [" + gerenteExistente.getApellidoPaterno() + "]: ");
            if (apellidoPaterno.isEmpty()) apellidoPaterno = gerenteExistente.getApellidoPaterno();
            
            String apellidoMaternoActual = gerenteExistente.getApellidoMaterno() != null ? gerenteExistente.getApellidoMaterno() : "";
            String apellidoMaterno = leerTexto("➤ Apellido materno [" + apellidoMaternoActual + "]: ");
            if (apellidoMaterno.isEmpty()) apellidoMaterno = gerenteExistente.getApellidoMaterno();
            
            Date fechaNacimiento = gerenteExistente.getFechaNacimiento();
            String fechaStr = leerTexto("➤ Fecha de nacimiento [" + formatoFecha.format(fechaNacimiento) + "] (dd/MM/yyyy): ");
            if (!fechaStr.isEmpty()) {
                try {
                    fechaNacimiento = formatoFecha.parse(fechaStr.trim());
                } catch (ParseException e) {
                    System.out.println("❌ Formato inválido, manteniendo fecha actual.");
                }
            }
            
            String curp = leerTexto("➤ CURP [" + gerenteExistente.getCurp() + "]: ");
            if (curp.isEmpty()) curp = gerenteExistente.getCurp();
            
            String tieneSucursalStr = leerTexto("➤ ¿Tiene sucursal? [" + (gerenteExistente.isTieneSucursal() ? "Sí" : "No") + "] (s/n): ");
            boolean tieneSucursal = gerenteExistente.isTieneSucursal();
            if (!tieneSucursalStr.isEmpty()) {
                tieneSucursal = tieneSucursalStr.toLowerCase().startsWith("s");
            }
            
            String sucursal = gerenteExistente.getSucursal();
            if (tieneSucursal) {
                String sucursalActual = sucursal != null ? sucursal : "";
                String nuevaSucursal = leerTexto("➤ Sucursal [" + sucursalActual + "]: ");
                if (!nuevaSucursal.isEmpty()) {
                    sucursal = nuevaSucursal;
                } else if (sucursal == null || sucursal.isEmpty()) {
                    sucursal = leerTexto("➤ Debe ingresar el nombre de la sucursal: ");
                }
            } else {
                sucursal = null;
            }
            
            if (apellidoMaterno != null && !apellidoMaterno.trim().isEmpty()) {
                return new Gerente(nombre, apellidoPaterno, apellidoMaterno, 
                                 fechaNacimiento, curp, gerenteExistente.getNumeroGerente(), 
                                 tieneSucursal, sucursal);
            } else {
                return new Gerente(nombre, apellidoPaterno, 
                                 fechaNacimiento, curp, gerenteExistente.getNumeroGerente(), 
                                 tieneSucursal, sucursal);
            }
                             
        } catch (Exception e) {
            System.out.println("❌ Error al solicitar datos de edición: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public void mostrarDetalles(Gerente gerente) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║           DETALLES DEL GERENTE       ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ Número: " + String.format("%-30s", gerente.getNumeroGerente()) + "║");
        System.out.println("║ ID: " + String.format("%-32s", getId(gerente)) + "║");
        System.out.println("║ Nombre: " + String.format("%-30s", gerente.getNombreCompleto()) + "║");
        System.out.println("║ CURP: " + String.format("%-32s", gerente.getCurp()) + "║");
        System.out.println("║ Fecha Nac.: " + String.format("%-25s", formatoFecha.format(gerente.getFechaNacimiento())) + "║");
        System.out.println("║ Tiene Sucursal: " + String.format("%-23s", gerente.isTieneSucursal() ? "Sí" : "No") + "║");
        if (gerente.isTieneSucursal() && gerente.getSucursal() != null) {
            System.out.println("║ Sucursal: " + String.format("%-28s", gerente.getSucursal()) + "║");
        }
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    public void mostrarLista() {
        if (elementos.isEmpty()) {
            System.out.println("❌ No hay gerentes registrados.");
            return;
        }
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                           LISTA DE GERENTES                       ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ Número │                Nombre                │      Sucursal      ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════╣");
        
        for (Gerente gerente : elementos) {
            String sucursal = gerente.isTieneSucursal() && gerente.getSucursal() != null ? 
                gerente.getSucursal() : "Sin sucursal";
            System.out.printf("║ %-6d │ %-33s │ %-18s ║%n", 
                            gerente.getNumeroGerente(),
                            gerente.getNombreCompleto(),
                            sucursal);
        }
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");
    }
    
    @Override
    protected void mostrarEncabezadoAlta() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║           ALTA DE GERENTE            ║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    protected void mostrarEncabezadoBaja() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║           BAJA DE GERENTE            ║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    protected void mostrarEncabezadoEdicion() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         EDICIÓN DE GERENTE           ║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    protected Object solicitarIdParaBaja() {
        return leerEntero("➤ Ingrese el número del gerente a eliminar: ");
    }
    
    @Override
    protected Object solicitarIdParaEdicion() {
        return leerEntero("➤ Ingrese el número del gerente a editar: ");
    }
}