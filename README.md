# sistema-mascotitas
Una cadena de tiendas de mascotas y veterinarios desean llevar el control de sus ventas de  productos, adopciones de clientes y consultas médicas, vacunación o servicios de higiene  programadas a domicilio-

## Estructura sin herencia:

Mascotita/
├── src/
│   ├── app/                         ← Clase principal y menú
│   │   └── Main.java
│   │
│   ├── modelo/                      ← Clases del modelo de datos
│   │   ├── Cliente.java
│   │   ├── Mascota.java
│   │   ├── Veterinario.java
│   │   ├── Asistente.java
│   │   ├── Gerente.java
│   │   ├── Persona.java
│   │   ├── Sucursal.java (enum)
│   │   ├── Tarjeta.java
│   │   ├── Cita.java
│   │   ├── Paquete.java
│   │   └── Adopcion.java (si manejas una clase aparte para esto)
│   │
│   ├── excepciones/                ← Excepciones personalizadas
│   │   ├── CitaOcupadaException.java
│   │   └── MascotaSinVacunasException.java
│   │
│   ├── interfaces/                 ← Interfaces del sistema
│   │   └── RevisionDeCitas.java
│   │
│   ├── util/                       ← Utilidades y herramientas
│   │   ├── Ordenadores.java        ← Comparators / Comparables
│   │   └── Archivos.java           ← Escritura/lectura de archivos
│   │
│   └── datos/                      ← Simulación de base de datos o DAO
│       ├── BaseDeDatos.java        ← Mapas o listas para almacenar info
│       ├── ClienteDAO.java
│       ├── VeterinarioDAO.java
│       ├── MascotaDAO.java
│       └── CitaDAO.java
│
├── README.txt
└── Mascotita.zip                   ← Archivo a entregar con todo incluido
