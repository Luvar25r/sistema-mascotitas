package utils;

import modelo.Mascota;
import java.util.List;
import java.util.Optional;

public class MascotaCRUD extends OperacionesCRUD<Mascota> {
    
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
        System.out.println("║          CONSULTA DE MASCOTA         ║");
        System.out.println("╚══════════════════════════════════════╝");
        
        if (elementos.isEmpty()) {
            System.out.println("❌ No hay mascotas registradas en el sistema.");
            return;
        }
        
        System.out.println("\n¿Cómo desea buscar la mascota?");
        System.out.println("1. Por ID de mascota");
        System.out.println("2. Por nombre");
        System.out.println("3. Mostrar todas las mascotas");
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
        String idMascota = leerTexto("➤ Ingrese el ID de la mascota: ");
        
        Optional<Mascota> mascotaEncontrada = buscarPorId(idMascota);
        
        if (mascotaEncontrada.isPresent()) {
            System.out.println("\n✅ Mascota encontrada:");
            mostrarDetalles(mascotaEncontrada.get());
        } else {
            System.out.println("❌ No se encontró una mascota con el ID: " + idMascota);
        }
    }
    
    private void consultarPorCriterio() {
        String criterio = leerTexto("➤ Ingrese el nombre a buscar: ");
        
        if (criterio.trim().isEmpty()) {
            System.out.println("❌ Debe ingresar un criterio de búsqueda.");
            return;
        }
        
        List<Mascota> mascotasEncontradas = buscarPorCriterio(criterio);
        
        if (mascotasEncontradas.isEmpty()) {
            System.out.println("❌ No se encontraron mascotas que coincidan con: " + criterio);
        } else {
            System.out.println("\n✅ Se encontraron " + mascotasEncontradas.size() + " mascota(s):");
            mostrarLista();
        }
    }
    
    @Override
    protected String getId(Mascota mascota) {
        return mascota.getId();
    }
    
    @Override
    protected boolean validarDatos(Mascota mascota) {
        return mascota != null 
            && mascota.getId() != null && !mascota.getId().trim().isEmpty()
            && mascota.getNombre() != null && !mascota.getNombre().trim().isEmpty();
    }
    
    @Override
    protected void notificarCambio(String tipoOperacion, Mascota mascota) {
        System.out.println("══════════════════════════════════════");
        System.out.println("✓ " + tipoOperacion + " DE MASCOTA EXITOSA");
        System.out.println("Mascota: " + mascota.getNombre());
        System.out.println("ID: " + getId(mascota));
        System.out.println("Vacunada: " + (mascota.isVacunada() ? "Sí" : "No"));
        System.out.println("══════════════════════════════════════");
    }
    
    @Override
    protected Object obtenerIdElemento(Mascota mascota) {
        return mascota.getId();
    }
    
    @Override
    protected boolean coincideConCriterio(Mascota mascota, String criterio) {
        String criterioLower = criterio.toLowerCase();
        return mascota.getNombre().toLowerCase().contains(criterioLower)
            || mascota.getId().toLowerCase().contains(criterioLower);
    }
    
    @Override
    public Mascota solicitarDatosAlta() {
        try {
            String id = leerTexto("➤ Ingrese el ID de la mascota: ");
            if (id.isEmpty()) {
                System.out.println("❌ El ID es obligatorio.");
                return null;
            }
            
            String nombre = leerTexto("➤ Ingrese el nombre de la mascota: ");
            if (nombre.isEmpty()) {
                System.out.println("❌ El nombre es obligatorio.");
                return null;
            }
            
            Mascota nuevaMascota = new Mascota(id, nombre);
            
            String agregarVacunas = leerTexto("➤ ¿Desea agregar vacunas? (s/n): ");
            if (agregarVacunas.toLowerCase().startsWith("s")) {
                String vacunasStr = leerTexto("➤ Ingrese las vacunas separadas por comas: ");
                if (!vacunasStr.isEmpty()) {
                    String[] vacunas = vacunasStr.split(",");
                    for (String vacuna : vacunas) {
                        nuevaMascota.agregarVacuna(vacuna.trim());
                    }
                }
            }
            
            return nuevaMascota;
                             
        } catch (Exception e) {
            System.out.println("❌ Error al solicitar datos: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public Mascota solicitarDatosEdicion(Mascota mascotaExistente) {
        try {
            System.out.println("\n📝 Datos actuales de la mascota:");
            mostrarDetalles(mascotaExistente);
            System.out.println("\n➤ Ingrese los nuevos datos (presione Enter para mantener el valor actual):");
            
            String nombre = leerTexto("➤ Nombre [" + mascotaExistente.getNombre() + "]: ");
            if (nombre.isEmpty()) nombre = mascotaExistente.getNombre();
            
            Mascota mascotaEditada = new Mascota(mascotaExistente.getId(), nombre);
            
            for (String vacuna : mascotaExistente.getVacunas()) {
                mascotaEditada.agregarVacuna(vacuna);
            }
            
            String editarVacunas = leerTexto("➤ ¿Desea editar las vacunas? (s/n): ");
            if (editarVacunas.toLowerCase().startsWith("s")) {
                String vacunasStr = leerTexto("➤ Ingrese TODAS las vacunas separadas por comas: ");
                if (!vacunasStr.isEmpty()) {
                    mascotaEditada = new Mascota(mascotaExistente.getId(), nombre);
                    String[] vacunas = vacunasStr.split(",");
                    for (String vacuna : vacunas) {
                        mascotaEditada.agregarVacuna(vacuna.trim());
                    }
                }
            }
            
            return mascotaEditada;
                             
        } catch (Exception e) {
            System.out.println("❌ Error al solicitar datos de edición: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public void mostrarDetalles(Mascota mascota) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║          DETALLES DE MASCOTA         ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ ID: " + String.format("%-34s", getId(mascota)) + "║");
        System.out.println("║ Nombre: " + String.format("%-30s", mascota.getNombre()) + "║");
        System.out.println("║ Vacunada: " + String.format("%-28s", mascota.isVacunada() ? "Sí" : "No") + "║");
        System.out.println("║ Vacunas: " + String.format("%-29s", mascota.getVacunas().toString()) + "║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    public void mostrarLista() {
        if (elementos.isEmpty()) {
            System.out.println("❌ No hay mascotas registradas.");
            return;
        }
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                        LISTA DE MASCOTAS                      ║");
        System.out.println("╠════════════════════════════════════════════════════════════════╣");
        System.out.println("║    ID    │         Nombre         │ Vacunada │    Vacunas    ║");
        System.out.println("╠════════════════════════════════════════════════════════════════╣");
        
        for (Mascota mascota : elementos) {
            String vacunas = mascota.getVacunas().size() > 0 ? 
                String.valueOf(mascota.getVacunas().size()) : "0";
            System.out.printf("║ %-8s │ %-22s │ %-8s │ %-13s ║%n", 
                            mascota.getId(),
                            mascota.getNombre(),
                            mascota.isVacunada() ? "Sí" : "No",
                            vacunas + " vacunas");
        }
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
    
    @Override
    protected void mostrarEncabezadoAlta() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║           ALTA DE MASCOTA            ║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    protected void mostrarEncabezadoBaja() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║           BAJA DE MASCOTA            ║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    protected void mostrarEncabezadoEdicion() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         EDICIÓN DE MASCOTA           ║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    protected Object solicitarIdParaBaja() {
        return leerTexto("➤ Ingrese el ID de la mascota a eliminar: ");
    }
    
    @Override
    protected Object solicitarIdParaEdicion() {
        return leerTexto("➤ Ingrese el ID de la mascota a editar: ");
    }
}