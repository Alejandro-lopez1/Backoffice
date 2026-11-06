# 01 — Actores y glosario

## 1. Actores

| ID | Actor | Descripción | Acceso actual |
|---|---|---|---|
| E1 | Administrativo / Secretaría | Personal que registra alumnos, inscripciones y consulta información académica | Menú completo (sin login) |
| E2 | Docente | Responsable de registrar asistencias, exámenes y notas de sus comisiones | Mismos menús que E1 (sin login) |
| E3 | Alumno | Consulta su situación académica, notas y asistencias | **Planificado**: no existe módulo ni autenticación |

> En la implementación actual los tres actores comparten el mismo menú de consola. La separación es conceptual y sirve para el análisis de flujos y para el backlog.

## 2. Matriz de funciones por actor

| Función | E1 Administrativo | E2 Docente | E3 Alumno |
|---|---|---|---|
| Registrar/buscar/listar alumnos | ✅ | — | — |
| Inscribir alumno en comisión | ✅ | — | — |
| Ver comisiones de un alumno | ✅ | ✅ | 📋 (propias) |
| Registrar/modificar/eliminar exámenes | ✅ | ✅ | — |
| Registrar/modificar/eliminar asistencias | ✅ | ✅ | — |
| Registrar/modificar/eliminar notas | ✅ | ✅ | — |
| Consultar carreras, materias, profesores, planes y comisiones | ✅ | ✅ | — |
| Consultar situación académica propia | — | — | 📋 |

## 3. Glosario

| Término | Definición |
|---|---|
| **Alumno** | Persona registrada con nombre, apellido, DNI, email y teléfono. El DNI es obligatorio y único. |
| **DNI** | Documento Nacional de Identidad; identifica unívocamente al alumno en el sistema. |
| **Legajo** | Número interno de legajo. **No está implementado** en el modelo actual (aparece en pruebas antiguas). |
| **Carrera** | Programa de estudios con código y nombre (por ejemplo, `TSP`). |
| **Materia** | Asignatura con nombre (por ejemplo, `Programación I`). |
| **Plan de estudio** | Vinculación entre una carrera, un año y una materia. |
| **Comisión** | Instancia de cursada de una materia, con código, profesor, cuatrimestre y horarios. |
| **Cuatrimestre** | Período lectivo definido por año, número (1 o 2) y fechas de inicio/fin. |
| **Horario de clase** | Día de la semana con uno o más módulos horarios. |
| **Módulo horario** | Franja con número, hora de inicio y hora de fin. |
| **Profesor** | Docente con nombre, apellido, email y teléfono. |
| **Inscripción** | Relación entre un alumno y una comisión (`AlumnoInscripto`), con fecha de inscripción, asistencias y notas. |
| **Asistencia** | Registro de presencia o ausencia de un alumno en una fecha. No puede duplicarse para el mismo alumno y fecha. |
| **Examen** | Evaluación de una comisión, con nombre, descripción, fecha y tipo. |
| **Tipo de evaluación** | `PARCIAL`, `RECUPERATORIO` o `FINAL`. |
| **Nota** | Valor numérico entre 1 y 10 asociado a un examen, con observación y fecha de registro. |
| **Regularidad** | Condición académica calculable a partir del porcentaje de asistencia (`AlumnoInscripto.esRegular`). No se expone en el menú. |
| **Estado de cursada** | `PROMOCIONADO`, `REGULAR` o `DESAPROBADO`. El enum existe, pero no se utiliza. |
| **Promedio** | Media de notas. No implementado. |
| **Correlatividad** | Requisito de materias aprobadas para cursar otra. No implementado. |
| **Repositorio** | Clase en memoria que guarda y busca entidades (`AlumnoRepository`, `NotaRepository`, etc.). |
| **Almacén (DFD)** | Representación lógica de un conjunto de datos persistidos; en este sistema equivale a un repositorio en memoria. |
| **Actor (DFD)** | Entidad externa que envía o recibe datos del sistema. |
| **HU** | Historia de usuario. |
| **CA** | Criterio de aceptación. |
| **MoSCoW** | Técnica de priorización: Must, Should, Could, Won't. |

## 4. Relación entre actores y DFD

| Actor | Entidad DFD | Procesos principales |
|---|---|---|
| Administrativo | E1 | 1.0, 2.0, 6.0 |
| Docente | E2 | 3.0, 4.0, 5.0 |
| Alumno | E3 | 7.0 (planificado) |

Ver [DFD 0](05-dfd-0-contexto.md) y [DFD 1](06-dfd-1-sistema.md).
