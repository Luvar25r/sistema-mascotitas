# sistema-mascotitas
Una cadena de tiendas de mascotas y veterinarios desean llevar el control de sus ventas de  productos, adopciones de clientes y consultas médicas, vacunación o servicios de higiene  programadas a domicilio-

## Estructura sin herencia:
```
Mascotita/
├── src/
│   ├── app/                         ← Clase principal y menú
│   │   └── Main.java
│   │       └── Métodos (Opciones del menú):
|   |           └── 1. Alta de cliente. ya
|   |           └── 2. Alta de mascota. ya
|   |           └── 3. Alta y Baja de veterinarios o asistente personal. ya
|   |           └── 4. Alta de gerente en sucursal. ya
|   |           └── 5. Registro de citas de veterinarios a domicilio. ya
|   |           └── 6. Alta de paquetes / editar paquetes
|   |                  └── Cortes
|   |                  └── Baño
|   |                  └── Desparasitación
|   |                  └── Esterilización
|   |                  //Pregunta de si los paquetes ya estan preestablecidos o
|   |                    se debe agregar una opcion para crear paquetes personalizados.
|   |           └── 7. Adopción o donacion de mascotas
|   |           └── 8. Pago de paquetes
|   |           └── 9. Consulta de citas de veterinarios
|   |           └── 10. Consulta de Paquetes
|   |           └── 11. Consulta de Adopciones
|   |           └── 12. Consulta de veterinarios
|   |           └── 13. Escritura a archivo de citas, dar opción todas o de una fecha en
|   |                   específico.
|   |                   //Pregunta de escritura
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
---------------------------------------------------
```








---------------------------------
```
//Enum Sucursal
//*Nombre de la sucursal (establecido por el gerente)
//*Zonas manejadas

//Informacion que va a contener clase Persona:
*Nombre o Nombres (String)
*Apellido Paterno (String)
*Apellido Materno (String) *Opcional*
*Fecha_nacimiento (Date)
*CURP (String)
// ----> Hereda a Cliente, Veterinario, Asistente Personal y Gerente

//Informacion que va a contener clase Mascota:
*Nombre (String)
*Raza (String)
*Numero de Mascota (int)
*Vacunas aplicadas (Arreglo String)
//---> En A/E/B

Union de opciones del menu:
1. Alta / Baja / Edicion de informacion de Cliente o Mascota
    *Cliente
           /*Estos 3 metodos estaran dentro de una clase
           la cual va a tener Sobreescritura dependiendo de
           si es cliente, mascota o personal,etc.*/
        -Alta               
        -Baja  
        -Edicion de informacion
        
        //Se hereda desde la superclase Persona a clase Cliente que va a contener:
          *Numero de cliente (int) (atributo)
          *Tiene o no mascota (boolean) (atributo)
    *Mascota
        -Alta  
        -Baja  
        -Edicion de informacion
   //Informacion que va a contener clase Mascota:
   *Nombre (String)
   *Raza (String)
   *Numero de Mascota (int)
   *Vacunas aplicadas (Arreglo String)
   //

2. Alta / Baja /Edicion de Personal
    *Veterinario
        -A/B/E
        
        //Se hereda desde la superclase Persona a clase Veterinario que va a contener:
          *Numero de cedula de veterinario (int) (atributo)
         
    *Asistente Personal
        -A/B/E
    *Gerente
        -A/B/E
        
        //Se hereda desde la superclase Persona a clase Gerente que va a contener:
          *Tiene la sucursal (boolean) (atributo)  en caso de true (mostrar sucursal de tipo Sucursal)
          *Número de gerente (int)

3. Administracion de Citas
    *Registrar de cita de veterinarios a domicilio
       //Datos de cada cita:
            * Numero de cita (int)
            * Fecha y hora de cita (Date)
                - Verificar que la fecha y hora de cita no existen en el sistema todavia,
                  en caso contrario lanzar excepcion "No puede agendar la cita, ya se
                  encuentra ocupada”.
            * Cliente (Cliente)
            * Mascota (Mascota)
            * Servicio o paquetes contratados (Arreglo):  (Dependiendo de los que incluya, se asigna:)

                *Veterinario (Veterinario)
                * y/o Asistente de Personal (Asistente)
                * Descripcion de servicio (String)

                                                         //Tipo de servicio(uno a varios)
                                                          *Cortes
                                                          *Baño
                                                          *Desparasitacion
                                                          *Esterilizacion
                                                          *Vacunacion
                                                          *Consultas medicas
                                                        ///

    *Consulta de citas activas
    *Registro de citas pasadas

4. Administracion de Productos
    *Consulta de lista de productos
    *A/E/B de productos
    *Registrar nueva venta
        *Lugar de la venta
            *A domicilio
            *En sucursal
    *Registro de ventas pasadas 

5. Administracion de Paquetes
    *Pago de paquetes
    *Creacion / Edicion de paquetes
    *Consulta de paquetes disponibles

6. Administracion de Adopcion de Mascotas
    *Registro de Adopciones
    *Consulta de Mascotas Donadas

7. Administracion de Sucursales
    *A/E/B de Sucursal
        //Informacion que va a contener cada sucursal
        *Nombre de sucursal
        *Codigo de sucursal /PK
        *Zona de sucursal
    *Consulta de sucursales
```
