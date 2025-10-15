## PROYECTO TAREA 7 : API DE GESTION DE TAREAS CON PERSISTENCIA (To-Do List)

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

* **Lenguaje:** Java
* **Framework:** Spring Boot
* **Persistencia:** Spring Data JPA
* **Base de Datos:** PostgreSQL (PgAdmin)


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
