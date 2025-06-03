package datos;

import java.sql.*;

/**
 * Clase para manejar la conexión a la base de datos SQLite
 */
public class ConexionDB {
    private static final String URL_DB = "jdbc:sqlite:basededatos.sqlite";
    private static ConexionDB instancia;
    private Connection conexion;

    private ConexionDB() {
        try {
            // Cargar el driver de SQLite
            Class.forName("org.sqlite.JDBC");
            this.conexion = DriverManager.getConnection(URL_DB);
            System.out.println("✅ Conexión a la base de datos establecida correctamente.");
            inicializarBaseDatos();
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Error: No se encontró el driver de SQLite.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static ConexionDB getInstance() {
        if (instancia == null) {
            instancia = new ConexionDB();
        }
        return instancia;
    }

    public Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL_DB);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener conexión: " + e.getMessage());
            e.printStackTrace();
        }
        return conexion;
    }

    private void inicializarBaseDatos() {
        try {
            Statement stmt = conexion.createStatement();
            
            // Crear tabla productos
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS productos (
                    id_producto INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre VARCHAR(100) NOT NULL,
                    precio DECIMAL(10, 2) NOT NULL CHECK (precio >= 0),
                    stock INTEGER NOT NULL CHECK (stock >= 0),
                    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
                    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP
                )
            """);

            // Crear tabla veterinarios
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS veterinarios (
                    numero_cedula INTEGER PRIMARY KEY,
                    nombre VARCHAR(50) NOT NULL,
                    apellido_paterno VARCHAR(50) NOT NULL,
                    apellido_materno VARCHAR(50),
                    fecha_nacimiento DATE NOT NULL,
                    curp VARCHAR(18) NOT NULL UNIQUE,
                    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
                    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP
                )
            """);

            // Crear tabla mascotas
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS mascotas (
                    id VARCHAR(50) PRIMARY KEY,
                    nombre VARCHAR(100) NOT NULL,
                    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
                    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP
                )
            """);

            // Crear tabla vacunas
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS vacunas (
                    id_vacuna INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre_vacuna VARCHAR(100) NOT NULL UNIQUE,
                    descripcion TEXT
                )
            """);

            // Crear tabla mascota_vacunas
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS mascota_vacunas (
                    id_mascota VARCHAR(50),
                    id_vacuna INTEGER,
                    fecha_aplicacion DATE DEFAULT CURRENT_DATE,
                    PRIMARY KEY (id_mascota, id_vacuna),
                    FOREIGN KEY (id_mascota) REFERENCES mascotas(id) ON DELETE CASCADE,
                    FOREIGN KEY (id_vacuna) REFERENCES vacunas(id_vacuna) ON DELETE CASCADE
                )
            """);

            // Crear índices
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_productos_nombre ON productos(nombre)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_veterinarios_curp ON veterinarios(curp)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_veterinarios_nombre ON veterinarios(nombre, apellido_paterno)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_mascotas_nombre ON mascotas(nombre)");

            stmt.close();
            System.out.println("✅ Base de datos inicializada correctamente.");

        } catch (SQLException e) {
            System.err.println("❌ Error al inicializar la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("✅ Conexión a la base de datos cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al cerrar la conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }
}