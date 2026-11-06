# 02 — Reglas de negocio y requisitos no funcionales

## 1. Reglas de negocio

| ID | Regla | Validación / evidencia | HU |
|---|---|---|---|
| RN-01 | El alumno debe tener nombre, apellido y DNI no nulos ni vacíos. | `Alumno` constructor y setters lanzan `IllegalArgumentException`. | HU-01 |
| RN-02 | El DNI es único en el padrón. | `AlumnoService.registrarAlumno` consulta `buscarPorDni` antes de guardar. | HU-01 |
| RN-03 | Email y teléfono son opcionales. | `Alumno` solo valida nombre, apellido y DNI. | HU-01 |
| RN-04 | La nota debe ser un número entre 1 y 10 inclusive. | `NotaService.registrarNota` y `modificarNota`. | HU-14, HU-16 |
| RN-05 | La fecha de registro de la nota es la fecha actual. | `NotaService.registrarNota` asigna `LocalDate.now()`. | HU-14 |
| RN-06 | El documento del alumno es obligatorio para registrar una asistencia. | `AsistenciaService.validarDocumento` y `AsistenciaAlumno.setDocumentoAlumno`. | HU-10, HU-12 |
| RN-07 | No se permite una asistencia con fecha futura. El alta desde el menú usa la fecha actual. | `Asistencia` y `AsistenciaAlumno.setFecha`; `AsistenciaService.registrarAsistencia`. | HU-10, HU-12 |
| RN-08 | Solo puede existir una asistencia por alumno y fecha. | `AsistenciaService.existeAsistencia`; `AlumnoInscripto.registrarAsistencia` también rechaza duplicados por fecha. | HU-10, HU-12 |
| RN-09 | Un alumno no puede inscribirse dos veces en la misma comisión. | `AlumnoService.inscribirAlumnoComision` + `existeInscripcionComision`. | HU-04 |
| RN-10 | Un alumno no puede inscribirse en dos comisiones con horarios superpuestos. | `AlumnoService` recorre inscripciones y usa `ComisionMateria.chocaCon`. | HU-04 |
| RN-11 | El código de comisión no puede ser vacío y la materia no puede ser nula. | `ComisionMateria` constructor. | HU-04, HU-22 |
| RN-12 | En un horario, la hora de inicio debe ser anterior a la de fin y los módulos no pueden superponerse; dos horarios chocan solo si son el mismo día y comparten franja. | `ModuloHorario`, `HorarioClase.agregarModulo/chocaCon`. | HU-04, HU-22 |
| RN-13 | Un examen requiere comisión, nombre, descripción, fecha y tipo de evaluación no nulos. | `Examen` constructor y setters. | HU-06, HU-08 |
| RN-14 | Un plan de estudio requiere carrera no nula y año positivo. | `PlanEstudio` constructor. | HU-21 |
| RN-15 | Los identificadores de exámenes, asistencias y notas se generan automáticamente desde 1. | Repositorios (`siguienteId`). | HU-06, HU-10, HU-14 |
| RN-16 | Una búsqueda vacía o nula devuelve todos los registros. | `buscarAlumno`, `buscarAsistencias`, `buscarExamenes`, `buscarNotas`. | HU-02, HU-07, HU-11, HU-15 |
| RN-17 | Si el criterio es numérico, se busca por ID; si es texto, por nombre de examen o documento, según el módulo. | `AsistenciaRepository.buscarPorCriterio`, `ExamenRepository.buscarPorCriterio`, `NotaService.buscarNotas`. | HU-07, HU-11, HU-15 |
| RN-18 | Modificar o eliminar un registro inexistente produce un error. | Servicios lanzan `IllegalArgumentException`. | HU-08, HU-09, HU-12, HU-13, HU-16, HU-17 |
| RN-19 | Al modificar una nota con valor nulo, se conserva el valor actual y solo se actualiza la observación. | `NotaService.modificarNota` valida `if (valor != null)`. | HU-16 |
| RN-20 | La observación de una nota es opcional y no se valida. | `Nota.setObservacion`. | HU-14 |

### 1.1 Reglas derivadas (implementadas en el modelo, no expuestas en el menú)

| ID | Regla | Evidencia |
|---|---|---|
| RD-01 | Porcentaje de asistencia = presentes / total de asistencias × 100. Si no hay asistencias, es 0. | `AlumnoInscripto.calcularPorcentajeAsistencia` |
| RD-02 | Un alumno es regular si su porcentaje de asistencia es mayor o igual a un mínimo recibido por parámetro. | `AlumnoInscripto.esRegular(double)` |
| RD-03 | Una nota puede agregarse a una inscripción (`AlumnoInscripto.agregarNota`). | `AlumnoInscripto` |

> Estas reglas están cubiertas por pruebas (`AlumnoInscriptoTest`), pero **no se usan desde la interfaz**. El estado de cursada (`EstadoCursada`) todavía no se calcula.

## 2. Requisitos no funcionales

| ID | Requisito | Estado | Observación |
|---|---|---|---|
| RNF-01 | Persistencia de datos entre ejecuciones | ❌ No cumple | Repositorios en memoria; se pierde todo al cerrar. |
| RNF-02 | Autenticación y autorización por rol | ❌ No cumple | Un solo menú sin login. |
| RNF-03 | Validación de entradas con mensajes claros en español | ⚠️ Parcial | Los modelos/servicios validan; la vista captura `IllegalArgumentException`. La lectura de enteros inválidos devuelve `-1` y muestra "Opción inválida". |
| RNF-04 | Cobertura automatizada de las funciones principales | ⚠️ Parcial | 105 pruebas, 14 clases. Sin pruebas para el servicio de asistencia. |
| RNF-05 | Codificación y presentación correcta de acentos | ⚠️ Parcial | Algunos archivos fuente tienen codificación mixta y la consola de Windows muestra caracteres corruptos (`Opci��n`). |
| RNF-06 | Portabilidad | ✅ | Java 17; ejecutable en cualquier SO con JDK. |
| RNF-07 | Facilidad de uso en consola | ⚠️ Parcial | Menús numerados y mensajes guía; no hay confirmación antes de eliminar. |
| RNF-08 | Tiempo de respuesta | ✅ | Operaciones en memoria, respuesta inmediata. |

## 3. Semántica de búsquedas

| Módulo | Criterio vacío/nulo | Criterio numérico | Criterio de texto |
|---|---|---|---|
| Alumnos | Devuelve todos | DNI exacto | Nombre o apellido que contenga el texto (sin distinguir mayúsculas) |
| Exámenes | Devuelve todos | ID exacto | Nombre de examen que contenga el texto |
| Asistencias | Devuelve todas | ID exacto | Documento exacto |
| Notas | Devuelve todas | ID exacto | Nombre de examen que contenga el texto |

## 4. Manejo de errores

- Los errores de negocio se expresan con `IllegalArgumentException`.
- Las vistas capturan la excepción y muestran `Error: <mensaje>`.
- Las operaciones de eliminación devuelven error si el ID no existe.
- No hay logging ni auditoría de operaciones.

## 5. Relación con las historias

Cada historia de [03 — Historias de usuario](03-historias-usuario.md) referencia las reglas RN que aplica. Los criterios de [04 — Criterios de aceptación](04-criterios-aceptacion.md) verifican estas reglas.
