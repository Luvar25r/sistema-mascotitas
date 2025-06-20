package modulos;

import modelo.Mascota;
import modelo.personas.Cliente;
import utils.*;
import modelo.Sucursal;
import modelo.Zona;
import java.util.Scanner;
import java.util.List;
import java.util.Arrays;


/**
 * Clase Menu principal del sistema de mascotas
 * Proporciona acceso a todos los módulos del sistema
 */
public class Menu {
    private Scanner scanner;
    private ClienteCRUD clienteCRUD;
    private MascotaCRUD mascotaCRUD;
    private VeterinarioCRUD veterinarioCRUD;
    private GerenteCRUD gerenteCRUD;
    private AsistenteCRUD asistenteCRUD;
    private AdopcionesCRUD adopcionCRUD;
    private ProductoCRUD productoCRUD;
    private ServicioCRUD servicioCRUD;
    private CitaCRUD citasCRUD;


    public Menu() {
        this.scanner = new Scanner(System.in);
        this.clienteCRUD = new ClienteCRUD();
        this.mascotaCRUD = new MascotaCRUD();
        this.veterinarioCRUD = new VeterinarioCRUD();
        this.gerenteCRUD = new GerenteCRUD();
        this.asistenteCRUD = new AsistenteCRUD();
        this.productoCRUD = new ProductoCRUD();
        this.servicioCRUD = new ServicioCRUD();
        this.adopcionCRUD = new AdopcionesCRUD(clienteCRUD, mascotaCRUD);
        this.citasCRUD = new CitaCRUD(clienteCRUD, mascotaCRUD,
                veterinarioCRUD,  asistenteCRUD,
                servicioCRUD);
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
            System.out.println("5. Administración de Sucursales");
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
                /*case 5:
                    menuAdopciones();
                    break;

                 */
                case 5:
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
                    submenuClientes();
                    break;
                case 2:
                    submenuMascotas();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }
    
    /**
     * Submenu específico para gestión de clientes
     */
    private void submenuClientes() {
        int opcion;
        
        do {
            System.out.println("\n=== GESTIÓN DE CLIENTES ===");
            System.out.println("1. Alta de Cliente");
            System.out.println("2. Baja de Cliente");
            System.out.println("3. Edición de Cliente");
            System.out.println("4. Consultar Cliente");
            System.out.println("5. Listar todos los Clientes");
            System.out.println("6. Cargar ejemplos de clientes ");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consume el salto de línea
            
            switch (opcion) {
                case 1:
                    clienteCRUD.alta();
                    break;
                case 2:
                    clienteCRUD.baja();
                    break;
                case 3:
                    clienteCRUD.edicion();
                    break;
                case 4:
                    clienteCRUD.consulta();
                    break;
                case 5:
                    clienteCRUD.mostrarLista();
                    esperarTecla();
                    break;

                case 6:
                    // ← NUEVA FUNCIONALIDAD
                    clienteCRUD.menuCargarEjemplos();
                    esperarTecla();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }
    private void submenuMascotas() {
        int opcion;

        do {
            System.out.println("\n=== GESTIÓN DE MASCOTAS ===");
            System.out.println("1. Alta de Mascota");
            System.out.println("2. Baja de Mascota");
            System.out.println("3. Edición de Mascota");
            System.out.println("4. Consultar Mascotas");
            System.out.println("5. Listar todos los Mascotas");
            System.out.println("6. Cargar datos de ejemplo");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Consume el salto de línea

            switch (opcion) {
                case 1:
                    mascotaCRUD.alta();
                    break;
                case 2:
                    mascotaCRUD.baja();
                    break;
                case 3:
                    mascotaCRUD.edicion();
                    break;
                case 4:
                    mascotaCRUD.consulta();
                    break;
                case 5:
                    mascotaCRUD.mostrarLista();
                    esperarTecla();
                    break;
                case 6:
                    // ← NUEVA FUNCIONALIDAD
                    mascotaCRUD.cargarDatosDeEjemplo();
                    esperarTecla();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }//SubmenuMascotas
    private void submenuVeterinarios() {
        int opcion;

        do {
            System.out.println("\n=== GESTIÓN DE VETERINARIOS ===");
            System.out.println("1. Alta de Veterinario");
            System.out.println("2. Baja de Veterinario");
            System.out.println("3. Edición de Veterinario");
            System.out.println("4. Consultar Veterinario");
            System.out.println("5. Listar todos los Veterinarios");
            System.out.println("6. Cargar datos de ejemplo");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Consume el salto de línea

            switch (opcion) {
                case 1:
                    veterinarioCRUD.alta();
                    break;
                case 2:
                    veterinarioCRUD.baja();
                    break;
                case 3:
                    veterinarioCRUD.edicion();
                    break;
                case 4:
                    veterinarioCRUD.consulta();
                    break;
                case 5:
                    veterinarioCRUD.mostrarLista();
                    esperarTecla();
                    break;
                case 6:
                    veterinarioCRUD.cargarDatosDeEjemplo();
                    esperarTecla();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }//SubmenuVeterinario
    private void submenuGerente() {
        int opcion;

        do {
            System.out.println("\n=== GESTIÓN DE GERENTE ===");
            System.out.println("1. Alta de Gerente ");
            System.out.println("2. Baja de Gerente");
            System.out.println("3. Edición de Gerente");
            System.out.println("4. Consultar Gerente");
            System.out.println("5. Listar todos los Gerentes");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Consume el salto de línea

            switch (opcion) {
                case 1:
                    gerenteCRUD.alta();
                    break;
                case 2:
                    gerenteCRUD.baja();
                    break;
                case 3:
                    gerenteCRUD.edicion();
                    break;
                case 4:
                    gerenteCRUD.consulta();
                    break;
                case 5:
                    gerenteCRUD.mostrarLista();
                    esperarTecla();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }//SubmenuGerente
    private void submenuAsistente() {
        int opcion;

        do {
            System.out.println("\n=== GESTIÓN DE ASISTENTE ===");
            System.out.println("1. Alta de Asistente");
            System.out.println("2. Baja de Asisente");
            System.out.println("3. Edición de Asistente");
            System.out.println("4. Consultar Asistente");
            System.out.println("5. Listar todos los Asisentes");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Consume el salto de línea

            switch (opcion) {
                case 1:
                    asistenteCRUD.alta();
                    break;
                case 2:
                    asistenteCRUD.baja();
                    break;
                case 3:
                    asistenteCRUD.edicion();
                    break;
                case 4:
                    asistenteCRUD.consulta();
                    break;
                case 5:
                    asistenteCRUD.mostrarLista();
                    esperarTecla();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }//SubmenuGerente
    private void submenuProducto() {
        int opcion;

        do {
            System.out.println("\n=== GESTIÓN DE PRODUCTO ===");
            System.out.println("1. Alta de Producto ");
            System.out.println("2. Baja de Producto");
            System.out.println("3. Edición de Producto");
            System.out.println("4. Consultar Producto");
            System.out.println("5. Listar todos los Productos");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Consume el salto de línea

            switch (opcion) {
                case 1:
                    productoCRUD.alta();
                    break;
                case 2:
                    productoCRUD.baja();
                    break;
                case 3:
                    productoCRUD.edicion();
                    break;
                case 4:
                    productoCRUD.consulta();
                    break;
                case 5:
                    productoCRUD.mostrarLista();
                    esperarTecla();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }//SubmenuProducto
    private void submenuServicio() {
        int opcion;

        do {
            System.out.println("\n=== GESTIÓN DE SERVICIOS ===");
            System.out.println("1. Alta de Servicio");
            System.out.println("2. Baja de Servicio");
            System.out.println("3. Edición de Servicio");
            System.out.println("4. Consultar Servicio");
            System.out.println("5. Listar todos los Servicios");
            System.out.println("6. Cargar datos de ejemplo");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Consume el salto de línea

            switch (opcion) {
                case 1:
                    servicioCRUD.alta();
                    break;
                case 2:
                    servicioCRUD.baja();
                    break;
                case 3:
                    servicioCRUD.edicion();
                    break;
                case 4:
                    servicioCRUD.consulta();
                    break;
                case 5:
                    servicioCRUD.mostrarLista();
                    esperarTecla();
                    break;
                case 6:
                    servicioCRUD.cargarDatosEjemplo();
                    esperarTecla();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }// SubmenuServicio
    private void submenuAdopciones() {//SubmenuAdopciones

        int opcion;

        do {
            System.out.println("\n=== GESTIÓN DE ADOPCIONES ===");
            System.out.println("1. Alta de Adopciones");
            System.out.println("2. Baja de Adopciones");
            System.out.println("3. Edición de Adopciones");
            System.out.println("4. Consultar Adopciones");
            System.out.println("5. Listar todas las Adopciones");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Consume el salto de línea

            switch (opcion) {
                case 1:
                    adopcionCRUD.alta();
                    break;
                case 2:
                    adopcionCRUD.baja();
                    break;
                case 3:
                    adopcionCRUD.edicion();
                    break;
                case 4:
                    adopcionCRUD.consulta();
                    break;
                case 5:
                    adopcionCRUD.mostrarLista();
                    esperarTecla();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }// SubmenuServicio
    private void submenuCitas() {//SubmenuCitas

        int opcion;

        do {
            System.out.println("\n=== GESTIÓN DE CITAS ===");
            System.out.println("1. Alta de Citas");
            System.out.println("2. Baja de Citas");
            System.out.println("3. Edición de Citas");
            System.out.println("4. Consultar Citas");
            System.out.println("5. Listar todas las Citas");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Consume el salto de línea

            switch (opcion) {
                case 1:
                    citasCRUD.alta();
                    break;
                case 2:
                    citasCRUD.baja();
                    break;
                case 3:
                    citasCRUD.edicion();
                    break;
                case 4:
                    citasCRUD.consulta();
                    break;
                case 5:
                    citasCRUD.mostrarLista();
                    esperarTecla();
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
                    submenuVeterinarios();
                    break;
                case 2:
                    submenuAsistente();
                    break;
                case 3:
                    submenuGerente();

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
                    submenuCitas();

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
            System.out.println("1. Gestión de productos");
            System.out.println("2. Gestión de servicios");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consume el salto de línea
            
            switch (opcion) {

                case 1:
                    submenuProducto();

                    break;
                case 2:
                    submenuServicio();
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
            System.out.println("1. Realizar una Adopción");
            System.out.println("2. Devolver una mascota");
            System.out.println("3. Mascotas disponibles para adopción");
            System.out.println("4. Registro de adopciones realizadas");
            System.out.println("5. Consultar mascotas devueltas");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consume el salto de línea
            
            switch (opcion) {
                case 1:
                    submenuAdopciones();
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
        SucursalManager sucursalManager = new SucursalManager();

        do {
            System.out.println("\n=== ADMINISTRACIÓN DE SUCURSALES ===");
            System.out.println("1. Consultar todas las sucursales");
            System.out.println("2. Buscar sucursal por código");
            System.out.println("3. Buscar sucursales por zona");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Consume el salto de línea

            switch (opcion) {
                case 1:
                    sucursalManager.mostrarLista();
                    esperarTecla();
                    break;
                case 2:
                    System.out.print("Ingrese el código de la sucursal: ");
                    String codigo = scanner.nextLine();
                    Sucursal sucursalPorCodigo = sucursalManager.buscarPorCodigo(codigo);
                    if (sucursalPorCodigo != null) {
                        sucursalManager.mostrarDetalles(sucursalPorCodigo);
                    } else {
                        System.out.println("❌ No se encontró ninguna sucursal con el código: " + codigo);
                    }
                    esperarTecla();
                    break;
                case 3:
                    System.out.println("Zonas disponibles:");
                    Zona[] zonas = Zona.values();
                    for (int i = 0; i < zonas.length; i++) {
                        System.out.println((i + 1) + ". " + zonas[i].getDescripcion());
                    }
                    System.out.print("Seleccione una zona (1-" + zonas.length + "): ");
                    int seleccion = scanner.nextInt();
                    scanner.nextLine(); // Consume el salto de línea

                    if (seleccion >= 1 && seleccion <= zonas.length) {
                        Zona zonaSeleccionada = zonas[seleccion - 1];
                        List<Sucursal> sucursalesPorZona = sucursalManager.buscarPorZona(zonaSeleccionada);
                        System.out.println("\nSucursales en " + zonaSeleccionada.getDescripcion() + ":");
                        for (Sucursal sucursal : sucursalesPorZona) {
                            System.out.println(" - " + sucursal.getNombre() + " (" + sucursal.getCodigoSucursal() + ")");
                        }
                    } else {
                        System.out.println("❌ Opción no válida.");
                    }
                    esperarTecla();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("❌ Opción no válida.");
            }
        } while (opcion != 0);
    }
    
    /**
     * Método para esperar que el usuario presione una tecla
     */
    private void esperarTecla() {
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
    
    /**
     * Cierra los recursos del scanner
     */
    public void cerrarRecursos() {
        scanner.close();
    }
}