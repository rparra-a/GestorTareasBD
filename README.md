### PROYECTO TAREA 7 : API DE GESTION DE TAREAS CON PERSISTENCIA (To-Do List)

### Desarrollado por Valentina Cartagena , Romina Parra y Bernarda Rodríguez.

### Crea una API REST con Spring Boot que permita crear, leer,actualizar y eliminar tareas de una base de datos.

Este repositorio contiene el desarrollo de una API Restful para la gestión de tareas, utilizando **Java** y el framework **Spring Boot** para la implementación del backend y la persistencia de datos en JPA.

Este es el proyecto central que implementa un CRUD (Crear, Leer, Actualizar, Eliminar) completo para la gestión de tareas, utilizando una base de datos.

#### 🎯 Objetivos de Práctica

* **Spring Boot:** Uso del framework para construir aplicaciones REST.
* **Verbos y Códigos HTTP:** Implementación correcta de métodos HTTP (POST, GET, PUT, DELETE) y sus códigos de respuesta.
* **Persistencia de Datos con JPA:** Mapeo de entidades y uso de Spring Data JPA para interactuar con la base de datos.

#### 📐 Arquitectura de Capas del Proyecto (Spring Boot)  

El proyecto sigue una arquitectura jerárquica y modularizada, donde cada capa tiene una responsabilidad única y bien definida.

#### 1. Capa de Presentación / Controladores (Controller)
Propósito: Es la capa de entrada de la aplicación. Maneja las peticiones HTTP entrantes (entrantes) y devuelve las respuestas HTTP (salientes). 

__Responsabilidad:__  
* Mapear las URLs (endpoints) a métodos específicos de Java (ej: GET /tareas).
* Recibir los datos de la petición (JSON) y validarlos a nivel básico.
* Delegar la lógica de negocio a la capa de Servicio.
* Formatear la respuesta (ej: devolver un objeto Tarea con código 201 Created).
* Clases anotadas con @RestController y métodos con @GetMapping, @PostMapping, @PutMapping, @DeleteMapping.
* Entidades: Utiliza DTOs (Data Transfer Objects) para la comunicación.

#### 2. Capa de Lógica de Negocio / Servicio (Service)
Propósito: Contiene toda la lógica de negocio de la aplicación y coordina las acciones.  

__Responsabilidad:__ 
* Implementar las reglas de negocio (ej: verificar que una tarea no se pueda eliminar si no está "Completada"
* Manejar las transacciones.
* Actuar como intermediario entre el Controlador y la capa de Persistencia.
* Llamar a los métodos de la capa de Repositorio para manipular los datos.
* Clases anotadas con @Service y a menudo @Transactional.

#### 3) Capa de Acceso a Datos / Repositorio (Repository)
Propósito: Es la capa responsable de comunicarse directamente con la base de datos.  

__Responsabilidad:__ 
* Ejecutar las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) en las tablas.
* Mapear las filas de la base de datos a objetos Java (Entidades) y viceversa.
* Es responsable de traducir las operaciones del Service a consultas SQL (a través de Hibernate)
* Gestionar la comunicación de bajo nivel (conexiones, sesiones, etc.).
* Interfaces anotadas con @Repository que extienden de JpaRepository (Spring Data JPA).

#### 4)  Capa de Persistencia (Database) Model (Modelo / Entidad)
Propósito: El almacén físico de los datos.  

__Responsabilidad:__ 
* Define las clases que serán mapeadas a las tablas de la BD (ej: Tarea.java)
* Incluye anotaciones de JPA (@Entity, @Id, @Table, @Column)
* Almacenar y recuperar los datos según las instrucciones SQL generadas por la capa de Repositorio (JPA/Hibernate).
* Representa el estado actual de un registro en la base de datos.<
* Tecnologías: PostgreSQL.

#### 5)  Capa de Comunicación DTO (Data Transfer Object)
Propósito: Define el formato de intercambio de datos.

__Responsabilidad:__ 
* Define las estructuras de datos que se usan para enviar y recibir información a través de la API (JSON).
* Su objetivo es evitar exponer la Entidad (Model) directamente.
  
####  🔄 Flujo de Datos 
* El flujo de trabajo en una operación típica (por ejemplo, crear una tarea) es estrictamente descendente y ascendente:
* El cliente envía datos en formato DTO (JSON) al Controller.
* El Controller valida la entrada (si el DTO es válido) y lo pasa al Service.
* El Service aplica la lógica, convierte el DTO a un objeto Model y llama al método save() del Repository.
* El Repository utiliza el Model para persistir los datos en la base de datos.
* La respuesta (el objeto Model persistido) regresa al Service.
* El Service convierte el Model de regreso a un DTO de respuesta.
* El Controller envía el DTO de respuesta al cliente.
* El proceso para verificar la base de datos PostgreSQL después de ejecutar las pruebas con Postman se realiza utilizando la herramienta de administración gráfica pgAdmin.

### Configuración del Proyecto (pom.xml summary)
El archivo pom.xml utiliza la versión 3.5.6 de spring-boot-starter-parent y está configurado para usar Java 21. 
Las dependencias clave son:
* spring-boot-starter-web: Para la construcción de aplicaciones web y RESTful.
* spring-boot-starter-data-jpa: Para la persistencia de datos usando JPA y Hibernate.
* postgresql: Driver para la conexión a la base de datos PostgreSQL.
* spring-boot-starter-test: Para pruebas unitarias y de integración.
-------------------------------------------------------------------------------------------
## 🛠️ Entidades Principales
1. Estado: Representa el estado de una tarea (ej. "pendiente", "en progreso", "completada").

* id (int): Clave primaria autoincremental.

* nombre (String): Nombre del estado (no puede ser nulo).

2. Tarea: Representa una tarea individual.

* id (int): Clave primaria autoincremental.

* titulo (String): Título de la tarea (no puede ser nulo).

* descripcion (String): Descripción de la tarea.

* estado (Estado): Relación ManyToOne con la entidad Estado.

🗺️ Endpoints de la API _La API expone los siguientes endpoints bajo la ruta base /api/tareas:

Manejo de Errores:
* 404 Not Found: Lanzado por RecursoNoEncontradoException si una Tarea no existe (ej. al intentar obtener, actualizar o eliminar una ID no válida).

* 400 Bad Request: Lanzado por EstadoInvalidoException si se intenta eliminar una tarea que no está en estado 'completada'.

----------------------------------------------------------------------------------------------
## 🛠️ Script SQL Entidades Principales

###  📝 Script SQL para la tabla Estado
CREATE TABLE estado (
    -- Clave primaria autoincremental (SERIAL en PostgreSQL)
    id SERIAL PRIMARY KEY,
    
    -- Nombre del estado (ej. 'pendiente', 'en progreso', 'completada'). No puede ser nulo.
    nombre VARCHAR(50) NOT NULL UNIQUE);

### 📝 Script SQL para la tabla Tarea
CREATE TABLE tarea (
    -- Clave primaria autoincremental
    id SERIAL PRIMARY KEY,
    
    -- Título de la tarea. No puede ser nulo.
    titulo VARCHAR(255) NOT NULL,
    
    -- Descripción de la tarea (puede ser nula o tener un tamaño mayor)
    descripcion TEXT,
    
    -- Columna para la relación ManyToOne con Estado.
    -- La columna se llama 'estado_id' por convención y almacena la clave foránea.
    estado_id INT,
    
    -- Definición de la clave foránea que relaciona esta tabla con la tabla 'estado'
    CONSTRAINT fk_estado
        FOREIGN KEY (estado_id)
        REFERENCES estado (id)
        -- ON DELETE RESTRICT o SET NULL se pueden añadir, se utiliza el default (RESTRICT) si no se especifica.
        -- Si la aplicación maneja la eliminación de estados, se podría considerar ON DELETE CASCADE o SET NULL.
        -- Dada la naturaleza de los estados, asumimos que son fijos y no se borran, o se gestiona la integridad desde la capa de servicio.);

### 📝 Scripts de Inserción de Estados Iniciales (estado) 🚦
Primero, necesitamos asegurarnos de que los posibles estados para las tareas existan en la tabla estado.

-- Inserción de los tres estados posibles para las tareas:
INSERT INTO estado (nombre) VALUES 
('pendiente'), 
('en progreso'), 
('completada');

-- NOTA: Asumiendo que 'pendiente' tiene ID 1, 'en progreso' tiene ID 2 y 'completada' tiene ID 3 
-- si la columna 'id' es SERIAL y se insertan en este orden.

### 📝 Scripts de Inserción de Tareas Iniciales (tarea)
Estas inserciones coinciden con las cuatro tareas iniciales descritas en la sección "Paso 1: Creación de Datos de Prueba (POST)" del README.md. Utilizamos los valores del ID de los estados insertados anteriormente (asumiendo IDs secuenciales predeterminados: 1 para 'pendiente', 2 para 'en progreso' y 3 para 'completada').

ID	Tarea formato JSON	Estado	Estado ID (FK)
1	{"titulo": "Comprar leche", ...}	Pendiente	1
2	{"titulo": "Hacer reporte semanal", ...}	En Progreso	2
3	{"titulo": "Pagar factura", ...}	Completada	3
4	{"titulo": "Reporte final", ...}	Pendiente	1

INSERT INTO tarea (titulo, descripcion, estado_id) VALUES 
('Comprar leche', 'Leche entera', 1),                -- Estado ID 1: pendiente
('Hacer reporte semanal', 'Reporte de ventas', 2),    -- Estado ID 2: en progreso
('Pagar factura', 'Internet y luz', 3),               -- Estado ID 3: completada
('Reporte final', 'Entregar al jefe', 1);             -- Estado ID 1: pendiente
___________________________________________________________________________________________

## 🚀 Configuración y Ejecución
Requisitos: 
Java Development Kit (JDK) 21
[Maven/Gradle]

Una base de datos configurada (ej. PostgreSQL, MySQL, o H2 para desarrollo).

1. Clonar el Repositorio
2. Configurar la Base de Datos
Asegúrate de configurar los parámetros de conexión a la base de datos en el archivo de configuración de Spring Boot (ej. application.properties o application.yml).

Nota: Debes precargar los estados iniciales ("pendiente", "en progreso", "completada") en la tabla estados para que la aplicación funcione correctamente.

3. Ejecutar la Aplicación
Si usas Maven:
La aplicación se iniciará por defecto en http://localhost:8080.
------------------------------------------------------------------------------------------
### Ruta de Prueba (Ejemplos de Solicitudes HTTP)

Haremos uso de __Postman__ para enviar las peticiones.

__Paso 0: Ejecutar la Aplicación__

Asegúrate de que tu aplicación Spring Boot está corriendo en la IDE (usualmente, ejecutando la clase Application.java).

__Paso 1: Creación de Datos de Prueba (POST)__

Creamos algunas tareas con diferentes estados para probar el filtrado y la eliminación.

| ID | Tarea formato JSON | Estado | Peticion |
| :---: | :--- | :---: | ---: |
| 1 | {"titulo": "Comprar leche", "descripcion": "Leche entera", "estado": "pendiente"} |❌ Pendiente| 	POST /api/tareas |
| 2 | {"titulo": "Hacer reporte semanal", "descripcion": "Reporte de ventas", "estado": "en progreso"} | ⏳ En Progreso | POST /api/tareas |
| 3 | {"titulo": "Pagar factura", "descripcion": "Internet y luz", "estado": "completada"} | ✔️ Completada | POST /api/tareas |
| 4 | {"titulo": "Reporte final", "descripcion": "Entregar al jefe", "estado": "pendiente"} | ❌ Pendiente | POST/api/tareas |

__Paso 2: Probar el Filtrado (GET con Query Params)__

Ahora probamos los filtros que agregamos al método obtenerTareasId().

| Funcionalidad| Petición (Ruta Completa) | Resultado Esperado |
| :---: | :--- | :---: |
| Filtrar por Estado | GET /api/tareas?estado=pendiente | Tareas con ID 1 y 4 |
| Filtrar por Título| 	GET /api/tareas?titulo=reporte | Tareas con ID 2 y 4 |
| Filtrar por Ambos | GET /api/tareas?estado=pendiente&titulo=reporte | Tarea con ID 4 |
| Listar Todo | GET /api/tareas | Tareas con ID 1, 2, 3, 4 |


__Paso 3: Probar el Manejo de Errores (ControllerAdvice)__
	
Probamos los endpoints con IDs inválidos o estados incorrectos para ver la respuesta personalizada (JSON con código de error).	
	
__Caso A: Recurso No Encontrado (404)__

| Petición| Petición (Ruta Completa) | Resultado Esperado |
| :---: | :--- | :---: |
| Buscar ID 99 | GET /api/tareas/99 | 404 Not Found. Cuerpo JSON personalizado. {"status": 404,"message": "Tarea no encontrada con ID: 99", "details": "uri=/api/tareas/99"} |		
				
__Caso B: Estado de Tarea Inválido al Eliminar (409 Conflicto)__

Intentamos eliminar la Tarea 2 (Estado: en progreso). El servicio debe fallar y el controlador debe lanzar EstadoInvalidoException.

| Petición| Petición (Ruta Completa) | Resultado Esperado |
| :---: | :--- | :---: |
| Eliminar ID 2 | DELETE /api/tareas/2 | 409 Conflict. Cuerpo JSON personalizado con mensaje de error sobre el estado.  {"status": 409,"message": "No se puede eliminar. La tarea con ID: 2 debe estar en estado 'completada' para ser eliminada. Estado actual: en progreso", "details": "uri=/api/tareas/2"} |	
		
__Paso 4: Probar la Eliminación Correcta (DELETE con Estado completada)__

Intentamos eliminar la Tarea 3 (Estado: completada).

| Petición| Petición (Ruta Completa) | Resultado Esperado |
| :---: | :--- | :---: |
| Eliminar ID 3 | DELETE /api/tareas/3| 204 No Content (Eliminación exitosa). |	
		
__Paso 5 : Probar la Actualización de Tareas (PUT__

Usaremos el ID de las tareas creadas en el Paso 1 (asumiendo que las Tareas 1, 2, y 4 aún existen).

__Caso A: Actualización Completa (Cambiar Título, Descripción y Estado)__

Vamos a actualizar la Tarea con ID=1 (originalmente: "Comprar leche", estado: pendiente).

|  Petición | TPetición (Ruta Completa) | Cuerpo de la Petición (JSON) | Resultado Esperado |
| :---: | :--- | :---: | ---: |
| Actualizar ID 1 | PUT /api/tareas/1 | {"titulo": "Comprar pan y huevos", "descripcion": "Mercado", "estado": "completada"} | 200 OK. La Tarea 1 ahora tiene los nuevos valores. |		

__Caso B: Actualización Parcial (Solo cambiar el Estado)__

Vamos a actualizar la Tarea con ID=4 (originalmente: "Reporte final", estado: pendiente) para cambiarla a en progreso.

Nota: En el TareaService.java modificado, la lógica de actualización se ajustó para mantener el valor anterior si el campo en la petición de actualización es null o una cadena vacía ("").

|  Petición | TPetición (Ruta Completa) | Cuerpo de la Petición (JSON) | Resultado Esperado |
| :---: | :--- | :---: | ---: |
| Actualizar ID 4 | PUT /api/tareas/4| {"estado": "en progreso"} | 200 OK. El estado de la Tarea 4 cambia a en progreso. Título y descripción permanecen igual. |	

__Caso C: Manejo de Errores al Actualizar (404 Not Found)__

Intentamos actualizar una tarea que no existe (ID=99). El ControllerAdvice debe interceptar la RecursoNoEncontradoException.

|  Petición | TPetición (Ruta Completa) | Cuerpo de la Petición (JSON) | Resultado Esperado |
| :---: | :--- | :---: | ---: |
| Actualizar ID 99 | GET /api/tareas/99| {"estado": "pendiente"}| 404 Not Found. Cuerpo JSON personalizado.{"status": 404,"message": "Tarea no encontrada con ID: 99","details": "uri=/api/tareas/99"} |	

🛠️ Tecnologías Utilizadas

* **Lenguaje Java:** Lenguaje de programación principal
* **Framework Spring Boot :** Para construir la aplicación REST.
* **Persistencia Spring Data JPA:** Abstracción sobre JPA para el acceso a datos.
* **Hibernate:** Implementación de JPA para mapeo objeto-relacional.
* **Maven/Gradle:** Herramienta de construcción y gestión de dependencias.
* **Base de Datos PostgreSQL (PgAdmin):** Sistema de gestión de bases de datos relacional.
* **APIs (Interfaces de Programación de Aplicaciones) Postman:** Para enviar y probar las peticiones HTTP.

### RECURSOS TECNOLOGICOS:

__1) Spring Boot:__

El sitio web https://start.spring.io/ es una herramienta esencial conocida como Spring Initializr.  
Su propósito principal es generar rápidamente la estructura básica (el esqueleto o boilerplate) como asistente web agiliza la configuración inicial, permitiendo a los desarrolladores comenzar a escribir la lógica de negocio de su aplicación inmediatamente.

#### Funcionalidades Clave:
* Generación de Proyectos: Permite configurar y descargar un proyecto Spring Boot listo para ejecutar en cuestión de segundos.
* Selección de Dependencias (Starters): Es su característica más valiosa.
* Permite seleccionar visualmente las funcionalidades que necesita el proyecto (por ejemplo, Web, JPA para bases de datos, Security, Lombok, etc.).
* Spring Initializr se encarga de añadir las dependencias correctas al archivo de configuración de Maven (pom.xml) o Gradle.
* Configuración Básica: Permite definir:  
Tipo de Proyecto: Maven o Gradle.  
Lenguaje de Programación: Java, Kotlin o Groovy.  
Versión de Spring Boot y la versión de Java.  
Metadatos del proyecto: Nombre del grupo (Group), artefacto (Artifact) y el nombre del paquete.  
Integración con IDEs: los Entornos de Desarrollo Integrado (IDEs) populares como IntelliJ IDEA, Eclipse STS y Visual Studio Code tienen integración directa con Spring Initializr, permitiendo crear proyectos Spring Boot desde la interfaz del IDE.

__Spring Data JPA__ es un módulo (una parte) del proyecto Spring Data que tiene como objetivo simplificar y agilizar el desarrollo de aplicaciones que acceden a bases de datos relacionales, utilizando la especificación JPA (Java Persistence API).  En esencia, Spring Data JPA actúa como una capa de abstracción y automatización sobre JPA.

__2) Postman :__

El sitio web https://web.postman.co/home es una plataforma de desarrollo y pruebas de APIs (Application Programming Interfaces) actuar como un "cliente" para tu API, permitiendo a desarrolladores y testers interactuar con los endpoints sin necesidad de construir una interfaz de usuario compleja o escribir mucho código inicial.

#### Funcionalidades Clave:

* Envío de Solicitudes (Requests) HTTP/HTTPS: Permite enviar peticiones utilizando todos los métodos HTTP comunes como:  
GET (para obtener datos).  
POST (para crear datos).  
PUT/PATCH (para actualizar datos).  
DELETE (para eliminar datos).  

* Facilita la configuración de parámetros de la solicitud, encabezados (headers), datos de autenticación (tokens, claves) y el cuerpo de la solicitud (body), típicamente en formato JSON o XML.
* Pruebas (Testing) de API:
Permite verificar si una API funciona como se espera, enviando la solicitud y examinando la respuesta.  
Ofrece la posibilidad de escribir scripts de prueba automáticos (en JavaScript) para validar el código de estado HTTP (por ejemplo, 200 OK, 201 Created), la estructura de los datos de respuesta y el contenido de la respuesta.  

* Colecciones y Entornos (Collections and Environments):  
Colecciones: Permite agrupar y organizar solicitudes relacionadas en carpetas lógicas. Esto facilita la reutilización y el intercambio de flujos de trabajo de API completos.  
Entornos: Permite definir variables (como URLs base, tokens de autenticación o claves) que cambian según el contexto (desarrollo, prueba, producción). Esto simplifica el cambio entre diferentes configuraciones sin modificar las solicitudes.

__3) PostgreSQL (Postgres)__
PostgreSQL https://www.postgresql.org/ es un sistema de gestión de bases de datos relacional orientado a objetos (ORDBMS) de código abierto. Se le conoce por su solidez, fiabilidad y cumplimiento estricto del estándar SQL. A menudo se le considera una alternativa de código abierto a sistemas comerciales como Oracle o SQL Server, ofreciendo un amplio conjunto de características avanzadas.
#### Funcionalidades Clave:

* Orientado a Objetos y Relacional: Además de las características relacionales estándar, soporta herencia de tablas y tipos de datos definidos por el usuario, lo que le da una ventaja en el manejo de estructuras complejas.

* Concurrencia (MVCC): Utiliza un sistema llamado Control de Concurrencia Multiversión (MVCC), que permite que las operaciones de lectura y escritura se realicen simultáneamente sin necesidad de bloqueos, mejorando el rendimiento en entornos de alta concurrencia.

* Extensibilidad: Ofrece una gran variedad de tipos de datos nativos (incluyendo JSON, XML, direcciones IP, y figuras geométricas) y permite a los usuarios crear sus propias funciones y extensiones, siendo PostGIS (para datos geoespaciales) una de las más populares.

* Cumplimiento ACID: Garantiza la Atomicidad, Consistencia, Aislamiento y Durabilidad de las transacciones, lo cual es fundamental para la integridad de los datos.

__4) pgAdmin__
pgAdmin es la plataforma de administración y desarrollo de código abierto más popular y rica en funciones para PostgreSQL.
#### Funcionalidades Clave:
* Gestión de Bases de Datos: Permite crear, modificar y eliminar bases de datos, esquemas, tablas, índices, vistas y otros objetos de PostgreSQL.
+ Herramienta de Consulta (Query Tool): Ofrece un editor SQL avanzado para escribir, ejecutar y depurar consultas.
* Administración: Facilita la gestión de usuarios, roles, permisos y la realización de tareas administrativas como backups (copias de seguridad) y restauración.
* Monitoreo: Proporciona un dashboard (panel de control) para visualizar el estado del servidor, las conexiones activas y el rendimiento de las consultas.


