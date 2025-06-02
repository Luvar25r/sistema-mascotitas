package utils;

import java.util.Comparator;
import java.util.function.Function;

/**
 * Clase utilitaria para crear comparadores genéricos reutilizables
 * que permiten ordenar cualquier tipo de objeto de manera ascendente o descendente
 */
public class OrdenadorBase {

    /**
     * Crea un comparador para ordenar objetos basado en una función de extracción de valor
     *
     * @param <T> Tipo de objeto a ordenar
     * @param <U> Tipo del valor por el cual ordenar (debe ser Comparable)
     * @param keyExtractor Función que extrae el valor de comparación del objeto
     * @param ascendente true para orden ascendente, false para descendente
     * @return Comparator configurado
     */
    public static <T, U extends Comparable<? super U>> Comparator<T> crearComparador(
            Function<T, U> keyExtractor, boolean ascendente) {

        Comparator<T> comparator = Comparator.comparing(keyExtractor);
        return ascendente ? comparator : comparator.reversed();
    }

    /**
     * Crea un comparador para ordenar objetos basado en una función de extracción de valor
     * que puede ser nulo, usando un valor por defecto
     *
     * @param <T> Tipo de objeto a ordenar
     * @param <U> Tipo del valor por el cual ordenar (debe ser Comparable)
     * @param keyExtractor Función que extrae el valor de comparación del objeto
     * @param defaultValue Valor por defecto para casos nulos
     * @param ascendente true para orden ascendente, false para descendente
     * @return Comparator configurado
     */
    public static <T, U extends Comparable<? super U>> Comparator<T> crearComparadorConDefault(
            Function<T, U> keyExtractor, U defaultValue, boolean ascendente) {

        Comparator<T> comparator = Comparator.comparing(
            obj -> {
                U value = keyExtractor.apply(obj);
                return value != null ? value : defaultValue;
            }
        );
        return ascendente ? comparator : comparator.reversed();
    }

    /**
     * Crea un comparador para ordenar por múltiples criterios
     *
     * @param <T> Tipo de objeto a ordenar
     * @param comparadores Array de comparadores a aplicar en orden de prioridad
     * @return Comparator compuesto
     */
    @SafeVarargs
    public static <T> Comparator<T> crearComparadorCompuesto(Comparator<T>... comparadores) {
        if (comparadores.length == 0) {
            throw new IllegalArgumentException("Debe proporcionar al menos un comparador");
        }

        Comparator<T> resultado = comparadores[0];
        for (int i = 1; i < comparadores.length; i++) {
            resultado = resultado.thenComparing(comparadores[i]);
        }
        return resultado;
    }

    /**
     * Crea un comparador para ordenar strings de manera case-insensitive
     *
     * @param <T> Tipo de objeto a ordenar
     * @param keyExtractor Función que extrae el String del objeto
     * @param ascendente true para orden ascendente, false para descendente
     * @return Comparator configurado
     */
    public static <T> Comparator<T> crearComparadorStringInsensitive(
            Function<T, String> keyExtractor, boolean ascendente) {
        Comparator<T> comparator = Comparator.comparing(
            keyExtractor, String.CASE_INSENSITIVE_ORDER
        );
        return ascendente ? comparator : comparator.reversed();
    }
}