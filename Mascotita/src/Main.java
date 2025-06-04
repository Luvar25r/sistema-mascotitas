import modulos.Menu;

/**
 * Clase principal del sistema Mascotitas
 * Hecho por:
 * *Alberto Vazquez Enriquez
 * *Luis Eduardo Vadillo Rojas
 * *Bryan Ricardo Laurrabaquio Ramirez
 * *Diego Rafael Huerta Ruiz
 * "El codigo que se realiza es de autoria propia y no es tomado de algun otro lugar o hecho por otra persona"
 */
public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        
        try {
            menu.mostrarMenuPrincipal();
        } finally {
            menu.cerrarRecursos();
        }
    }
}