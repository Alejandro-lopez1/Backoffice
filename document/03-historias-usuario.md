# 03 — Historias de usuario

## 1. Convenciones

**Formato:** `Como <actor>, quiero <acción> para <beneficio>`.

**Prioridad (MoSCoW):**

- **Must:** imprescindible para el funcionamiento actual.
- **Should:** importante, ya implementado o de alto valor.
- **Could:** deseable, de menor urgencia.
- **Won't (este alcance):** fuera del alcance actual.

**Estado:** ✅ implementada · ⚠️ implementada con limitación · 🧩 parcial · 📋 backlog.

Los criterios de aceptación asociados están en [04 — Criterios de aceptación](04-criterios-aceptacion.md).

## 2. Resumen

| Épica | Historias | Estado |
|---|---|---|
| Alumnos y padrón | HU-01 a HU-05 | 5 ✅ |
| Exámenes | HU-06 a HU-09 | 4 ✅ |
| Asistencias | HU-10 a HU-13 | 4 ⚠️ |
| Notas | HU-14 a HU-17 | 4 ⚠️ |
| Consultas | HU-18 a HU-24 | 7 ✅ |
| Backlog | HU-25 a HU-29 | 5 📋 |

---

## 3. Épica Alumnos y padrón

### HU-01 · Registrar alumno
- **Prioridad:** Must
- **Actor:** Administrativo
- **Historia:** Como administrativo, quiero registrar un alumno con sus datos personales para incorporarlo al padrón y poder inscribirlo en comisiones.
- **Reglas:** RN-01, RN-02, RN-03
- **Datos:** nombre, apellido, DNI, email (opcional), teléfono (opcional)
- **Estado:** ✅
- **Evidencia:** `AlumnoService.registrarAlumno`, `AlumnoView.registrarAlumno`, `AlumnoTest`, `AlumnoServiceTest`

### HU-02 · Buscar alumno
- **Prioridad:** Must
- **Actor:** Administrativo
- **Historia:** Como administrativo, quiero buscar un alumno por DNI, nombre o apellido para consultar sus datos sin recorrer todo el padrón.
- **Reglas:** RN-16, RN-17
- **Datos:** criterio de búsqueda
- **Estado:** ✅
- **Evidencia:** `AlumnoService.buscarAlumno`, `AlumnoRepository.buscarPorDni`, `AlumnoRepository.buscarPorNombre`, `AlumnoServiceTest`

### HU-03 · Listar alumnos
- **Prioridad:** Should
- **Actor:** Administrativo
- **Historia:** Como administrativo, quiero ver el listado completo de alumnos para tener una visión general del padrón.
- **Reglas:** RN-16
- **Datos:** ninguno
- **Estado:** ✅
- **Evidencia:** `AlumnoController.listarAlumnos`, `AlumnoRepository.buscarTodos`, `AlumnoRepositoryTest.buscarTodos`

### HU-04 · Inscribir alumno en comisión
- **Prioridad:** Must
- **Actor:** Administrativo
- **Historia:** Como administrativo, quiero inscribir un alumno en una comisión para registrar su cursada y habilitar asistencias y notas.
- **Reglas:** RN-09, RN-10, RN-11, RN-12
- **Datos:** DNI, comisión elegida de la lista de comisiones disponibles
- **Estado:** ✅
- **Evidencia:** `AlumnoView.inscribirComision`, `AlumnoController.inscribirAlumnoComision`, `AlumnoService.inscribirAlumnoComision`, `AlumnoServiceTest`, `AlumnoControllerTest`
- **Observaciones:**
  - La vista muestra las comisiones con materia, profesor y horarios, y el usuario elige por número. El controlador resuelve la comisión real con `ComisionRepository.buscarPorCodigo`, por lo que la inscripción conserva profesor, cuatrimestre y horarios.
  - Como la comisión tiene horarios, la validación de superposición horaria (RN-10) se aplica efectivamente desde el menú.
  - Un código de comisión inexistente produce el error `No existe la comisión con código ...`.

### HU-05 · Consultar comisiones de un alumno
- **Prioridad:** Should
- **Actor:** Administrativo / Docente
- **Historia:** Como administrativo, quiero ver en qué comisiones está inscripto un alumno para controlar su situación de cursada.
- **Reglas:** RN-09
- **Datos:** DNI
- **Estado:** ✅
- **Evidencia:** `AlumnoService.obtenerComisionesDeAlumno`, `AlumnoView.verComisiones`, `AlumnoServiceTest.obtenerComisionesDeAlumno`

---

## 4. Épica Exámenes

### HU-06 · Registrar examen
- **Prioridad:** Must
- **Actor:** Docente
- **Historia:** Como docente, quiero registrar un examen de una comisión con fecha y tipo de evaluación para luego cargar sus notas.
- **Reglas:** RN-13, RN-15
- **Datos:** carrera, materia, comisión, fecha (`dd-MM-yyyy`), título, descripción, tipo (`PARCIAL`, `RECUPERATORIO`, `FINAL`)
- **Estado:** ✅
- **Evidencia:** `ExamenView.registrarExamen`, `ProfesorExamenService.registrarExamen`, `ExamenRepository.guardar`, `ProfesorExamenServiceTest`
- **Observaciones:**
  - La vista solicita título, descripción, fecha y tipo, y envía la descripción al servicio; el modelo exige que no sea vacía (RN-13).
  - El examen registrado muestra ID, comisión, materia, profesor, nombre, descripción, fecha y tipo.

### HU-07 · Listar y buscar exámenes
- **Prioridad:** Must
- **Actor:** Docente
- **Historia:** Como docente, quiero listar o buscar exámenes por ID o nombre para consultar su información.
- **Reglas:** RN-16, RN-17
- **Datos:** criterio (ID o texto)
- **Estado:** ✅
- **Evidencia:** `ProfesorExamenService.listarExamenes`, `ProfesorExamenService.buscarExamenes`, `ExamenRepository.buscarPorCriterio`, `ProfesorExamenServiceTest`

### HU-08 · Modificar examen
- **Prioridad:** Should
- **Actor:** Docente
- **Historia:** Como docente, quiero modificar los datos de un examen para corregir fechas, descripción o tipo de evaluación.
- **Reglas:** RN-13, RN-18
- **Datos:** ID y nuevos datos
- **Estado:** ✅
- **Evidencia:** `ProfesorExamenService.modificarExamen`, `ExamenView.modificarExamen`

### HU-09 · Eliminar examen
- **Prioridad:** Should
- **Actor:** Docente
- **Historia:** Como docente, quiero eliminar un examen cargado por error para mantener actualizada la planificación.
- **Reglas:** RN-18
- **Datos:** ID
- **Estado:** ✅
- **Evidencia:** `ProfesorExamenService.eliminarExamen`, `ExamenRepository.eliminar`

---

## 5. Épica Asistencias

### HU-10 · Registrar asistencia del día
- **Prioridad:** Must
- **Actor:** Docente
- **Historia:** Como docente, quiero registrar si un alumno estuvo presente o ausente en la fecha de hoy para llevar el control de asistencia.
- **Reglas:** RN-06, RN-07, RN-08
- **Datos:** documento del alumno, presente (S/N)
- **Estado:** ⚠️
- **Evidencia:** `AsistenciaView.registrarAsistencia`, `AlumnoAsistenciaService.registrarAsistencia`, `AsistenciaAlumno`
- **Limitación:** la asistencia se guarda en `AsistenciaRepository` como `AsistenciaAlumno`, pero no se agrega a la lista `asistencias` de `AlumnoInscripto`; el porcentaje de asistencia de la inscripción no se actualiza.

### HU-11 · Listar y buscar asistencias
- **Prioridad:** Should
- **Actor:** Docente
- **Historia:** Como docente, quiero listar o buscar asistencias por ID o documento para revisar el historial.
- **Reglas:** RN-16, RN-17
- **Datos:** criterio
- **Estado:** ⚠️
- **Evidencia:** `AlumnoAsistenciaService.buscarAsistencias`, `AsistenciaRepository.buscarPorCriterio`
- **Limitación:** el listado y la búsqueda leen solo `AsistenciaRepository` (`AsistenciaAlumno`); no reflejan la lista `Asistencia` de `AlumnoInscripto`.

### HU-12 · Modificar asistencia
- **Prioridad:** Could
- **Actor:** Docente
- **Historia:** Como docente, quiero corregir una asistencia para arreglar un error de carga.
- **Reglas:** RN-06, RN-07, RN-08, RN-18
- **Datos:** ID, documento, fecha, presente
- **Estado:** ⚠️
- **Evidencia:** `AlumnoAsistenciaService.modificarAsistencia`, `AsistenciaView.modificarAsistencia`
- **Limitación:** la modificación afecta solo `AsistenciaAlumno`; la lista `Asistencia` de `AlumnoInscripto` queda desincronizada.

### HU-13 · Eliminar asistencia
- **Prioridad:** Could
- **Actor:** Docente
- **Historia:** Como docente, quiero eliminar una asistencia cargada por error.
- **Reglas:** RN-18
- **Datos:** ID
- **Estado:** ⚠️
- **Evidencia:** `AlumnoAsistenciaService.eliminarAsistencia`, `AsistenciaRepository.eliminar`
- **Limitación:** la baja elimina solo de `AsistenciaRepository`; no existe sincronización con `AlumnoInscripto`.

---

## 6. Épica Notas

### HU-14 · Registrar nota
- **Prioridad:** Must
- **Actor:** Docente
- **Historia:** Como docente, quiero registrar la nota de un alumno en un examen para dejar constancia de su evaluación.
- **Reglas:** RN-04, RN-05, RN-20
- **Datos:** carrera, materia, comisión, alumno inscripto, examen, valor (1-10), observación (opcional)
- **Estado:** ⚠️
- **Evidencia:** `NotaView.registrarNota`, `AlumnoNotaService.registrarNota`, `NotaRepository.guardar`, `AlumnoInscripto.agregarNota`, `AlumnoNotaServiceTest`
- **Observaciones:**
  - La vista permite elegir carrera, materia, comisión, alumno inscripto y examen; solo aparecen los alumnos inscriptos en la comisión elegida.
  - **Limitación:** `Nota` guarda el examen, el valor, la observación y la fecha, pero **no guarda una referencia al alumno**. La relación con el alumno solo existe en la lista interna de `AlumnoInscripto`, por lo que al listar notas no se puede saber a qué alumno pertenecen.

### HU-15 · Listar y buscar notas
- **Prioridad:** Should
- **Actor:** Docente
- **Historia:** Como docente, quiero listar o buscar notas por ID o nombre de examen para revisar las evaluaciones registradas.
- **Reglas:** RN-16, RN-17
- **Datos:** criterio
- **Estado:** ⚠️
- **Evidencia:** `AlumnoNotaService.buscarNotas`, `NotaRepository.buscarPorId`
- **Limitación:** no se puede identificar al alumno de cada nota: `Nota` no guarda referencia y `NotaRepository` no reconstruye la relación desde `AlumnoInscripto`.

### HU-16 · Modificar nota
- **Prioridad:** Could
- **Actor:** Docente
- **Historia:** Como docente, quiero corregir el valor o la observación de una nota para reparar un error de carga.
- **Reglas:** RN-04, RN-18, RN-19
- **Datos:** ID, nuevo valor (opcional), observación
- **Estado:** ⚠️
- **Evidencia:** `AlumnoNotaService.modificarNota`, `NotaView.modificarNota`
- **Limitación:** la nota se modifica por ID y no guarda el alumno, por lo que no se puede verificar ni mostrar a quién pertenece.

### HU-17 · Eliminar nota
- **Prioridad:** Could
- **Actor:** Docente
- **Historia:** Como docente, quiero eliminar una nota cargada por error.
- **Reglas:** RN-18
- **Datos:** ID
- **Estado:** ⚠️
- **Evidencia:** `AlumnoNotaService.eliminarNota`, `NotaRepository.eliminar`
- **Limitación:** la baja elimina la nota de `NotaRepository`, pero deja la referencia en la lista `notas` de `AlumnoInscripto`.

---

## 7. Épica Consultas

### HU-18 · Ver carreras
- **Prioridad:** Should
- **Actor:** Administrativo
- **Historia:** Como administrativo, quiero ver todas las carreras para conocer la oferta académica.
- **Estado:** ✅ · **Evidencia:** `ConsultasView.listarCarreras`, `CarreraRepository.listarCarreras`

### HU-19 · Ver materias
- **Prioridad:** Should
- **Actor:** Administrativo
- **Historia:** Como administrativo, quiero ver las materias con sus planes y comisiones para ubicarlas en la estructura académica.
- **Estado:** ✅ · **Evidencia:** `ConsultasView.listarMaterias`, `MateriaRepository`

### HU-20 · Ver profesores
- **Prioridad:** Should
- **Actor:** Administrativo
- **Historia:** Como administrativo, quiero ver el listado de profesores para consultar sus datos de contacto.
- **Estado:** ✅ · **Evidencia:** `ConsultasView.listarProfesores`, `ProfesorRepository`

### HU-21 · Ver plan de estudio de una carrera
- **Prioridad:** Should
- **Actor:** Administrativo
- **Historia:** Como administrativo, quiero ver el plan de estudio de una carrera para conocer qué materias corresponden a cada año.
- **Reglas:** RN-14
- **Estado:** ✅ · **Evidencia:** `ConsultasView.verPlanDeEstudio`, `PlanEstudioRepository`

### HU-22 · Ver comisiones con horarios
- **Prioridad:** Should
- **Actor:** Administrativo / Docente
- **Historia:** Como administrativo, quiero ver todas las comisiones con sus horarios para organizar la cursada.
- **Reglas:** RN-11, RN-12
- **Estado:** ✅ · **Evidencia:** `ConsultasView.verComisiones`, `ComisionRepository`

### HU-23 · Ver comisiones por profesor
- **Prioridad:** Should
- **Actor:** Administrativo
- **Historia:** Como administrativo, quiero ver las comisiones asignadas a un profesor para controlar su carga docente.
- **Estado:** ✅ · **Evidencia:** `ConsultasView.verComisionesPorProfesor`, `ComisionRepository`

### HU-24 · Ver alumnos
- **Prioridad:** Should
- **Actor:** Administrativo
- **Historia:** Como administrativo, quiero ver todos los alumnos registrados para consultar el padrón.
- **Reglas:** RN-16
- **Estado:** ✅ · **Evidencia:** `ConsultasView.listarAlumnos`, `AlumnoRepository.buscarTodos`

---

## 8. Backlog (no implementado)

### HU-25 · Calcular estado de cursada
- **Prioridad:** Could
- **Actor:** Docente
- **Historia:** Como docente, quiero que el sistema determine si un alumno está promocionado, regular o desaprobado para cerrar la cursada.
- **Reglas propuestas:** combinar porcentaje de asistencia y notas; usar `EstadoCursada`.
- **Estado:** 📋 · **Referencia:** `EstadoCursada` (sin uso)

### HU-26 · Calcular promedio
- **Prioridad:** Could
- **Actor:** Docente
- **Historia:** Como docente, quiero conocer el promedio de notas de un alumno para evaluar su rendimiento.
- **Estado:** 📋

### HU-27 · Validar correlatividades
- **Prioridad:** Could
- **Actor:** Administrativo
- **Historia:** Como administrativo, quiero que el sistema impida inscribir en una materia sin tener aprobadas sus correlativas para respetar el plan de estudios.
- **Estado:** 📋

### HU-28 · Consultar situación académica (Alumno)
- **Prioridad:** Should
- **Actor:** Alumno
- **Historia:** Como alumno, quiero consultar mis notas, asistencias y estado de cursada para conocer mi situación sin pedirla en secretaría.
- **Estado:** 📋 · **Referencia:** proceso 7.0 del DFD 1 (planificado)

### HU-29 · Emitir constancia de alumno regular
- **Prioridad:** Could
- **Actor:** Alumno / Administrativo
- **Historia:** Como alumno, quiero obtener una constancia de regularidad para presentar donde me la soliciten.
- **Estado:** 📋

### Requisitos no funcionales de backlog

- **RNF-01** Persistencia (base de datos o archivos).
- **RNF-02** Autenticación y autorización por rol.
- **RNF-04** Completar cobertura de pruebas de servicios.
- **RNF-05** Unificar codificación UTF-8 y corregir acentos en consola.

Ver [02 — Reglas de negocio](02-reglas-negocio.md#2-requisitos-no-funcionales).
