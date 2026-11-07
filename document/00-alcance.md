# 00 — Alcance

## 1. Objetivo

Centralizar la administración académica del ITEC N°1 en una aplicación de consola que permita gestionar alumnos, inscripciones a comisiones, exámenes, asistencias y notas, y consultar la estructura académica (carreras, materias, planes, profesores y comisiones).

El sistema está orientado a un uso administrativo interno: una persona opera el menú y registra o consulta la información. No es un portal para alumnos.

## 2. Contexto

| Aspecto | Situación actual |
|---|---|
| Tipo de aplicación | Consola (Java 17) |
| Persistencia | En memoria (`ArrayList` en repositorios) |
| Datos iniciales | Precargados en los repositorios (carreras, materias, profesores, comisiones, planes y 4 alumnos) |
| Autenticación | No existe; un único menú para todos los roles |
| Interfaz | Menús numerados por teclado |
| Pruebas | JUnit 5 (`src/test/java`), 170 casos ejecutados en 21 clases |

## 3. Alcance funcional

| Módulo | Incluido |
|---|---|
| Alumnos | Alta, búsqueda por DNI/nombre/apellido, listado |
| Inscripciones | Inscripción a una comisión real, control de duplicados y superposición horaria, consulta de comisiones por alumno |
| Exámenes | Alta, listado, búsqueda, modificación y baja |
| Asistencias | Registro del día, listado, búsqueda, modificación y baja |
| Notas | Registro (1 a 10), listado, búsqueda, modificación y baja |
| Consultas | Carreras, materias, profesores, planes de estudio, comisiones, comisiones por profesor y alumnos |
| Estructura académica | Datos de carrera, materia, plan, profesor, comisión, cuatrimestre y horarios disponibles para consulta |

## 4. Fuera de alcance (implementación actual)

| Función | Motivo |
|---|---|
| Persistencia en base de datos o archivos | Los repositorios son en memoria; los datos se pierden al cerrar |
| Inicio de sesión, roles y permisos | No implementado |
| Portal o consultas para alumnos | No implementado (actor E3 planificado) |
| Cálculo de regularidad y estado de cursada | `EstadoCursada` existe pero no se usa |
| Cálculo de promedio | No implementado |
| Validación de correlatividades | No implementado |
| Emisión de constancias | No implementado |
| Reportes exportables PDF/Excel/CSV | No implementado en el MVP |
| Actas de cierre | No implementado en el MVP |
| Auditoría de operaciones (AuditLog) | No implementado en el MVP |
| Justificación de inasistencias con tres estados (PRESENTE/AUSENTE/JUSTIFICADA) | No implementado en el MVP |
| Script SQL / base de datos | No implementado en el MVP |
| Interfaz web, escritorio o API | No implementado |
| Notificaciones por email | No implementado |

> El README del repositorio menciona regularidad, promedio y correlatividades como objetivos. En el código actual **no están implementados**; se documentan como backlog en [03 — Historias de usuario](03-historias-usuario.md).

## 5. Supuestos

1. El operador conoce el DNI del alumno y el código de la comisión.
2. Los datos maestros (carreras, materias, profesores, comisiones y planes) se cargan al iniciar y no se editan desde el menú.
3. La fecha de la asistencia registrada desde el menú es siempre la fecha actual.
4. La nota se asocia a un examen de una comisión y a un alumno ya inscripto en esa comisión.
5. El uso es individual y secuencial; no hay concurrencia.
6. Los textos se muestran en español, con fechas de examen en formato `dd-MM-yyyy`.

## 6. Restricciones técnicas

- Java 17 y Maven (solo JUnit 5 como dependencia).
- Ejecución por consola; requiere JDK instalado.
- Sin base de datos ni servicios externos.
- Los IDs de exámenes, asistencias y notas se generan en memoria desde 1.

## 7. Criterios de éxito del alcance actual

- El menú principal permite acceder a los cinco módulos.
- Un alumno puede registrarse una sola vez por DNI.
- No se permiten inscripciones duplicadas ni superposición horaria.
- Las notas solo aceptan valores entre 1 y 10.
- Las consultas muestran la estructura académica precargada.
- Los 170 casos automatizados pasan.

## 8. Limitaciones conocidas del MVP

Estas limitaciones están verificadas contra el código actual y describen cómo funciona hoy el sistema.

1. **`Nota` no guarda referencia al alumno.** La nota se vincula al alumno solo a través de `AlumnoInscripto.agregarNota`, y esa lista no se reconstruye desde `NotaRepository`. Por eso el listado de notas no puede mostrar a quién pertenece cada una.
2. **Dos modelos de asistencia no sincronizados.** `Asistencia` (usada dentro de `AlumnoInscripto`) y `AsistenciaAlumno` (la que persiste `AsistenciaRepository`) representan el mismo concepto, pero registrar una no actualiza la otra.
3. **`ComisionRepository` no tiene operaciones de inscripción por comisión.** La inscripción y su consulta viven en `AlumnoRepository` y `AlumnoServiceImpl`; `ComisionRepository` solo lista, busca y guarda comisiones.
4. **`EstadoCursada` no se usa.** No hay cálculo de estado de cursada ni de promedio. `AlumnoInscripto` expone `calcularPorcentajeAsistencia()` y `esRegular(double)`, pero ningún servicio ni el menú los invocan.
5. **Persistencia solo en memoria.** Los repositorios usan `ArrayList`; los datos se pierden al cerrar la aplicación.
6. **Validaciones de vistas sin pruebas automatizadas.** Las validaciones de entrada de `AlumnoView`, `ExamenView`, `AsistenciaView` y `NotaView` se verificaron manualmente en consola.
7. **Sin autenticación, roles ni portal de alumnos.** Hay un único menú para todos los actores; no hay login ni permisos.

## 9. Documentos relacionados

- [Historias de usuario](03-historias-usuario.md)
- [DFD 0](05-dfd-0-contexto.md) y [DFD 1](06-dfd-1-sistema.md)
- [Trazabilidad](08-trazabilidad.md)
- [Manual de uso](10-manual-uso.md)
