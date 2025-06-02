package util;

import modelo.Cita;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;

public class GestorArchivos {

    public static boolean exportarCitasPasadas(List<Cita> citas, String criterioOrden, String extension) {
        String nombreArchivo = generarNombreArchivo(criterioOrden, extension);

        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            if (extension.equalsIgnoreCase("csv")) {
                escribirCSV(writer, citas);
            } else {
                escribirTXT(writer, citas);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String generarNombreArchivo(String criterioOrden, String extension) {
        return "01012000_RegistroCitasMascotitas_" + criterioOrden + "." + extension;
    }

    private static void escribirCSV(FileWriter writer, List<Cita> citas) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Encabezados
        writer.append("Número de Cita,Fecha,Cliente,Mascota,Servicio\n");

        // Datos
        for (Cita cita : citas) {
            writer.append(String.valueOf(cita.getNumeroCita())).append(",");
            writer.append(cita.getFechaHora().format(formatter)).append(",");
            writer.append(cita.getCliente().getNombre()).append(" ")
                 .append(cita.getCliente().getApellidoPaterno()).append(",");
            writer.append(cita.getMascota().getNombre()).append(",");
            writer.append(cita.getDescripcionServicio()).append("\n");
        }
    }

    private static void escribirTXT(FileWriter writer, List<Cita> citas) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        writer.append("REGISTRO DE CITAS PASADAS\n");
        writer.append("======================\n\n");

        for (Cita cita : citas) {
            writer.append("Número de Cita: ").append(String.valueOf(cita.getNumeroCita())).append("\n");
            writer.append("Fecha y Hora: ").append(cita.getFechaHora().format(formatter)).append("\n");
            writer.append("Cliente: ").append(cita.getCliente().getNombre()).append(" ")
                 .append(cita.getCliente().getApellidoPaterno()).append("\n");
            writer.append("Mascota: ").append(cita.getMascota().getNombre()).append("\n");
            writer.append("Servicio: ").append(cita.getDescripcionServicio()).append("\n\n");
        }
    }
}
