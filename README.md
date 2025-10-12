## PROYECTO TAREA 6 : API DE GESTION DE TAREAS (To-Do List)

### Desarrollado por Valentina Cartagena , Romina Parra y Bernarda Rodríguez.
### Crea una API REST con Spring Boot que permita crear, leer,actualizar y eliminar tareas.

El repositorio de GitHub GestorTareas.Java contiene un Sistema de Gestión de tareas simple desarrollado en Java, utilizando los principios de la Programación Orientada a Objetos (POO) con herencia, polimorfismo y manejo de excepciones personalizadas, aplicar el concepto
CRUD. 

### Objetivos:  
Crear una API RESTfull completa con Spring Boot que implemente las operaciones CRUD para la gestión de tareas.  
Usar Postman como plataforma de desarrollo y pruebas de APIs (Verbos y códigos HTTP) enviar solicitudes (requests) a una API,  ver, analizar y validar las respuestas (responses).


📌 Requisitos:

Estructurar la arquitectura en capas de la aplicacion JAVA en distintos modulos para mejorar la mantenibilidad y escalabilidad.  
Tener en cuenta el control de excepciones y manejo de estas, finalmente hacer uso de la API mediante Postman

* Model
* Controller 
* Service
* Excepction
* API Postman 

#### 1) Model  

 Contiene los siguientes campos:  
* id (autogenerado).
* título.
* descripción.
* estado (con posibles valores: "pendiente", "en progreso", "completada").

#### 2) Controller

Implementación CRUD.  
La API debe permitir:

* Crear una nueva tarea.
* Listar todas las tareas.
* Obtener una tarea específica por su id.
* Actualizar cualquiera de los campos de una tarea existente.
* Eliminar una tarea por su id.

Extras : Eliminar una tarea si su estado esta completa, obtener una lista de tareas filtradas ,según su estado.

#### 3) Service 

Endpoints Requeridos:  

Método	Endpoint	Descripción

* POST	/api/tareas	Crea una nueva tarea.    
* GET	/api/tareas	Lista todas las tareas.    
* GET	/api/tareas/{id}	Obtiene una tarea por su ID.    
* PUT	/api/tareas/{id}	Actualiza completamente la tarea con el ID especificado.    
* DELETE	/api/tareas/{id}	Elimina una tarea por su ID.    
__Extras:__    
* GET /api/tareas?estado=pendiente - Lista solo las tareas con estado "pendiente".    
* GET /api/tareas?titulo=compra - Lista tareas cuyo título contenga "compra".  
* GET /api/tareas?estado=en%20progreso&titulo=reporte - Lista tareas con estado "en progreso" y título que contenga "reporte".  


#### 4) Excepction 

Se crearon dos Excepción Personalizada para Manejar un error especifco para el método EliminarTarea ya que podria haber dos fallas:  
* Tarea no encontrada: La tarea con ese ID no existe. (RecursoNoEncontradoException)
* Estado incorrecto: La tarea existe, pero su estado no es "completada". (EstadoInvalidoException.java)

Además para un manejo de errores más centralizado y personalizado, se creo GlobalExceptionHandler con la anotación @ControllerAdvice. 
Esto reemplaza los ResponseEntity.notFound().build() en el controlador.
Agrega un manejador en el ControllerAdvice para capturar la nueva excepción y devolver un código de estado 409 Conflict.

### Conceptos Practicados:

* Desarrollo de APIs REST con Spring Boot.
* Manejo de Verbos y Códigos HTTP (POST 201, GET 200, PUT 200/204, DELETE 200/204).
* Aplicación del concepto CRUD (Create, Read, Update, Delete).


### Criterios de Finalización:
* El código para el Ejercicio está completo y pasa las pruebas de lógica.
* La API REST de To-Do List está implementada en Spring Boot.
* Todos los Endpoints Requeridos funcionan correctamente y devuelven los códigos HTTP apropiados.

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
