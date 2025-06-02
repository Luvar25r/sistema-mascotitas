package modelo.mascotametds;

import modelo.Mascota;

public class MascotaDevuelta extends Mascota {
    private boolean tieneLesiones;

    public MascotaDevuelta(String id, String nombre, boolean tieneLesiones) {
        super(id, nombre);
        this.tieneLesiones = tieneLesiones;
    }

    public boolean tieneLesiones() {
        return tieneLesiones;
    }

    public void setTieneLesiones(boolean tieneLesiones) {
        this.tieneLesiones = tieneLesiones;
    }

    public double calcularCargoPorMaltrato() {
        if (tieneLesiones) {
            return 1500.0;
        }
        return 0.0;
    }

    public void sugerirLlamadaBVA() {
        if (tieneLesiones) {
            System.out.println("Sugerencia: Contactar a la Brigada de Vigilancia Animal (BVA) al 55 5208 9898.");
        }
    }
}