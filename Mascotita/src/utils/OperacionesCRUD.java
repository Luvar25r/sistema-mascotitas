package utils;

import java.util.*;

public abstract class OperacionesCRUD<T> {
    protected List<T> elementos;
    protected Scanner scanner;
    
    public OperacionesCRUD() {
        this.elementos = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }
    
    // Operaciones CRUD principales
    public boolean alta(T elemento) {
        if (!validarDatos(elemento)) {
            System.out.println("❌ Los datos no son válidos.");
            return false;
        }
        
        if (elementos.add(elemento)) {
            notificarCambio("ALTA", elemento);
            // Aquí podrías agregar la persistencia de datos si es necesario
            return true;
        }
        
        return false;
    }
    
    public boolean baja(T elemento) {
        if (elemento != null && elementos.remove(elemento)) {
            notificarCambio("BAJA", elemento);
            return true;
        }
        return false;
    }
    
    public boolean bajaById(Object id) {
        Optional<T> elemento = buscarPorId(id);
        if (elemento.isPresent()) {
            return baja(elemento.get());
        }
        return false;
    }
    
    public boolean edicion(T elementoAntiguo, T elementoNuevo) {
        if (elementoAntiguo != null && elementoNuevo != null && validarDatos(elementoNuevo)) {
            int index = elementos.indexOf(elementoAntiguo);
            if (index != -1) {
                elementos.set(index, elementoNuevo);
                notificarCambio("EDICIÓN", elementoNuevo);
                return true;
            }
        }
        return false;
    }
    
    public boolean edicionById(Object id, T elementoNuevo) {
        Optional<T> elementoAntiguo = buscarPorId(id);
        if (elementoAntiguo.isPresent()) {
            return edicion(elementoAntiguo.get(), elementoNuevo);
        }
        return false;
    }
    
    // Métodos de consulta
    public List<T> listarTodos() {
        return new ArrayList<>(elementos);
    }
    
    public Optional<T> buscarPorId(Object id) {
        return elementos.stream()
                .filter(elemento -> Objects.equals(obtenerIdElemento(elemento), id))
                .findFirst();
    }
    
    public List<T> buscarPorCriterio(String criterio) {
        return elementos.stream()
                .filter(elemento -> coincideConCriterio(elemento, criterio))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    public int contarElementos() {
        return elementos.size();
    }
    
    public boolean existe(T elemento) {
        return elementos.contains(elemento);
    }
    
    public boolean existeById(Object id) {
        return buscarPorId(id).isPresent();
    }

    public abstract void alta();

    public abstract void baja();

    public abstract void edicion();

    // Métodos interactivos abstractos que deben implementar las clases hijas
    public abstract T solicitarDatosAlta();
    public abstract T solicitarDatosEdicion(T elementoExistente);
    public abstract void mostrarDetalles(T elemento);
    public abstract void mostrarLista();
    
    // Métodos de alta, baja y edición interactivos
    public boolean altaInteractiva() {
        mostrarEncabezadoAlta();
        T nuevoElemento = solicitarDatosAlta();
        if (nuevoElemento != null) {
            return alta(nuevoElemento);
        }
        System.out.println("❌ No se pudo completar el alta.");
        return false;
    }
    
    public boolean bajaInteractiva() {
        mostrarEncabezadoBaja();
        if (elementos.isEmpty()) {
            System.out.println("❌ No hay elementos para eliminar.");
            return false;
        }
        
        mostrarLista();
        Object id = solicitarIdParaBaja();
        
        Optional<T> elemento = buscarPorId(id);
        if (elemento.isPresent()) {
            System.out.println("\n📋 Elemento a eliminar:");
            mostrarDetalles(elemento.get());
            
            String confirmacion = leerTexto("\n¿Está seguro de eliminar este elemento? (s/n): ");
            if (confirmacion.toLowerCase().startsWith("s")) {
                return bajaById(id);
            } else {
                System.out.println("❌ Operación cancelada.");
                return false;
            }
        } else {
            System.out.println("❌ No se encontró un elemento con el ID especificado.");
            return false;
        }
    }
    
    public boolean edicionInteractiva() {
        mostrarEncabezadoEdicion();
        if (elementos.isEmpty()) {
            System.out.println("❌ No hay elementos para editar.");
            return false;
        }
        
        mostrarLista();
        Object id = solicitarIdParaEdicion();
        
        Optional<T> elementoExistente = buscarPorId(id);
        if (elementoExistente.isPresent()) {
            T elementoEditado = solicitarDatosEdicion(elementoExistente.get());
            if (elementoEditado != null) {
                return edicionById(id, elementoEditado);
            } else {
                System.out.println("❌ No se pudo completar la edición.");
                return false;
            }
        } else {
            System.out.println("❌ No se encontró un elemento con el ID especificado.");
            return false;
        }
    }
    
    // Métodos auxiliares
    protected abstract void mostrarEncabezadoAlta();
    protected abstract void mostrarEncabezadoBaja();
    protected abstract void mostrarEncabezadoEdicion();
    protected abstract Object solicitarIdParaBaja();
    protected abstract Object solicitarIdParaEdicion();
    
    // **NUEVO MÉTODO ABSTRACTO - getId() para obtener el ID de cualquier elemento**
    protected abstract String getId(T elemento);
    
    // Utilidades comunes
    protected String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }
    
    protected String leerTextoOpcional(String mensaje) {
        System.out.print(mensaje);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? null : input;
    }
    
    protected int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, ingrese un número válido.");
            }
        }
    }
    
    protected double leerDecimal(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, ingrese un número decimal válido.");
            }
        }
    }
    
    protected boolean leerBooleano(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (s/n): ");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            if (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí")) {
                return true;
            } else if (respuesta.equals("n") || respuesta.equals("no")) {
                return false;
            } else {
                System.out.println("❌ Por favor, responda 's' para sí o 'n' para no.");
            }
        }
    }
    
    // Métodos abstractos que deben implementar las clases hijas
    protected abstract boolean validarDatos(T elemento);
    protected abstract void notificarCambio(String tipoOperacion, T elemento);
    protected abstract Object obtenerIdElemento(T elemento);
    protected abstract boolean coincideConCriterio(T elemento, String criterio);
}