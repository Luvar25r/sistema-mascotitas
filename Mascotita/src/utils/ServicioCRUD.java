package utils;

import modelo.Asistente;
import modelo.Mascota;
import modelo.Servicio;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public void alta() {
        altaInteractiva();
    }

    @Override
    public void baja() {
        bajaInteractiva();
    }

    @Override
    public void edicion() {
        edicionInteractiva();
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
                public boolean asistenteDisponible(Asistente asistente, Date fechaHoraDate) {
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
                public boolean asistenteDisponible(Asistente asistente, Date fechaHoraDate) {
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
        return elemento.getNombre();
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

    public void consulta() { 
        consultaInteractiva();
    }

    public void consultaInteractiva() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║          CONSULTA DE SERVICIO        ║");
        System.out.println("╚══════════════════════════════════════╝");

        if (elementos.isEmpty()) {
            System.out.println("❌ No hay servicios registrados en el sistema.");
            return;
        }

        System.out.println("\n¿Cómo desea buscar el servicio?");
        System.out.println("1. Por nombre del servicio");
        System.out.println("2. Por criterio");
        System.out.println("3. Mostrar todos los servicios");
        System.out.print("Seleccione una opción: ");

        int opcionnum = leerEntero("");

        switch (opcionnum) {
            case 1:
                String opcion = leerTexto("➤ Ingrese el nombre del servicio: ");
                consultarPorNombre(opcion);
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

    /**
     * Busca un servicio por su nombre (case-insensitive)
     * @param nombreServicio Nombre del servicio a buscar
     * @return Optional con el servicio si se encuentra
     */
    public Optional<Servicio> consultarPorNombre(String nombreServicio) {
        if (nombreServicio == null || nombreServicio.trim().isEmpty()) {
            System.out.println("❌ El nombre del servicio no puede estar vacío.");
            return Optional.empty();
        }

        // Buscar en la lista de servicios (heredada de OperacionesCRUD)
        Optional<Servicio> servicioEncontrado = elementos.stream()
                .filter(servicio -> servicio.getNombre().equalsIgnoreCase(nombreServicio))
                .findFirst();

        if (servicioEncontrado.isPresent()) {
            System.out.println("\n✅ Servicio encontrado:");
            mostrarDetalles(servicioEncontrado.get());
        } else {
            System.out.println("❌ No se encontró el servicio con el nombre: " + nombreServicio);
        }

        return servicioEncontrado;
    }
    
    private void consultarPorCriterio() {
        String criterio = leerTexto("➤ Ingrese nombre o descripción a buscar: ");

        if (criterio.trim().isEmpty()) {
            System.out.println("❌ Debe ingresar un criterio de búsqueda.");
            return;
        }

        List<Servicio> serviciosEncontrados = buscarPorCriterio(criterio);

        if (serviciosEncontrados.isEmpty()) {
            System.out.println("❌ No se encontraron servicios que coincidan con: " + criterio);
        } else {
            System.out.println("\n✅ Se encontraron " + serviciosEncontrados.size() + " servicio(s):");
            System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
            System.out.println("║                    RESULTADOS DE BÚSQUEDA                     ║");
            System.out.println("╠════════════════════════════════════════════════════════════════╣");
            System.out.println("║           Nombre           │  Precio  │      Descripción      ║");
            System.out.println("╠════════════════════════════════════════════════════════════════╣");

            for (Servicio servicio : serviciosEncontrados) {
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

            if (serviciosEncontrados.size() == 1) {
                String respuesta = leerTexto("\n¿Desea ver los detalles completos? (s/n): ");
                if (respuesta.toLowerCase().startsWith("s")) {
                    mostrarDetalles(serviciosEncontrados.get(0));
                }
            } else {
                String respuesta = leerTexto("\n¿Desea ver los detalles de algún servicio? (s/n): ");
                if (respuesta.toLowerCase().startsWith("s")) {
                    String nombre = leerTexto("➤ Ingrese el nombre del Servicio: ");
                    Optional<Servicio> servicio = serviciosEncontrados.stream()
                            .filter(s -> s.getNombre().equalsIgnoreCase(nombre))
                            .findFirst();

                    if (servicio.isPresent()) {
                        mostrarDetalles(servicio.get());
                    } else {
                        System.out.println("❌ El nombre ingresado no está en los resultados.");
                    }
                }
            }
        }
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

    public void cargarDatosEjemplo() {
        // Servicio de Baño
        Servicio bano = new Servicio("Baño", "Baño completo con champú especial, secado y perfumado para mascotas", 25.00) {
            @Override
            public boolean veterinarioDisponible() {
                return true;
            }

            @Override
            public boolean asistenteDisponible(Asistente asistente, Date fechaHoraDate) {
                return true;
            }

            @Override
            public boolean mascotaVacunada(Mascota mascota) {
                return true;
            }

            @Override
            public boolean requiereVeterinario() {
                return false;
            }

            @Override
            public boolean requiereAsistente() {
                return true;
            }

            @Override
            public boolean requiereVacunas() {
                return false;
            }

            @Override
            public boolean revisarDisponibilidad(LocalDateTime fechaHora) {
                return true;
            }
        };

        // Servicio de Corte
        Servicio corte = new Servicio("Corte", "Corte y arreglo de pelo profesional adaptado a la raza de la mascota", 35.00) {
            @Override
            public boolean veterinarioDisponible() {
                return true;
            }

            @Override
            public boolean asistenteDisponible(Asistente asistente, Date fechaHoraDate) {
                return true;
            }

            @Override
            public boolean mascotaVacunada(Mascota mascota) {
                return true;
            }

            @Override
            public boolean requiereVeterinario() {
                return false;
            }

            @Override
            public boolean requiereAsistente() {
                return true;
            }

            @Override
            public boolean requiereVacunas() {
                return false;
            }

            @Override
            public boolean revisarDisponibilidad(LocalDateTime fechaHora) {
                return true;
            }
        };

        // Servicio de Desparasitación
        Servicio desparasitacion = new Servicio("Desparasitacion", "Tratamiento antiparasitario interno y externo con medicamentos especializados", 45.00) {
            @Override
            public boolean veterinarioDisponible() {
                return true;
            }

            @Override
            public boolean asistenteDisponible(Asistente asistente, Date fechaHoraDate) {
                return true;
            }

            @Override
            public boolean mascotaVacunada(Mascota mascota) {
                return true;
            }

            @Override
            public boolean requiereVeterinario() {
                return true;
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
                return true;
            }
        };

        // Servicio de Esterilización
        Servicio esterilizacion = new Servicio("Esterilizacion", "Cirugía de esterilización para control reproductivo, incluye pre y post operatorio", 150.00) {
            @Override
            public boolean veterinarioDisponible() {
                return true;
            }

            @Override
            public boolean asistenteDisponible(Asistente asistente, Date fechaHoraDate) {
                return true;
            }

            @Override
            public boolean mascotaVacunada(Mascota mascota) {
                return mascota != null; // Requiere evaluación previa
            }

            @Override
            public boolean requiereVeterinario() {
                return true;
            }

            @Override
            public boolean requiereAsistente() {
                return true;
            }

            @Override
            public boolean requiereVacunas() {
                return true;
            }

            @Override
            public boolean revisarDisponibilidad(LocalDateTime fechaHora) {
                return true;
            }
        };

        // Servicio de Vacunación
        Servicio vacunacion = new Servicio("Vacunacion", "Aplicación de vacunas según calendario de inmunización y edad de la mascota", 30.00) {
            @Override
            public boolean veterinarioDisponible() {
                return true;
            }

            @Override
            public boolean asistenteDisponible(Asistente asistente, Date fechaHoraDate) {
                return true;
            }

            @Override
            public boolean mascotaVacunada(Mascota mascota) {
                return true;
            }

            @Override
            public boolean requiereVeterinario() {
                return true;
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
                return true;
            }
        };

        // Agregar todos los servicios a la lista
        elementos.add(bano);
        elementos.add(corte);
        elementos.add(desparasitacion);
        elementos.add(esterilizacion);
        elementos.add(vacunacion);

        System.out.println("✅ Se han cargado 5 servicios de ejemplo en el sistema.");
    }

}