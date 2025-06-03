package utils;

import modelo.Mascota;
import modelo.Servicio;

import java.time.LocalDateTime;

public class ServicioCRUD extends OperacionesCRUD<Servicio> {
    
    @Override
    protected boolean validarDatos(Servicio servicio) {
        return servicio != null 
            && servicio.getNombre() != null 
            && !servicio.getNombre().trim().isEmpty()
            && servicio.getPrecio() >= 0
            && servicio.getDescripcion() != null
            && !servicio.getDescripcion().trim().isEmpty();
    }
    
    @Override
    protected void notificarCambio(String tipoOperacion, Servicio servicio) {
        System.out.println("══════════════════════════════════════");
        System.out.println("✓ " + tipoOperacion + " DE SERVICIO EXITOSA");
        System.out.println("Servicio: " + servicio.getNombre());
        System.out.println("Precio: $" + servicio.getPrecio());
        System.out.println("══════════════════════════════════════");
    }
    
    @Override
    protected Object obtenerIdElemento(Servicio servicio) {
        return servicio.getNombre(); // Usando nombre como identificador único
    }
    
    @Override
    protected boolean coincideConCriterio(Servicio servicio, String criterio) {
        String criterioLower = criterio.toLowerCase();
        return servicio.getNombre().toLowerCase().contains(criterioLower)
            || servicio.getDescripcion().toLowerCase().contains(criterioLower);
    }
    
    @Override
    public Servicio solicitarDatosAlta() {
        try {
            String nombre = leerTexto("➤ Ingrese el nombre del servicio: ");
            if (nombre.isEmpty()) {
                System.out.println("❌ El nombre es obligatorio.");
                return null;
            }
            
            if (existeById(nombre)) {
                System.out.println("❌ Ya existe un servicio con ese nombre.");
                return null;
            }
            
            String descripcion = leerTexto("➤ Ingrese la descripción: ");
            if (descripcion.isEmpty()) {
                System.out.println("❌ La descripción es obligatoria.");
                return null;
            }
            
            double precio = leerDecimal("➤ Ingrese el precio: $");
            if (precio < 0) {
                System.out.println("❌ El precio no puede ser negativo.");
                return null;
            }
            
            return new Servicio(nombre, descripcion, precio) {
                @Override
                public boolean veterinarioDisponible() {
                    return false;
                }

                @Override
                public boolean asistenteDisponible() {
                    return false;
                }

                @Override
                public boolean mascotaVacunada(Mascota mascota) {
                    return false;
                }

                @Override
                public boolean requiereVeterinario() {
                    return false;
                }

                @Override
                public boolean requiereAsistente() {
                    return false;
                }

                @Override
                public boolean requiereVacunas() {
                    return false;
                }

                @Override
                public boolean revisarDisponibilidad(LocalDateTime fechaHora) {
                    return false;
                }
            };
            
        } catch (Exception e) {
            System.out.println("❌ Error al solicitar datos: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public Servicio solicitarDatosEdicion(Servicio servicioExistente) {
        try {
            System.out.println("\n📝 Datos actuales del servicio:");
            mostrarDetalles(servicioExistente);
            System.out.println("\n➤ Ingrese los nuevos datos (presione Enter para mantener el valor actual):");
            
            String nombre = leerTexto("➤ Nombre [" + servicioExistente.getNombre() + "]: ");
            if (nombre.isEmpty()) nombre = servicioExistente.getNombre();
            
            String descripcion = leerTexto("➤ Descripción [" + servicioExistente.getDescripcion() + "]: ");
            if (descripcion.isEmpty()) descripcion = servicioExistente.getDescripcion();
            
            String precioStr = leerTexto("➤ Precio [$" + servicioExistente.getPrecio() + "]: ");
            double precio = servicioExistente.getPrecio();
            if (!precioStr.isEmpty()) {
                try {
                    precio = Double.parseDouble(precioStr);
                    if (precio < 0) {
                        System.out.println("❌ Precio inválido, manteniendo precio actual.");
                        precio = servicioExistente.getPrecio();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("❌ Formato inválido, manteniendo precio actual.");
                }
            }
            
            return new Servicio(nombre, descripcion, precio) {
                @Override
                public boolean veterinarioDisponible() {
                    return false;
                }

                @Override
                public boolean asistenteDisponible() {
                    return false;
                }

                @Override
                public boolean mascotaVacunada(Mascota mascota) {
                    return false;
                }

                @Override
                public boolean requiereVeterinario() {
                    return false;
                }

                @Override
                public boolean requiereAsistente() {
                    return false;
                }

                @Override
                public boolean requiereVacunas() {
                    return false;
                }

                @Override
                public boolean revisarDisponibilidad(LocalDateTime fechaHora) {
                    return false;
                }
            };
            
        } catch (Exception e) {
            System.out.println("❌ Error al solicitar datos de edición: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public void mostrarDetalles(Servicio servicio) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║         DETALLES DEL SERVICIO        ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ Nombre: " + String.format("%-30s", servicio.getNombre()) + "║");
        System.out.println("║ Precio: $" + String.format("%-29s", servicio.getPrecio()) + "║");
        System.out.println("║ Descripción:                         ║");
        
        // Dividir descripción en líneas de 36 caracteres máximo
        String descripcion = servicio.getDescripcion();
        int maxLinea = 36;
        for (int i = 0; i < descripcion.length(); i += maxLinea) {
            int fin = Math.min(i + maxLinea, descripcion.length());
            String linea = descripcion.substring(i, fin);
            System.out.println("║ " + String.format("%-37s", linea) + "║");
        }
        
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    public void mostrarLista() {
        if (elementos.isEmpty()) {
            System.out.println("❌ No hay servicios registrados.");
            return;
        }
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                       LISTA DE SERVICIOS                      ║");
        System.out.println("╠════════════════════════════════════════════════════════════════╣");
        System.out.println("║           Nombre           │  Precio  │      Descripción      ║");
        System.out.println("╠════════════════════════════════════════════════════════════════╣");
        
        for (Servicio servicio : elementos) {
            String descripcionCorta = servicio.getDescripcion().length() > 20 ? 
                                    servicio.getDescripcion().substring(0, 17) + "..." : 
                                    servicio.getDescripcion();
            
            System.out.printf("║ %-25s │ $%-7.2f │ %-20s ║%n", 
                            servicio.getNombre().length() > 25 ? 
                            servicio.getNombre().substring(0, 22) + "..." : 
                            servicio.getNombre(),
                            servicio.getPrecio(),
                            descripcionCorta);
        }
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
    
    @Override
    protected void mostrarEncabezadoAlta() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║          ALTA DE SERVICIO            ║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    protected void mostrarEncabezadoBaja() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║          BAJA DE SERVICIO            ║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    protected void mostrarEncabezadoEdicion() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║        EDICIÓN DE SERVICIO           ║");
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    @Override
    protected Object solicitarIdParaBaja() {
        return leerTexto("➤ Ingrese el nombre del servicio a eliminar: ");
    }
    
    @Override
    protected Object solicitarIdParaEdicion() {
        return leerTexto("➤ Ingrese el nombre del servicio a editar: ");
    }

    @Override
    protected String getId(Servicio elemento) {
        return "";
    }

    // Métodos específicos para Servicios
    public boolean actualizarPrecio(String nombreServicio, double nuevoPrecio) {
        var servicio = buscarPorId(nombreServicio);
        if (servicio.isPresent() && nuevoPrecio >= 0) {
            Servicio s = servicio.get();
            s.setPrecio(nuevoPrecio);
            notificarCambio("ACTUALIZACIÓN_PRECIO", s);
            return true;
        }
        return false;
    }
    
    public boolean actualizarPrecioInteractivo() {
        try {
            if (elementos.isEmpty()) {
                System.out.println("❌ No hay servicios registrados.");
                return false;
            }
            
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║      ACTUALIZACIÓN DE PRECIO         ║");
            System.out.println("╚══════════════════════════════════════╝");
            
            mostrarLista();
            String nombreServicio = leerTexto("➤ Ingrese el nombre del servicio: ");
            double nuevoPrecio = leerDecimal("➤ Ingrese el nuevo precio: $");
            
            return actualizarPrecio(nombreServicio, nuevoPrecio);
            
        } catch (Exception e) {
            System.out.println("❌ Error durante la actualización: " + e.getMessage());
            return false;
        }
    }
}