package modulos;

import java.util.*;
import modelo.Mascota;
import modelo.mascotametds.MascotaDevuelta; // Corregir el import
import modelo.personas.Cliente;
import excepciones.ExcepcionAdopcion;

/**
 * Clase encargada de la administración de adopciones.
 */
public class AdopcionManager {
    private Set<Mascota> mascotasDisponibles;
    private Map<Cliente, Mascota> adopciones;

    public enum OrdenAdopcion {
        CLIENTE_ASC, CLIENTE_DESC, MASCOTA_ASC, MASCOTA_DESC
    }

    public AdopcionManager() {
        this.mascotasDisponibles = new HashSet<>();
        this.adopciones = new TreeMap<>(); // orden por clave (Cliente.compareTo)
    }

    public void registrarMascotaDisponible(Mascota mascota) {
        mascotasDisponibles.add(mascota);
    }

    public Set<Mascota> obtenerMascotasDisponibles() {
        return Collections.unmodifiableSet(mascotasDisponibles);
    }

    public void elegirMascotaParaAdopcion(Cliente cliente, Mascota mascota) throws ExcepcionAdopcion {
        if (mascota.getVacunas().isEmpty()) {
            throw new ExcepcionAdopcion("La mascota no tiene vacunas suministradas y no es apta para su adopción.");
        }
        adopciones.put(cliente, mascota);
        mascotasDisponibles.remove(mascota);
    }

    public List<Map.Entry<Cliente, Mascota>> listarAdopciones(OrdenAdopcion orden) {
        List<Map.Entry<Cliente, Mascota>> lista = new ArrayList<>(adopciones.entrySet());

        switch (orden) {
            case CLIENTE_ASC:
                lista.sort(Comparator.comparing(e -> e.getKey().getId()));
                break;
            case CLIENTE_DESC:
                lista.sort(Comparator.comparing((Map.Entry<Cliente, Mascota> e) -> e.getKey().getId()).reversed());
                break;
            case MASCOTA_ASC:
                lista.sort(Comparator.comparing(e -> e.getValue().getNombre()));
                break;
            case MASCOTA_DESC:
                lista.sort(Comparator.comparing((Map.Entry<Cliente, Mascota> e) -> e.getValue().getNombre()).reversed());
                break;
        }
        return lista;
    }

    public void devolverMascota(Cliente cliente, boolean tieneLesiones) {
        Mascota mascota = adopciones.remove(cliente);
        if (mascota == null) {
            throw new NoSuchElementException("El cliente no tiene una adopción registrada.");
        }
        MascotaDevuelta devuelta = new MascotaDevuelta(mascota.getId(), mascota.getNombre(), tieneLesiones);
        double cargo = devuelta.calcularCargoPorMaltrato();
        if (cargo > 0) {
            System.out.println("Cargo por maltrato aplicado: $" + cargo);
            devuelta.sugerirLlamadaBVA();
        }
    }

    public boolean existeAdopcion(Cliente cliente) {
        return adopciones.containsKey(cliente);
    }
}