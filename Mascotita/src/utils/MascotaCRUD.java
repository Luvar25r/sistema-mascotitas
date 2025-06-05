package utils;

import modelo.Mascota;

import java.util.Optional;
import java.util.Scanner;

public class MascotaCRUD extends OperacionesCRUD<Mascota> {
    private final Scanner scanner;

    public MascotaCRUD() {
        super();
        this.scanner = new Scanner(System.in);
    }


    @Override
    public void alta() {
        System.out.println("Registrando nueva mascota...");
        Mascota nuevaMascota = solicitarDatosAlta();
        if (nuevaMascota != null && elementos.add(nuevaMascota)) {
            System.out.println("✅ Mascota registrada exitosamente");
        } else {
            System.out.println("❌ No se pudo registrar la mascota");
        }
    }

    @Override
    public void baja() {
        System.out.println("Eliminando mascota...");

        if (elementos.isEmpty()) {
            System.out.println("❌ No hay mascotas registradas");
            return;
        }

        mostrarLista();
        System.out.print("Ingrese el ID de la mascota a eliminar: ");
        String id = scanner.nextLine().trim();

        Optional<Mascota> mascota = buscarPorId(id);
        if (mascota.isPresent()) {
            if (elementos.remove(mascota.get())) {
                System.out.println("✅ Mascota eliminada exitosamente");
            } else {
                System.out.println("❌ Error al eliminar la mascota");
            }
        } else {
            System.out.println("❌ No se encontró la mascota con el ID especificado");
        }
    }

    @Override
    public void edicion() {
        System.out.println("Editando mascota...");

        if (elementos.isEmpty()) {
            System.out.println("❌ No hay mascotas registradas");
            return;
        }

        mostrarLista();
        System.out.print("Ingrese el ID de la mascota a editar: ");
        String id = scanner.nextLine().trim();

        Optional<Mascota> mascotaExistente = buscarPorId(id);
        if (mascotaExistente.isPresent()) {
            Mascota editada = solicitarDatosEdicion(mascotaExistente.get());
            if (editada != null) {
                elementos.remove(mascotaExistente.get());
                elementos.add(editada);
                System.out.println("✅ Mascota actualizada exitosamente");
            }
        } else {
            System.out.println("❌ No se encontró la mascota con el ID especificado");
        }
    }

    public void consulta() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         CONSULTA DE MASCOTAS         ║");
        System.out.println("╚══════════════════════════════════════╝");

        if (elementos.isEmpty()) {
            System.out.println("❌ No hay mascotas registradas");
            return;
        }

        mostrarLista();
    }

    @Override
    public Mascota solicitarDatosAlta() {
        try {
            System.out.print("Ingrese el ID de la mascota: ");
            String id = scanner.nextLine().trim();

            if (id.isEmpty()) {
                System.out.println("❌ El ID es obligatorio");
                return null;
            }

            if (buscarPorId(id).isPresent()) {
                System.out.println("❌ Ya existe una mascota con ese ID");
                return null;
            }

            System.out.print("Ingrese el nombre de la mascota: ");
            String nombre = scanner.nextLine().trim();

            System.out.print("Ingrese la raza de la mascota: ");
            String raza = scanner.nextLine().trim();

            Mascota nuevaMascota = new Mascota(id, nombre);
            nuevaMascota.setRaza(raza);

            System.out.print("¿Desea agregar vacunas? (s/n): ");
            if (scanner.nextLine().trim().toLowerCase().startsWith("s")) {
                System.out.print("Ingrese las vacunas separadas por comas: ");
                String[] vacunas = scanner.nextLine().split(",");
                for (String vacuna : vacunas) {
                    if (!vacuna.trim().isEmpty()) {
                        nuevaMascota.agregarVacuna(vacuna.trim());
                    }
                }
            }

            return nuevaMascota;

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Mascota solicitarDatosEdicion(Mascota mascotaExistente) {
        try {
            System.out.println("\nDatos actuales de la mascota:");
            mostrarDetalles(mascotaExistente);

            System.out.print("Nuevo nombre (Enter para mantener '" + mascotaExistente.getNombre() + "'): ");
            String nuevoNombre = scanner.nextLine().trim();
            if (nuevoNombre.isEmpty()) nuevoNombre = mascotaExistente.getNombre();

            System.out.print("Nueva raza (Enter para mantener '" + mascotaExistente.getRaza() + "'): ");
            String nuevaRaza = scanner.nextLine().trim();
            if (nuevaRaza.isEmpty()) nuevaRaza = mascotaExistente.getRaza();

            Mascota mascotaEditada = new Mascota(mascotaExistente.getId(), nuevoNombre);
            mascotaEditada.setRaza(nuevaRaza);

            System.out.print("¿Desea modificar las vacunas? (s/n): ");
            if (scanner.nextLine().trim().toLowerCase().startsWith("s")) {
                System.out.print("Ingrese las nuevas vacunas separadas por comas: ");
                String[] vacunas = scanner.nextLine().split(",");
                for (String vacuna : vacunas) {
                    mascotaEditada.agregarVacuna(vacuna.trim());
                }
            } else {
                mascotaExistente.getVacunas().forEach(mascotaEditada::agregarVacuna);
            }

            return mascotaEditada;

        } catch (Exception e) {
            System.out.println("❌ Error al editar la mascota: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void mostrarDetalles(Mascota mascota) {
        System.out.println("ID: " + mascota.getId());
        System.out.println("Nombre: " + mascota.getNombre());
        System.out.println("Raza: " + mascota.getRaza());
        System.out.println("Vacunas: " + String.join(", ", mascota.getVacunas()));
    }

    @Override
    public void mostrarLista() {
        System.out.println("\nLista de Mascotas:");
        for (Mascota mascota : elementos) {
            System.out.printf("ID: %-10s | Nombre: %-15s | Raza: %-15s | Vacunas: %s%n",
                    mascota.getId(),
                    mascota.getNombre(),
                    mascota.getRaza(),
                    String.join(", ", mascota.getVacunas()));
        }
    }

    @Override protected void mostrarEncabezadoAlta() {}
    @Override protected void mostrarEncabezadoBaja() {}
    @Override protected void mostrarEncabezadoEdicion() {}
    @Override protected Object solicitarIdParaBaja() { return null; }
    @Override protected Object solicitarIdParaEdicion() { return null; }

    @Override
    protected String getId(Mascota mascota) {
        return mascota.getId();
    }

    @Override
    protected boolean validarDatos(Mascota mascota) {
        return mascota != null &&
                mascota.getId() != null && !mascota.getId().isEmpty() &&
                mascota.getNombre() != null && !mascota.getNombre().isEmpty();
    }

    @Override
    protected void notificarCambio(String tipoOperacion, Mascota elemento) {}

    @Override
    protected Object obtenerIdElemento(Mascota mascota) {
        return mascota.getId();
    }

    @Override
    protected boolean coincideConCriterio(Mascota mascota, String criterio) {
        return mascota.getNombre().toLowerCase().contains(criterio.toLowerCase()) ||
                mascota.getId().toLowerCase().contains(criterio.toLowerCase());
    }


    /**
     * Método para cargar 5 datos de ejemplo de mascotas
     */
    public void cargarDatosDeEjemplo() {
        try {
            // Mascota 1: Perro Golden Retriever
            Mascota mascota1 = new Mascota("1", "Max");
            mascota1.setRaza("Golden Retriever");
            mascota1.agregarVacuna("Rabia");
            mascota1.agregarVacuna("Parvovirus");
            mascota1.agregarVacuna("Moquillo");
            elementos.add(mascota1);

            // Mascota 2: Gato Persa
            Mascota mascota2 = new Mascota("2", "Luna");
            mascota2.setRaza("Persa");
            mascota2.agregarVacuna("Triple Felina");
            mascota2.agregarVacuna("Leucemia Felina");
            elementos.add(mascota2);

            // Mascota 3: Perro Labrador
            Mascota mascota3 = new Mascota("3", "Rocky");
            mascota3.setRaza("Labrador");
            mascota3.agregarVacuna("Rabia");
            mascota3.agregarVacuna("Hepatitis");
            mascota3.agregarVacuna("Parainfluenza");
            elementos.add(mascota3);

            // Mascota 4: Gato Siamés
            Mascota mascota4 = new Mascota("4", "Mimi");
            mascota4.setRaza("Siamés");
            mascota4.agregarVacuna("Triple Felina");
            mascota4.agregarVacuna("Rabia");
            elementos.add(mascota4);

            // Mascota 5: Perro Pastor Alemán
            Mascota mascota5 = new Mascota("5", "Zeus");
            mascota5.setRaza("Pastor Alemán");
            mascota5.agregarVacuna("Rabia");
            mascota5.agregarVacuna("Parvovirus");
            mascota5.agregarVacuna("Moquillo");
            mascota5.agregarVacuna("Coronavirus");
            elementos.add(mascota5);

            System.out.println("✅ Se han cargado 5 mascotas de ejemplo");

        } catch (Exception e) {
            System.out.println("❌ Error al cargar datos de ejemplo: " + e.getMessage());
        }
    }


}


