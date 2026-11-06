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
| Pruebas | JUnit 5 (`src/test/java`), 120 casos ejecutados en 14 clases |

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
| Emisión de constancias o reportes exportables | No implementado |
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
- Los 120 casos automatizados pasan.

## 8. Documentos relacionados

- [Historias de usuario](03-historias-usuario.md)
- [DFD 0](05-dfd-0-contexto.md) y [DFD 1](06-dfd-1-sistema.md)
- [Trazabilidad](08-trazabilidad.md)
- [Manual de uso](10-manual-uso.md)
