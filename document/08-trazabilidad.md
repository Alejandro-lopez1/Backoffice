# 08 — Trazabilidad

## 1. Objetivo

Relacionar cada historia de usuario con sus criterios de aceptación, el proceso y almacén del DFD, la implementación en código y las pruebas automatizadas.

## 2. Matriz de trazabilidad

| HU | CA | Proceso | Almacén | Implementación | Pruebas | Estado |
|---|---|---|---|---|---|---|
| HU-01 Registrar alumno | CA-HU01-1 a 4 | 1.0 | D1 | `AlumnoService.registrarAlumno`, `Alumno` | `AlumnoTest`, `AlumnoServiceTest` | ✅ |
| HU-02 Buscar alumno | CA-HU02-1 a 4 | 1.0 | D1 | `AlumnoService.buscarAlumno`, `AlumnoRepository.buscarPorDni/buscarPorNombre` | `AlumnoServiceTest`, `AlumnoRepositoryTest` | ✅ |
| HU-03 Listar alumnos | CA-HU03-1 | 1.0 | D1 | `AlumnoController.listarAlumnos`, `AlumnoServiceImpl.listarAlumnos`, `AlumnoRepository.buscarTodos` | `AlumnoControllerTest.listarAlumnosIncluyeElAlumnoRegistrado`, `AlumnoServiceTest.listarAlumnosIncluyeLosRegistrados` | ✅ |
| HU-04 Inscribir en comisión | CA-HU04-1 a 5 | 2.0 | D1, D2, D6, D7 | `AlumnoServiceImpl.inscribirAlumnoComision(dni, codigoComision)` (resuelve la comisión real), `AlumnoController.inscribirAlumnoComision` | `AlumnoServiceTest` (inscripción, duplicado, superposición y código inexistente), `AlumnoControllerTest`, `ComisionRepositoryTest` | ✅ |
| HU-05 Ver comisiones del alumno | CA-HU05-1 a 2 | 2.0 | D2 | `AlumnoServiceImpl.obtenerComisionesDeAlumno` | `AlumnoServiceTest.obtenerComisionesDeAlumno` | ✅ |
| HU-06 Registrar examen | CA-HU06-1 a 4 | 4.0 | D4, D6 | `ExamenView.registrarExamen` (solicita la descripción), `ProfesorExamenServiceImpl.registrarExamen`, `Examen` | `ProfesorExamenServiceTest` | ✅ |
| HU-07 Listar/buscar exámenes | CA-HU07-1 a 4 | 4.0 | D4 | `ProfesorExamenServiceImpl.buscarExamenes`, `ExamenRepository.buscarPorCriterio` | `ProfesorExamenServiceTest.buscarExamenPorIdYPorNombre` | ✅ |
| HU-08 Modificar examen | CA-HU08-1 a 2 | 4.0 | D4 | `ProfesorExamenServiceImpl.modificarExamen` | `ProfesorExamenServiceTest.modificarExamen` | ✅ |
| HU-09 Eliminar examen | CA-HU09-1 a 2 | 4.0 | D4 | `ProfesorExamenServiceImpl.eliminarExamen` | `ProfesorExamenServiceTest.eliminarExamen` | ✅ |
| HU-10 Registrar asistencia | CA-HU10-1 a 3 | 3.0 | D3 | `AlumnoAsistenciaServiceImpl.registrarAsistencia`, `AsistenciaAlumno` | `AlumnoAsistenciaServiceTest.registrarAsistenciaValida`, `AsistenciaTest` (modelo) | ✅ |
| HU-11 Listar/buscar asistencias | CA-HU11-1 a 3 | 3.0 | D3 | `AlumnoAsistenciaServiceImpl.buscarAsistencias`, `AsistenciaRepository.buscarPorCriterio` | `AlumnoAsistenciaServiceTest.buscarAsistenciasPorDocumentoYPorId` | ✅ |
| HU-12 Modificar asistencia | CA-HU12-1 a 4 | 3.0 | D3 | `AlumnoAsistenciaServiceImpl.modificarAsistencia` | `AlumnoAsistenciaServiceTest.modificarAsistenciaActualizaLosDatos`, `AsistenciaTest` (fecha futura) | ✅ |
| HU-13 Eliminar asistencia | CA-HU13-1 a 2 | 3.0 | D3 | `AlumnoAsistenciaServiceImpl.eliminarAsistencia` | `AlumnoAsistenciaServiceTest.eliminarAsistenciaExistenteEInexistente` | ✅ |
| HU-14 Registrar nota | CA-HU14-1 a 4 | 5.0 | D2, D4, D5 | `AlumnoNotaServiceImpl.registrarNota`, `AlumnoInscripto.agregarNota`, `NotaView.registrarNota` | `AlumnoNotaServiceTest` | ✅ |
| HU-15 Listar/buscar notas | CA-HU15-1 a 4 | 5.0 | D5 | `AlumnoNotaServiceImpl.buscarNotas`, `NotaRepository.buscarPorId` | `AlumnoNotaServiceTest.buscarNotaPorIdYPorNombreDeExamen` | ✅ |
| HU-16 Modificar nota | CA-HU16-1 a 4 | 5.0 | D5 | `AlumnoNotaServiceImpl.modificarNota` | `AlumnoNotaServiceTest.modificarNotaConValorNuloConservaElValor` | ✅ |
| HU-17 Eliminar nota | CA-HU17-1 a 2 | 5.0 | D5 | `AlumnoNotaServiceImpl.eliminarNota` | `AlumnoNotaServiceTest.eliminarNota` | ✅ |
| HU-18 Ver carreras | CA-HU18-1 | 6.0 | D8 | `ConsultasController.listarCarreras`, `CarreraServiceImpl` | `CarreraServiceTest`, `CarreraTest` (modelo) | ✅ |
| HU-19 Ver materias | CA-HU19-1 | 6.0 | D7, D9 | `ConsultasController.listarMaterias`, `MateriaServiceImpl` | `MateriaServiceTest` | ✅ |
| HU-20 Ver profesores | CA-HU20-1 | 6.0 | D10 | `ConsultasController.listarProfesores`, `ProfesorConsultaServiceImpl` | `ProfesorConsultaServiceTest` | ✅ |
| HU-21 Ver plan de estudio | CA-HU21-1 | 6.0 | D7 | `ConsultasController.listarPlanes`, `PlanEstudioServiceImpl` | `PlanEstudioServiceTest`, `PlanEstudioTest` (modelo) | ✅ |
| HU-22 Ver comisiones | CA-HU22-1 | 6.0 | D6 | `ConsultasController.listarComisiones`, `ComisionServiceImpl` | `ComisionServiceTest`, `ComisionMateriaTest`, `HorarioClaseTest`, `ModuloHorarioTest` (modelo) | ✅ |
| HU-23 Comisiones por profesor | CA-HU23-1 | 6.0 | D6, D10 | `ConsultasController.buscarComisionesPorProfesor`, `ComisionServiceImpl` | `ComisionServiceTest.buscarPorProfesorFiltraPorApellido` | ✅ |
| HU-24 Ver alumnos | CA-HU24-1 | 6.0 | D1 | `ConsultasController.listarAlumnos`, `AlumnoServiceImpl` | `ConsultasControllerTest.listarAlumnosDelegaEnElServicio`, `AlumnoRepositoryTest.buscarTodos` | ✅ |
| HU-25 Estado de cursada | Propuestos | — | — | `EstadoCursada` (sin uso) | — | 📋 |
| HU-26 Promedio | Propuestos | — | — | — | — | 📋 |
| HU-27 Correlatividades | Propuestos | — | — | — | — | 📋 |
| HU-28 Consultar situación | Propuestos | 7.0 | D1, D2, D3, D5 | — | — | 📋 |
| HU-29 Constancia regular | Propuestos | 7.0 | D2, D5 | — | — | 📋 |

## 3. Cobertura de pruebas

### 3.1 Inventario

| Clase de prueba | Métodos (`@Test` + `@ParameterizedTest`) | Casos ejecutados | Qué cubre |
|---|---|---|---|
| `AlumnoTest` | 8 | 11 | Validaciones y contrato de `Alumno` |
| `AlumnoInscriptoTest` | 9 | 9 | Inscripción, asistencias, porcentaje y regularidad |
| `AsistenciaTest` | 9 | 9 | Fecha, presente, fecha futura, comparación |
| `CarreraTest` | 6 | 6 | Validaciones de `Carrera` |
| `ComisionMateriaTest` | 10 | 10 | Comisión, horarios, superposición |
| `HorarioClaseTest` | 10 | 10 | Módulos y choques de horario |
| `ModuloHorarioTest` | 11 | 11 | Validación de horas y superposición |
| `PlanEstudioTest` | 4 | 4 | Validaciones de plan |
| `AlumnoRepositoryTest` | 13 | 13 | Alta, búsquedas e inscripciones |
| `AlumnoServiceTest` | 23 | 23 | Registro, búsquedas, listado, inscripción, duplicado, superposición y comisiones |
| `AlumnoAsistenciaServiceTest` | 9 | 9 | Alta, duplicado, búsqueda por documento/ID, modificación, baja y validaciones |
| `AlumnoNotaServiceTest` | 7 | 7 | Alta válida, rango 1-10, modificación, búsqueda, baja e inexistentes |
| `ProfesorExamenServiceTest` | 7 | 7 | Alta con descripción, rechazo de vacía, búsqueda, modificación, baja y por comisión |
| `CarreraServiceTest` | 3 | 3 | Listado y búsqueda por código (existente, inexistente y nulo) |
| `MateriaServiceTest` | 3 | 3 | Listado y búsqueda por nombre (existente e inexistente) |
| `PlanEstudioServiceTest` | 3 | 3 | Listado y búsqueda por carrera (existente, inexistente y nula) |
| `ComisionServiceTest` | 7 | 7 | Listado, código, materia y profesor (incluye vacíos) |
| `ProfesorConsultaServiceTest` | 2 | 2 | Listado y copia independiente |
| `ComisionRepositoryTest` | 4 | 4 | Filtro exacto por materia y búsqueda por código |
| `AlumnoControllerTest` | 7 | 7 | Inscripción con comisión real, listado y disponibilidad para notas |
| `ConsultasControllerTest` | 12 | 12 | Delegación de las 12 consultas de lectura |
| **Total** | **167** | **170** | 21 clases de prueba |

**Total:** 170 casos ejecutados (`mvn test`: 0 fallidos, 0 omitidos). La diferencia entre 167 métodos y 170 casos proviene de `AlumnoTest`: sus 3 `@ParameterizedTest` con `@NullAndEmptySource` ejecutan 2 casos cada uno.

### 3.2 Cobertura por módulo

| Módulo | Cobertura | Observación |
|---|---|---|
| Alumnos | ✅ Alta | Modelo, repositorio, servicio y controlador |
| Inscripciones | ✅ Alta | Servicio, repositorio y controlador (comisión real) |
| Exámenes | ✅ Alta | `ProfesorExamenServiceTest` cubre el ciclo completo |
| Asistencias | ✅ Alta | `AlumnoAsistenciaServiceTest` cubre el ciclo completo |
| Notas | ✅ Alta | `AlumnoNotaServiceTest` cubre el ciclo completo |
| Consultas | ✅ Alta | Servicios de consulta y `ConsultasController` con delegación cubierta |

### 3.3 Brechas prioritarias

1. **Pruebas de vista**: las validaciones de entrada de `ExamenView`, `AlumnoView` y `NotaView` se verificaron manualmente en consola, sin pruebas automatizadas.
2. **`Nota` sin referencia al alumno**: el listado no puede mostrar a quién pertenece cada nota.
3. **Persistencia**: todos los almacenes son en memoria y se pierden al cerrar la aplicación.

## 4. Matriz de reglas

| Regla | Implementación | Prueba |
|---|---|---|
| RN-01 a RN-03 | `Alumno` | `AlumnoTest` |
| RN-04, RN-05 | `AlumnoNotaService` | `AlumnoNotaServiceTest` |
| RN-06 a RN-08 | `AlumnoAsistenciaService` | `AlumnoAsistenciaServiceTest` |
| RN-09, RN-10 | `AlumnoService` | `AlumnoServiceTest` |
| RN-11, RN-12 | `ComisionMateria`, `HorarioClase`, `ModuloHorario` | ✅ |
| RN-13 | `Examen` | `ProfesorExamenServiceTest` |
| RN-14 | `PlanEstudio` | `PlanEstudioTest` |
| RN-15 | Repositorios | Parcial |
| RN-16, RN-17 | Servicios/repositorios | Parcial |
| RN-18 | Servicios | Parcial |
| RD-01, RD-02 | `AlumnoInscripto` | `AlumnoInscriptoTest` |

## 5. Conclusión

La documentación cubre 24 historias implementadas en código, todas operables de punta a punta desde el menú. La deuda principal está en **pruebas de las vistas** y en **dos brechas de diseño** ya identificadas: la nota no guarda referencia al alumno y no hay persistencia.
