package modelo;

import java.util.Date;
import java.util.Random;

/**
 * Clase para manejo de tarjetas con validación de Luhn
 */
public class Tarjeta {
    private long numeroTarjeta; // 19 dígitos
    private Date fechaVencimiento;
    private short numeroCVC; // 4 dígitos
    private double cargo;

    public Tarjeta(Date fechaVencimiento, double cargo) {
        this.numeroTarjeta = generarNumeroTarjetaValido();
        this.fechaVencimiento = fechaVencimiento;
        this.numeroCVC = generarCVC();
        this.cargo = cargo;
    }

    /**
     * Genera un número de tarjeta válido usando el algoritmo de Luhn
     */
    private long generarNumeroTarjetaValido() {
        Random random = new Random();
        StringBuilder numero = new StringBuilder();
        
        // Generar 18 dígitos aleatorios
        for (int i = 0; i < 18; i++) {
            numero.append(random.nextInt(10));
        }
        
        // Calcular dígito verificador usando Luhn
        String numeroBase = numero.toString();
        int digitoVerificador = calcularDigitoLuhn(numeroBase);
        numero.append(digitoVerificador);
        
        return Long.parseLong(numero.toString());
    }

    /**
     * Calcula el dígito verificador usando el algoritmo de Luhn
     */
    private int calcularDigitoLuhn(String numero) {
        int suma = 0;
        boolean alternar = true;
        
        // Procesar de derecha a izquierda
        for (int i = numero.length() - 1; i >= 0; i--) {
            int digito = Character.getNumericValue(numero.charAt(i));
            
            if (alternar) {
                digito *= 2;
                if (digito > 9) {
                    digito = digito / 10 + digito % 10;
                }
            }
            
            suma += digito;
            alternar = !alternar;
        }
        
        return (10 - (suma % 10)) % 10;
    }

    /**
     * Valida un número de tarjeta usando el algoritmo de Luhn
     */
    public static boolean validarNumeroTarjeta(long numeroTarjeta) {
        String numero = String.valueOf(numeroTarjeta);
        int suma = 0;
        boolean alternar = false;
        
        for (int i = numero.length() - 1; i >= 0; i--) {
            int digito = Character.getNumericValue(numero.charAt(i));
            
            if (alternar) {
                digito *= 2;
                if (digito > 9) {
                    digito = digito / 10 + digito % 10;
                }
            }
            
            suma += digito;
            alternar = !alternar;
        }
        
        return suma % 10 == 0;
    }

    private short generarCVC() {
        Random random = new Random();
        return (short) (1000 + random.nextInt(9000)); // 4 dígitos
    }

    // Getters y Setters
    public long getNumeroTarjeta() { return numeroTarjeta; }
    public Date getFechaVencimiento() { return fechaVencimiento; }
    public short getNumeroCVC() { return numeroCVC; }
    public double getCargo() { return cargo; }
    public void setCargo(double cargo) { this.cargo = cargo; }

    @Override
    public String toString() {
        return "Tarjeta{" +
                "numeroTarjeta=" + numeroTarjeta +
                ", fechaVencimiento=" + fechaVencimiento +
                ", cargo=" + cargo +
                '}';
    }
}