Sistema de reserva de turnos
---
> Trabajo integrador

**Objetivo:**

El objetivo de esta actividad es poder integrar los conocimientos vistos hasta el momento. 

* Nivel de complejidad: Intermedia 🔥🔥


**Desafío:**

Se desea implementar un sistema que permita administrar la reserva de turnos para una clínica odontológica. Esta necesita informatizar su operatoria. Por lo cual, te solicitan un sistema que debe cumplir con los siguientes requerimientos:

* **Administración de datos de odontólogos:** listar, agregar, modificar y eliminar odontólogos. Registrar apellido, nombre y matrícula de los mismos.

* **Administración de datos de los pacientes:** listar, agregar, modificar y eliminar pacientes. Al registrar un paciente los datos que se le solicitan son:
    * Apellido
    * Nombre
    * DNI
    * Fecha de alta 
    * Domicilio 
        * Calle
        * Número
        * Localidad
        * Provincia

    Además, le agregaremos un ID autoincremental tanto a los pacientes como a los domicilios.

* **Registrar turno**: se tiene que poder permitir asignar a un paciente un turno con un odontólogo a una determinada fecha y hora. 

* **Login**: validar el ingreso al sistema mediante un login con usuario y password. Se debe permitir a cualquier usuario logueado (ROLE_USER) registrar un turno, pero solo a quienes tengan un rol de administración (ROLE_ADMIN) poder gestionar odontólogos y pacientes. Un usuario podrá tener un único rol y los mismos se ingresarán directamente en la base de datos.

**Requerimientos técnicos**:

La aplicación debe ser desarrollada en capas:
* **Capa de entidades de negocio:** son las clases Java de nuestro negocio modelado a través del paradigma orientado a objetos.

* **Capa de acceso a datos (Repository)**: son las clases que se encargarán de acceder a la base de datos.

* **Capa de datos (base de datos)**: es la base de datos de nuestro sistema modelado a través de un modelo entidad-relación. Utilizaremos la base H2 por su practicidad. 
* **Capa de negocio**: son las clases service que se encargan de desacoplar el acceso a datos de la vista.

* **Capa de presentación**: son las pantallas web que tendremos que desarrollar utilizando el framework de Spring Boot MVC con los controladores y alguna de estas dos opciones: HTML+JavaScript para la vista.


Es importante realizar el manejo de excepciones logueando cualquier excepción que se pueda generar y la realización de test unitarios para garantizar la calidad de los desarrollos.


---


Se pide utilizar H2 como base de datos, aplicar el patrón DAO y testear con JUnit. Tener en cuenta que el modelado de clases debe contar con al menos dos clases: **PACIENTE** y **DOMICILIO**, con la consideración de que los pacientes podrán tener solo un domicilio.

Crear solo una clase de servicio, PacienteService, y crear por cada entidad un DAO, es decir, DomicilioDAOH2 y PacienteDAOH2. Al guardar y buscar un paciente en PacienteDAOH2 deberás invocar el guardar y buscar de DomicilioDAOH2.

**Tests de aceptación**

Guardar en la base de datos dos o más pacientes con sus respectivos domicilios antes de ejecutar los tests.

Utilizando la capa de servicio:

Agregar un paciente con domicilio y buscarlo en la base de datos por ID.

Eliminar el paciente anteriormente creado y luego buscarlo, debe tener también el domicilio.

Buscar e imprimir en consola todos los pacientes con sus domicilios, serán los pacientes que agregamos antes de ejecutar el primer test.






**Realizado por :** Sammy Gigi Cantoral Montejo (sgcm14)

<img src ="https://raw.githubusercontent.com/sgcm14/sgcm14/main/sammy.jpg" width="200">
