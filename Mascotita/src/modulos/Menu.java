package modulos;

import java.util.Scanner;

/**
 * Clase Menu principal del sistema de mascotas
 * Proporciona acceso a todos los módulos del sistema
 */
public class Menu {
    private Scanner scanner;
    
    public Menu() {
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Muestra el menu principal del sistema
     */
    public void mostrarMenuPrincipal() {
        int opcion;
        
        do {
            System.out.println("\n=== SISTEMA MASCOTITAS ===");
            System.out.println("1. Alta / Baja / Edición de Cliente o Mascota");
            System.out.println("2. Alta / Baja / Edición de Personal");
            System.out.println("3. Administración de Citas");
            System.out.println("4. Administración de Productos y Servicios");
            System.out.println("5. Administración de Adopción de Mascotas");
            System.out.println("6. Administración de Sucursales");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consume el salto de línea
            
            switch (opcion) {
                case 1:
                    menuClientesMascotas();
                    break;
                case 2:
                    menuPersonal();
                    break;
                case 3:
                    menuCitas();
                    break;
                case 4:
                    menuProductosServicios();
                    break;
                case 5:
                    menuAdopciones();
                    break;
                case 6:
                    menuSucursales();
                    break;
                case 0:
                    System.out.println("Gracias por usar el sistema Mascotitas. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }
    
    /**
     * Menu para gestión de clientes y mascotas
     */
    private void menuClientesMascotas() {
        int opcion;
        
        do {
            System.out.println("\n=== GESTIÓN DE CLIENTES Y MASCOTAS ===");
            System.out.println("1. Gestión de Clientes");
            System.out.println("2. Gestión de Mascotas");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consume el salto de línea
            
            switch (opcion) {
                case 1:
                    submenuABE("Cliente");
                    // TODO: Implementar ModuloCliente con métodos alta(), baja(), edicion()
                    break;
                case 2:
                    submenuABE("Mascota");
                    // TODO: Implementar ModuloMascota con métodos alta(), baja(), edicion()
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }
    
    /**
     * Menu para gestión de personal
     */
    private void menuPersonal() {
        int opcion;
        
        do {
            System.out.println("\n=== GESTIÓN DE PERSONAL ===");
            System.out.println("1. Gestión de Veterinarios");
            System.out.println("2. Gestión de Asistentes");
            System.out.println("3. Gestión de Gerentes");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consume el salto de línea
            
            switch (opcion) {
                case 1:
                    submenuABE("Veterinario");
                    // TODO: Implementar ModuloVeterinario con métodos alta(), baja(), edicion() y ordenamiento A-Z/Z-A
                    break;
                case 2:
                    submenuABE("Asistente Personal");
                    // TODO: Implementar ModuloAsistente con métodos alta(), baja(), edicion()
                    break;
                case 3:
                    submenuABE("Gerente");
                    // TODO: Implementar ModuloGerente con métodos alta(), baja(), edicion()
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }
    
    /**
     * Menu para administración de citas
     */
    private void menuCitas() {
        int opcion;
        
        do {
            System.out.println("\n=== ADMINISTRACIÓN DE CITAS ===");
            System.out.println("1. Registrar cita de veterinarios a domicilio");
            System.out.println("2. Consultar citas agendadas");
            System.out.println("3. Registro de citas pasadas");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consume el salto de línea
            
            switch (opcion) {
                case 1:
                    // TODO: Implementar ModuloCitas.registrarCita() con verificaciones de interface RevisionDeCitas
                    System.out.println("Registrando nueva cita...");
                    break;
                case 2:
                    // TODO: Implementar ModuloCitas.consultarCitasAgendadas() ordenadas por fecha más cercana
                    System.out.println("Consultando citas agendadas...");
                    break;
                case 3:
                    menuCitasPasadas();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }
    
    /**
     * Submenu para citas pasadas con opciones de ordenamiento
     */
    private void menuCitasPasadas() {
        // TODO: Implementar ModuloCitas.consultarCitasPasadas() con opciones de ordenamiento y descarga de archivos
        System.out.println("Función de citas pasadas pendiente de implementar");
    }
    
    /**
     * Menu para administración de productos y servicios
     */
    private void menuProductosServicios() {
        int opcion;
        
        do {
            System.out.println("\n=== ADMINISTRACIÓN DE PRODUCTOS Y SERVICIOS ===");
            System.out.println("1. Pago de productos y/o servicios");
            System.out.println("2. Gestión de productos");
            System.out.println("3. Gestión de servicios");
            System.out.println("4. Consultar productos disponibles");
            System.out.println("5. Consultar servicios disponibles");
            System.out.println("6. Registro de ventas pasadas");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consume el salto de línea
            
            switch (opcion) {
                case 1:
                    // TODO: Implementar ModuloPagos con generación de tarjeta (algoritmo de Luhn)
                    System.out.println("Procesando pago...");
                    break;
                case 2:
                    submenuABE("Producto");
                    // TODO: Implementar ModuloProductos con métodos alta(), baja(), edicion()
                    break;
                case 3:
                    submenuABE("Servicio");
                    // TODO: Implementar ModuloServicios con métodos alta(), baja(), edicion()
                    break;
                case 4:
                    // TODO: Implementar consulta de productos con ordenamiento por ID y nombre
                    System.out.println("Consultando productos...");
                    break;
                case 5:
                    // TODO: Implementar consulta de servicios predeterminados
                    System.out.println("Consultando servicios...");
                    break;
                case 6:
                    // TODO: Implementar registro de ventas con ordenamiento A-Z/Z-A
                    System.out.println("Consultando ventas pasadas...");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }
    
    /**
     * Menu para administración de adopciones
     */
    private void menuAdopciones() {
        int opcion;
        
        do {
            System.out.println("\n=== ADMINISTRACIÓN DE ADOPCIONES ===");
            System.out.println("1. Mascotas disponibles para adopción");
            System.out.println("2. Registro de adopciones realizadas");
            System.out.println("3. Consultar mascotas devueltas");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consume el salto de línea
            
            switch (opcion) {
                case 1:
                    // TODO: Implementar ModuloAdopciones.mostrarMascotasDisponibles() con verificación de vacunas
                    System.out.println("Mostrando mascotas disponibles...");
                    break;
                case 2:
                    // TODO: Implementar consulta de adopciones con ordenamiento por cliente y mascota
                    System.out.println("Consultando adopciones realizadas...");
                    break;
                case 3:
                    // TODO: Implementar consulta de mascotas devueltas con cargo por maltrato
                    System.out.println("Consultando mascotas devueltas...");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }
    
    /**
     * Menu para administración de sucursales
     */
    private void menuSucursales() {
        int opcion;
        
        do {
            System.out.println("\n=== ADMINISTRACIÓN DE SUCURSALES ===");
            System.out.println("1. Gestión de sucursales");
            System.out.println("2. Consultar sucursales");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consume el salto de línea
            
            switch (opcion) {
                case 1:
                    submenuABE("Sucursal");
                    // TODO: Implementar ModuloSucursales con métodos alta(), baja(), edicion()
                    break;
                case 2:
                    // TODO: Implementar consulta de sucursales
                    System.out.println("Consultando sucursales...");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }
    
    /**
     * Submenu genérico para operaciones Alta/Baja/Edición
     */
    private void submenuABE(String entidad) {
        int opcion;
        
        do {
            System.out.println("\n=== GESTIÓN DE " + entidad.toUpperCase() + " ===");
            System.out.println("1. Alta de " + entidad);
            System.out.println("2. Baja de " + entidad);
            System.out.println("3. Edición de " + entidad);
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consume el salto de línea
            
            switch (opcion) {
                case 1:
                    System.out.println("Registrando nuevo " + entidad.toLowerCase() + "...");
                    break;
                case 2:
                    System.out.println("Eliminando " + entidad.toLowerCase() + "...");
                    break;
                case 3:
                    System.out.println("Editando " + entidad.toLowerCase() + "...");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }
    
    /**
     * Cierra los recursos del scanner
     */
    public void cerrarRecursos() {
        scanner.close();
    }
}