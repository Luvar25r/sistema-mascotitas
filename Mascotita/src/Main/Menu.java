package Main;

import java.util.Scanner;
public class Menu {
    public static void main(String[] args) {
        MenuManager menu = new MenuManager();
        menu.iniciar();
    }
}

class MenuManager {
    private Scanner scanner;

    public MenuManager() {
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            mostrarMenuPrincipal();
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    menuGestionClientesMascotas();
                    break;
                case 2:
                    menuGestionPersonal();
                    break;
                case 3:
                    menuAdministracionCitas();
                    break;
                case 4:
                    menuProductosServicios();
                    break;
                case 5:
                    menuAdopcionMascotas();
                    break;
                case 6:
                    menuAdministracionSucursales();
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente nuevamente.");
            }
        }
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n=== SISTEMA MASCOTITAS ===");
        System.out.println("1. Gestión de Clientes y Mascotas");
        System.out.println("2. Gestión de Personal");
        System.out.println("3. Administración de Citas");
        System.out.println("4. Administración de Productos y Servicios");
        System.out.println("5. Administración de Adopción de Mascotas");
        System.out.println("6. Administración de Sucursales");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private void menuGestionClientesMascotas() {
        System.out.println("\n=== GESTIÓN DE CLIENTES Y MASCOTAS ===");
        System.out.println("1. Alta de Cliente");
        System.out.println("2. Baja de Cliente");
        System.out.println("3. Edición de Cliente");
        System.out.println("4. Alta de Mascota");
        System.out.println("5. Baja de Mascota");
        System.out.println("6. Edición de Mascota");
        System.out.println("0. Volver al menú principal");
    }

    private void menuGestionPersonal() {
        System.out.println("\n=== GESTIÓN DE PERSONAL ===");
        System.out.println("1. Gestión de Veterinarios");
        System.out.println("2. Gestión de Asistentes");
        System.out.println("3. Gestión de Gerentes");
        System.out.println("0. Volver al menú principal");
    }

    private void menuAdministracionCitas() {
        System.out.println("\n=== ADMINISTRACIÓN DE CITAS ===");
        System.out.println("1. Registrar cita a domicilio");
        System.out.println("2. Consultar citas agendadas");
        System.out.println("3. Ver registro de citas pasadas");
        System.out.println("0. Volver al menú principal");
    }

    private void menuProductosServicios() {
        System.out.println("\n=== ADMINISTRACIÓN DE PRODUCTOS Y SERVICIOS ===");
        System.out.println("1. Pago de productos/servicios");
        System.out.println("2. Gestión de productos");
        System.out.println("3. Gestión de servicios");
        System.out.println("4. Consultar productos disponibles");
        System.out.println("5. Consultar servicios disponibles");
        System.out.println("6. Ver registro de ventas");
        System.out.println("0. Volver al menú principal");
    }

    private void menuAdopcionMascotas() {
        System.out.println("\n=== ADMINISTRACIÓN DE ADOPCIÓN DE MASCOTAS ===");
        System.out.println("1. Ver mascotas disponibles para adopción");
        System.out.println("2. Consultar registro de adopciones");
        System.out.println("3. Consultar mascotas devueltas");
        System.out.println("0. Volver al menú principal");
    }

    private void menuAdministracionSucursales() {
        System.out.println("\n=== ADMINISTRACIÓN DE SUCURSALES ===");
        System.out.println("1. Alta de sucursal");
        System.out.println("2. Baja de sucursal");
        System.out.println("3. Edición de sucursal");
        System.out.println("4. Consulta de sucursales");
        System.out.println("0. Volver al menú principal");
    }
}