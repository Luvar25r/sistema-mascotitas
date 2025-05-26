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
|   |           └── 7. Adopción o devolucion de mascotas
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
│   │   └── Adopcion.java 
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

#Nota: Los "Paquetes", se llaman "Servicios" en este sistema.

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

//Informacion que va a contener la clase Servicios (uno a varios):
  *Cortes 
        Verificaciones necesarias para agendar citas (interface revisiondeCitas):
            -Mascota Vacunada : Necesario
            -Veterinario: No necesario
            -Asistente: Necesario
        Con Override todo esto en la interface.
  *Baño
        Verificaciones necesarias para agendar citas:
            -Mascota Vacunada : Necesario
            -Veterinario: No necesario
            -Asistente: Necesario
  *Desparasitacion
        Verificaciones necesarias para agendar citas:
            -Mascota Vacunada : Necesario
            -Veterinario: Necesario
            -Asistente: No necesario
  *Esterilizacion
        Verificaciones necesarias para agendar citas:
            -Mascota Vacunada : Necesario
            -Veterinario: Necesario
            -Asistente: Necesario
  *Vacunacion
        Verificaciones necesarias para agendar citas:
            -Mascota Vacunada : No necesario
            -Veterinario: Necesario
            -Asistente: No necesario
  *Consultas medicas
        Verificaciones necesarias para agendar citas:
            -Mascota Vacunada : No necesario
            -Veterinario: Necesario
            -Asistente: No necesario
//

//Informacion que va a contener la clase Cita:
    *Datos de cada cita:
            * Numero de cita (int)
            * Fecha y hora de cita (Date)
            * Cliente (Cliente)
            * Mascota (Mascota)
            * Servicios contratados (Arreglo):  (Dependiendo de los que incluya, se asigna:)

                *Veterinario (Veterinario)
                * y/o Asistente de Personal (Asistente)
                * Descripcion de servicio (String)
    

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
        //Se hereda desde la superclase Mascota

------------------------------------
2. Alta / Baja /Edicion de Personal
    *Veterinario
        -A/B/E
        Orden:
            *A-z (Ascendente por nombre de veterinario)
            *Z-A (Descendente por nombre de veterinario)
        
        //Se hereda desde la superclase Persona a clase Veterinario que va a contener:
          *Numero de cedula de veterinario (int) (atributo)
         
    *Asistente Personal
        -A/B/E
        //Se hereda desde la superclase Persona a clase Asistente que va a contener:
            *ID de Asistente (int) (atributo)
    *Gerente
        -A/B/E
        
        //Se hereda desde la superclase Persona a clase Gerente que va a contener:
          *Tiene la sucursal (boolean) (atributo)  en caso de true (mostrar sucursal de tipo Sucursal)
          *Número de gerente (int)
          *

--------------------------
3. Administracion de Citas
    *Registrar de cita de veterinarios a domicilio
       *Registrar los datos de clase Cita (los datos estan guardados de forma temporal adentro de esta clase)
       * Uso de la interface revisionDeCitas(servicio) (Dependiendo del servicio, las verificaciones necesarias).
            * veterinarioDisponible (boolean)
                - En caso de que no haya veterinarios disponibles lanzar excepcion "No hay veterinarios disponibles"
            * asistenteDisponible (boolean)
                - En caso de que no haya asistentes disponibles lanzar excepcion "No hay veterinarios disponibles"
            * mascotaVacunada (boolean)
                - En caso de que no haya veterinarios disponibles lanzar excepcion "No hay veterinarios disponibles"
            * revisarDisponibilidad (boolean) OBLIGATORIA
                - Verificar que la fecha y hora de cita no existen en el sistema todavia,
                  en caso contrario lanzar excepcion "No puede agendar la cita, ya se
                  encuentra ocupada”.
                  
       *Si se cumplen todas las condiciones necesarias, guardar informacion de Cita en Citas Agendadas.
       *Cuando CURRENT_TIME (Tiempo actual) sea despues de una Cita Agendada, mover la informacion de Cita Agendada a  
       registro de Citas Pasadas.

    *Consulta de Citas Agendadas:
        Orden predeterminado:
            *Fecha mas cercana
      
    *Registro de citas pasadas
        Metodos:
        - Generar listado de todas las citas y preguntar el orden por: (Usar Comparable o Comparator)
            * Fecha
                o Mas reciente
                o Mas antigua
            * Nombre del cliente
                o A-Z
                o Z-A
            * Apellido Paterno
                o A-Z
                o Z-A
            * Apellido Materno
                o A-Z
                o Z-A
        - Preguntar si se desea descargar el listado en archivo (con java.io)
            *Nombre predeterminado de archivo: 01012000_RegistroCitasMascotitas_[Orden seleccionado].csv o .txt
            

5. Administracion de Productos y Servicios
   
    *Pago de productos y/o servicios
        * FK -> Numero de cliente (Cliente)
        * Se le asigna Tarjeta (atributo)
            * Numero de Tarjeta (long) (aleatorio) [19 tamaño] Implementar con algoritmo de Luhn.
            * Fecha de vencimiento (Date)
            * numero CVC (short) [4 tamaño]
            * Cargo = sumatoria de cada producto o servicios contratado. (double).
    
    *Creacion / Edicion / Eliminacion de productos
    *Creacion / Edicion / Eliminacion de servicios
    
    *Consulta de lista de productos disponibles:
        *Nombre
        *ID de producto
        Orden:
            *0-9 (Ascendente por ID de producto)
            *9-0 (Descendente por ID de producto)
            *A-Z (Ascendente por Nombre de producto)
            *Z-A (Descendente por Nombre de producto)
            
    *Consulta de Servicios disponibles:
        Servicios predeterminados:
            //Hereda de Servicios (global)
            
    * Registro de ventas pasadas
        Orden:
            *A-Z (Ascendente por Nombre de producto o servicio)
            *Z-A (Descendente por Nombre de producto o servicio)

6. Administracion de Adopcion de Mascotas
    *Coleccion de mascotas disponibles para adopcion
        Metodos:
        - Elegir mascota para adopcion
            - Verificar vacunas aplicadas
                if Vacunas suministradas esta vacio, entonces:
                Lanzar excepcion "La mascota no tiene vacunas suministradas y no es apta para su adopcion."
                regresar al menu de seleccion de mascotas disponibles
        
    *Registro de Adopciones hechas
        Se guarda:
        * Llave (cliente)
        * Valor (mascota) (Este se transfiere de la Coleccion de Mascotas).
        Orden:
            *0-9 (Ascendente por llave de cliente)
            *9-0 (Descendente por llave de cliente)
            *A-Z (Ascendente por nombre de mascota)
            *Z-A (Descendente por nombre de mascota)
    *Consulta de Mascotas devueltas
        Atributos:
        * Lesiones en la mascota (boolean)
            Metodos:
            - Cargo por maltrato
            - Sugerir llamada a la Brigada de Vigilancia Animal (BVA) - 55 5208 9898

7. Administracion de Sucursales
    *A/E/B de Sucursal
        //Informacion que va a contener cada sucursal
        *Nombre de sucursal
        *Codigo de sucursal /PK
        *Zona de sucursal
    *Consulta de sucursales
```
