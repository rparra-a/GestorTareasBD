## PROYECTO TAREA 6 : API DE GESTION DE TAREAS (To-Do List)

### Desarrollado por Valentina Cartagena , Romina Parra y Bernarda Rodríguez.
### Crea una API REST con Spring Boot que permita crear, leer,actualizar y eliminar tareas.

El repositorio de GitHub GestorTareas.Java contiene un Sistema de Gestión de tareas simple desarrollado en Java, utilizando los principios de la Programación Orientada a Objetos (POO) con herencia, polimorfismo y manejo de excepciones personalizadas, aplicar el concepto
CRUD. 

### Objetivos: 
Crear una API RESTfull completa con Spring Boot que implemente las operaciones CRUD para la gestión de tareas.  
Usar Postman como plataforma de desarrollo y pruebas de APIs (Verbos y códigos HTTP) enviar solicitudes (requests) a una API,  ver, analizar y validar las respuestas (responses).


📌 Requisitos:



con id (autogenerado), título, descripción y
estado (pendiente, en progreso, completada).
Listar todas las tareas.
Obtener una tarea por ID.
Actualizar una tarea (título, descripción y/o estado).
Eliminar una tarea por ID.


Objetivo: Crear una API RESTful completa con Spring Boot que implemente las operaciones CRUD para la gestión de tareas.

Tecnología Clave: Spring Boot.

Requisitos Funcionales:

Modelo de Tarea: Debe incluir los siguientes campos:

id (autogenerado).

título.

descripción.

estado (con posibles valores: "pendiente", "en progreso", "completada").

Implementación CRUD: La API debe permitir:

Crear una nueva tarea.

Listar todas las tareas.

Obtener una tarea específica por su id.

Actualizar cualquiera de los campos de una tarea existente.

Eliminar una tarea por su id.

Endpoints Requeridos:

Método	Endpoint	Descripción
POST	/tareas	Crea una nueva tarea.
GET	/tareas	Lista todas las tareas.
GET	/tareas/{id}	Obtiene una tarea por su ID.
PUT	/tareas/{id}	Actualiza completamente la tarea con el ID especificado.
DELETE	/tareas/{id}	Elimina una tarea por su ID.

Exportar a Hojas de cálculo
Conceptos Practicados:

Desarrollo de APIs REST con Spring Boot.

Manejo de Verbos y Códigos HTTP (POST 201, GET 200, PUT 200/204, DELETE 200/204).

Aplicación del concepto CRUD (Create, Read, Update, Delete).

Criterios de Finalización:
El código para el Ejercicio Individual está completo y pasa las pruebas de lógica.

La API REST de To-Do List está implementada en Spring Boot.

Todos los Endpoints Requeridos funcionan correctamente y devuelven los códigos HTTP apropiados.



## RECURSOS TECNOLOGICOS:

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

Plataforma de desarrollo y pruebas de APIs (Application Programming Interfaces) actuar como un "cliente" para tu API, permitiendo a desarrolladores y testers interactuar con los endpoints sin necesidad de construir una interfaz de usuario compleja o escribir mucho código inicial.

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
