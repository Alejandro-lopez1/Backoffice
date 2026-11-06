# 07 — Diccionario de datos

## 1. Entidades del dominio

### D1 · Alumno

| Atributo | Tipo | Obligatorio | Regla |
|---|---|---|---|
| nombre | String | Sí | No vacío (RN-01) |
| apellido | String | Sí | No vacío (RN-01) |
| dni | String | Sí | No vacío y único (RN-01, RN-02) |
| email | String | No | Sin validación de formato |
| telefono | String | No | Sin validación de formato |

**Clave:** `dni`. **Igualdad:** dos alumnos son iguales si tienen el mismo DNI.

### D2 · Inscripción (`AlumnoInscripto`)

| Atributo | Tipo | Obligatorio | Regla |
|---|---|---|---|
| alumno | Alumno | Sí | Debe existir |
| comision | ComisionMateria | Sí | No nula |
| fechaInscripcion | LocalDate | Sí | Fecha actual al crear |
| asistencias | List\<Asistencia\> | — | Sin duplicados por fecha |
| notas | List\<Nota\> | — | Se agregan al registrar una nota |

**Clave lógica:** alumno + comisión.

### D3 · Asistencia (`AsistenciaAlumno`)

| Atributo | Tipo | Obligatorio | Regla |
|---|---|---|---|
| id | Long | Sí (generado) | Autoincremental |
| documentoAlumno | String | Sí | No vacío |
| fecha | LocalDate | Sí | No futura |
| presente | boolean | Sí | true = presente |

**Clave lógica:** documento + fecha (RN-08).

> Existe además `Asistencia` (fecha + presente), usada dentro de `AlumnoInscripto`. Es un objeto de valor, no un registro del repositorio.

### D4 · Examen

| Atributo | Tipo | Obligatorio | Regla |
|---|---|---|---|
| id | Long | Sí (generado) | Autoincremental |
| comision | ComisionMateria | Sí | No nula |
| nombre | String | Sí | No vacío |
| descripcion | String | Sí | No vacía (RN-13). La vista la solicita en el alta. |
| fecha | LocalDate | Sí | No nula |
| tipoEvaluacion | TipoEvaluacion | Sí | PARCIAL, RECUPERATORIO o FINAL |

### D5 · Nota

| Atributo | Tipo | Obligatorio | Regla |
|---|---|---|---|
| id | Long | Sí (generado) | Autoincremental |
| examen | Examen | Sí | Debe existir |
| valor | Double | Sí | Entre 1 y 10 |
| observacion | String | No | Sin validación |
| fechaRegistro | LocalDate | Sí | Fecha actual al registrar |

**Limitación:** no incluye `alumno`. La relación se mantiene solo en `AlumnoInscripto`.

### D6 · Comisión (`ComisionMateria`)

| Atributo | Tipo | Obligatorio | Regla |
|---|---|---|---|
| codigo | String | Sí | No vacío |
| materia | Materia | Sí | No nula |
| profesor | Profesor | No | Puede ser nulo |
| cuatrimestre | Cuatrimestre | No | Puede ser nulo |
| horarios | List\<HorarioClase\> | — | Sin superposición interna |

**Clave:** código (la igualdad se define por código).

### D7 · Plan de estudio

| Atributo | Tipo | Obligatorio | Regla |
|---|---|---|---|
| carrera | Carrera | Sí | No nula |
| anio | int | Sí | > 0 |
| materia | Materia | No | — |

### D8 · Carrera

| Atributo | Tipo | Obligatorio |
|---|---|---|
| codigo | String | Sí |
| nombre | String | Sí |

### D9 · Materia

| Atributo | Tipo | Obligatorio |
|---|---|---|
| nombre | String | Sí |

### D10 · Profesor

| Atributo | Tipo | Obligatorio |
|---|---|---|
| nombre | String | Sí |
| apellido | String | Sí |
| email | String | No |
| telefono | String | No |

### Estructura de horarios

| Entidad | Atributos | Regla |
|---|---|---|
| Cuatrimestre | anio (>0), numero (1-2), fechaInicio, fechaFin (inicio < fin) | RN-12 |
| HorarioClase | diaSemana, modulos | Módulos del mismo día no se superponen |
| ModuloHorario | numero, horaInicio, horaFin (inicio < fin) | RN-12 |

## 2. Flujos de datos

| ID | Flujo | Composición |
|---|---|---|
| F01 | Datos de alumno | nombre + apellido + dni + email? + telefono? |
| F02 | Confirmación y listado | alumno(s) registrados |
| F03 | Datos de inscripción | dni + comisión elegida de la lista |
| F04 | Constancia de inscripción | alumno + comisión + fecha |
| F05 | Criterio de consulta | texto libre o vacío |
| F06 | Listados y reportes | carreras, materias, profesores, planes, comisiones, alumnos |
| F07 | Datos de asistencia | documento + presente + fecha |
| F08 | Constancia de asistencia | id + documento + fecha + estado |
| F09 | Datos de examen | comisión + nombre + descripción + fecha + tipo |
| F10 | Constancia de examen | examen con id |
| F11 | Datos de nota | inscripción + examen + valor + observación? |
| F12 | Constancia de nota | nota con id + fecha de registro |
| F13 | Identificación del alumno | dni (planificado) |
| F14 | Situación académica | notas + asistencias + estado de cursada (planificado) |

## 3. Almacenes y repositorios

| Almacén | Repositorio | Datos iniciales |
|---|---|---|
| D1 Alumnos | `AlumnoRepository` | 4 alumnos |
| D2 Inscripciones | `AlumnoRepository` | Vacío |
| D3 Asistencias | `AsistenciaRepository` | Vacío |
| D4 Exámenes | `ExamenRepository` | Vacío |
| D5 Notas | `NotaRepository` | Vacío |
| D6 Comisiones | `ComisionRepository` | 4 comisiones 2026 |
| D7 Planes | `PlanEstudioRepository` | 9 planes |
| D8 Carreras | `CarreraRepository` | 3 carreras |
| D9 Materias | `MateriaRepository` | 3 materias |
| D10 Profesores | `ProfesorRepository` | 4 profesores |

## 4. Datos derivados

| Dato | Cálculo | Dónde |
|---|---|---|
| Porcentaje de asistencia | presentes / total × 100 (0 si no hay registros) | `AlumnoInscripto.calcularPorcentajeAsistencia` |
| Regularidad | porcentaje >= mínimo recibido | `AlumnoInscripto.esRegular(double)` |
| Promedio | No implementado | Backlog HU-26 |
| Estado de cursada | No implementado | Backlog HU-25 |

## 5. Tipos enumerados

| Enum | Valores | Uso |
|---|---|---|
| `TipoEvaluacion` | `PARCIAL`, `RECUPERATORIO`, `FINAL` | Examen |
| `EstadoCursada` | `PROMOCIONADO`, `REGULAR`, `DESAPROBADO` | Definido, sin uso actual |

## 6. Observaciones de integridad

1. `Nota` no referencia al alumno; el vínculo existe solo en memoria a través de `AlumnoInscripto`. Si se persiste la nota de forma aislada, se pierde la relación.
2. `AsistenciaAlumno.documentoAlumno` es un texto, no una clave foránea; no se valida contra D1.
3. La inscripción valida el código contra D6 (`ComisionRepository.buscarPorCodigo`), por lo que D2 solo referencia comisiones existentes. El lado inverso no se valida: una comisión de D6 puede eliminarse sin revisar D2.
4. `EstadoCursada` no participa de ningún cálculo.
