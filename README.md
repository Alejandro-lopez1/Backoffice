# 🎓 Backoffice Académico - ITEC N°1

Sistema de gestión académica desarrollado en Java para el Instituto Tecnológico N°1.

El objetivo del proyecto es centralizar la administración académica, permitiendo gestionar alumnos, profesores, materias, comisiones, asistencias y calificaciones, aplicando reglas de negocio propias del ámbito educativo.

## Objetivos

- Gestionar alumnos y carreras.
- Administrar planes de estudio y materias.
- Registrar asistencias de alumnos.
- Registrar exámenes y calificaciones.
- Calcular regularidad académica.
- Gestionar inscripciones a comisiones.
- Aplicar correlatividades entre materias.
- Generar información para el seguimiento académico.

## Funcionalidades principales

### Gestión de Asistencias

- Registro de asistencia por alumno.
- Registro de presentes y ausentes.
- Consulta de historial de asistencias.
- Cálculo automático de porcentaje de asistencia.
- Determinación de condición regular/no regular.

### Gestión de Calificaciones

- Registro de exámenes.
- Registro de notas.
- Gestión de distintos tipos de evaluación:
  - Trabajo Práctico
  - Parcial
  - Recuperatorio
  - Final
- Cálculo de promedio académico.

### Gestión Académica

- Administración de carreras.
- Administración de planes de estudio.
- Administración de materias.
- Gestión de comisiones.
- Gestión de profesores.
- Inscripción de alumnos.

## Modelo de Dominio

El sistema se basa en las siguientes entidades principales:

- Alumno
- AlumnoCarrera
- Carrera
- PlanEstudio
- Materia
- ComisionMateria
- Profesor
- HorarioClase
- ModuloHorario
- Asistencia
- Examen
- Nota

## Tecnologías

- Java
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
- Trabajo colaborativo con Git Flow

## Estructura del Proyecto

```text
src/
├── model/
├── service/
├── repository/
├── controller/
└── Main.java
```

## Equipo de Desarrollo

- Alejandro XXXXX
- Integrante 2
- Integrante 3

## Estado del Proyecto

🚧 En desarrollo
