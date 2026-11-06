# 🎓 Backoffice Académico - ITEC N°1

Sistema de gestión académica desarrollado en Java para el Instituto Tecnológico N°1.

El objetivo del proyecto es centralizar la administración académica, permitiendo gestionar alumnos, inscripciones a comisiones, exámenes, asistencias y calificaciones desde una aplicación de consola, con datos precargados y persistencia en memoria.

## Documentación

La documentación funcional y de análisis (alcance, actores, reglas, 24 historias con implementación en código y 5 de backlog, criterios de aceptación, DFD 0 y DFD 1 en Gane-Sarson, diccionario de datos, trazabilidad, modelo de dominio y manual de uso) está en [`docs/`](docs/README.md).

> El manual y la trazabilidad marcan las limitaciones reales del código: no hay persistencia, no hay autenticación y la nota no guarda una referencia directa al alumno.

## Objetivos

- Gestionar alumnos e inscripciones a comisiones.
- Registrar y administrar exámenes.
- Registrar asistencias de alumnos.
- Registrar notas y consultar calificaciones.
- Consultar carreras, materias, planes de estudio, profesores, comisiones y alumnos.
- Generar información para el seguimiento académico dentro del alcance del MVP.

## Funcionalidades principales

### Gestión de alumnos e inscripciones

- Alta de alumnos con validación de nombre, apellido y DNI único.
- Búsqueda por DNI, nombre o apellido y listado completo.
- Inscripción de alumnos en una comisión real, con control de duplicados y superposición horaria.
- Consulta de las comisiones en las que está inscripto un alumno.

### Gestión de exámenes

- Alta, listado, búsqueda, modificación y baja de exámenes.
- Registro de fecha, título, descripción obligatoria y comisión.
- Tipos de evaluación disponibles: Parcial, Recuperatorio y Final.

### Gestión de asistencias

- Registro de asistencia del día por documento de alumno (presente o ausente).
- Listado, búsqueda, modificación y baja de asistencias.
- Validación de duplicados por documento y fecha, y de fechas futuras al modificar.

### Gestión de calificaciones

- Registro de notas asociadas a un examen y a un alumno inscripto en la comisión.
- Validación del rango de notas de 1 a 10, con observación opcional.
- Listado, búsqueda, modificación y baja de notas.

### Consultas

- Carreras, materias, profesores, planes de estudio, comisiones, comisiones por profesor y alumnos.
- Información de la estructura académica precargada al iniciar la aplicación.

## Fuera de alcance del MVP

Las siguientes funciones están documentadas como backlog y **no están implementadas** en el código actual. El detalle está en [`docs/00-alcance.md`](docs/00-alcance.md).

- Cálculo de regularidad y estado de cursada.
- Cálculo de promedio académico.
- Validación de correlatividades entre materias.
- Persistencia en base de datos o archivos y script SQL.
- Inicio de sesión, roles y permisos.
- Portal o consultas para alumnos.
- Reportes exportables PDF/Excel/CSV y emisión de constancias.
- Actas de cierre, auditoría de operaciones y justificación de inasistencias con estados.
- Interfaz web, escritorio o API.
- Notificaciones por email.

## Cómo ejecutar

### Requisitos

- JDK 17 o superior.
- Maven 3.9 o superior (opcional si se compila con `javac`).
- Consola o terminal integrada del IDE.

### Compilar y ejecutar

Con Maven, desde la raíz del proyecto:

```bash
mvn -q compile
java -cp target/classes Main
```

Sin Maven:

```bash
javac -encoding UTF-8 -d target/classes $(find src/main/java -name "*.java")
java -cp target/classes Main
```

En PowerShell, el comando equivalente para compilar es:

```powershell
javac -encoding UTF-8 -d target\classes (Get-ChildItem -Recurse src\main\java -Filter *.java).FullName
java -cp target\classes Main
```

### Pruebas automatizadas

```bash
mvn test
```

Se ejecutan 120 casos de JUnit 5.

### Codificación de la consola

Algunos archivos fuente tienen codificación mixta y los acentos pueden verse como `Opci��n` o `Programaci?n`. En Windows se puede reducir el problema con:

```powershell
chcp 65001
java "-Dfile.encoding=UTF-8" -cp target\classes Main
```

Aun así, la codificación de la entrada puede alterar los acentos. La inscripción, los exámenes y las notas se eligen por número desde listas, así que no es necesario tipear códigos con tilde.

## Modelo de Dominio

El sistema se basa en las siguientes entidades principales:

- Alumno
- AlumnoInscripto
- Carrera
- PlanEstudio
- Materia
- ComisionMateria
- Profesor
- Cuatrimestre
- HorarioClase
- ModuloHorario
- Asistencia
- AsistenciaAlumno
- Examen
- Nota
- TipoEvaluacion (enumeración)
- EstadoCursada (enumeración definida, sin uso en el menú)

## Tecnologías

- Java 17
- Maven
- JUnit 5
- IntelliJ IDEA
- Git
- GitHub

## Conceptos Aplicados

- Programación Orientada a Objetos (POO)
- UML
- Encapsulamiento
- Composición y Agregación
- Inyección de Dependencias por Constructor
- Arquitectura en Capas
- Colecciones (`ArrayList`)
- Pruebas automatizadas con JUnit 5
- Trabajo colaborativo con Git Flow

## Estructura del Proyecto

```text
src/
├── main/java/ar/edu/itec/
│   ├── model/
│   ├── repository/
│   ├── service/
│   ├── controller/
│   ├── view/
│   └── enums/
└── test/java/ar/edu/itec/
    ├── model/
    ├── repository/
    ├── service/
    └── controller/
docs/
```

## Equipo de Desarrollo

- Lopez Enrique Alejandro
- Almada Axel
- Giorgi Lucas
- Rios Leonardo

## Estado del Proyecto

MVP por consola, persistencia en memoria.
