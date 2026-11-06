# 08 — Trazabilidad

## 1. Objetivo

Relacionar cada historia de usuario con sus criterios de aceptación, el proceso y almacén del DFD, la implementación en código y las pruebas automatizadas.

## 2. Matriz de trazabilidad

| HU | CA | Proceso | Almacén | Implementación | Pruebas | Estado |
|---|---|---|---|---|---|---|
| HU-01 Registrar alumno | CA-HU01-1 a 4 | 1.0 | D1 | `AlumnoService.registrarAlumno`, `Alumno` | `AlumnoTest`, `AlumnoServiceTest` | ✅ |
| HU-02 Buscar alumno | CA-HU02-1 a 4 | 1.0 | D1 | `AlumnoService.buscarAlumno`, `AlumnoRepository.buscarPorDni/buscarPorNombre` | `AlumnoServiceTest`, `AlumnoRepositoryTest` | ✅ |
| HU-03 Listar alumnos | CA-HU03-1 | 1.0 | D1 | `AlumnoController.listarAlumnos`, `AlumnoRepository.buscarTodos` | `AlumnoRepositoryTest.buscarTodos` | ✅ |
| HU-04 Inscribir en comisión | CA-HU04-1 a 5 | 2.0 | D1, D2, D6, D7 | `AlumnoController.inscribirAlumnoComision` (resuelve la comisión real), `AlumnoService.inscribirAlumnoComision` | `AlumnoServiceTest` (inscripción, duplicado y superposición), `AlumnoControllerTest`, `ComisionRepositoryTest` | ✅ |
| HU-05 Ver comisiones del alumno | CA-HU05-1 a 2 | 2.0 | D2 | `AlumnoService.obtenerComisionesDeAlumno` | `AlumnoServiceTest.obtenerComisionesDeAlumno` | ✅ |
| HU-06 Registrar examen | CA-HU06-1 a 4 | 4.0 | D4, D6 | `ExamenView.registrarExamen` (solicita la descripción), `ExamenService.registrarExamen`, `Examen` | `ExamenServiceTest` | ✅ |
| HU-07 Listar/buscar exámenes | CA-HU07-1 a 4 | 4.0 | D4 | `ExamenService.buscarExamenes`, `ExamenRepository.buscarPorCriterio` | `ExamenServiceTest.buscarExamenPorIdYPorNombre` | ✅ |
| HU-08 Modificar examen | CA-HU08-1 a 2 | 4.0 | D4 | `ExamenService.modificarExamen` | `ExamenServiceTest.modificarExamen` | ✅ |
| HU-09 Eliminar examen | CA-HU09-1 a 2 | 4.0 | D4 | `ExamenService.eliminarExamen` | `ExamenServiceTest.eliminarExamen` | ✅ |
| HU-10 Registrar asistencia | CA-HU10-1 a 3 | 3.0 | D3 | `AsistenciaService.registrarAsistencia`, `AsistenciaAlumno` | `AsistenciaTest` (modelo), `AlumnoInscriptoTest` (parcial) | ✅ |
| HU-11 Listar/buscar asistencias | CA-HU11-1 a 3 | 3.0 | D3 | `AsistenciaService.buscarAsistencias`, `AsistenciaRepository.buscarPorCriterio` | — | ✅ |
| HU-12 Modificar asistencia | CA-HU12-1 a 4 | 3.0 | D3 | `AsistenciaService.modificarAsistencia` | `AsistenciaTest` (fecha futura) | ✅ |
| HU-13 Eliminar asistencia | CA-HU13-1 a 2 | 3.0 | D3 | `AsistenciaService.eliminarAsistencia` | — | ✅ |
| HU-14 Registrar nota | CA-HU14-1 a 4 | 5.0 | D2, D4, D5 | `NotaService.registrarNota`, `AlumnoInscripto.agregarNota`, `NotaView.registrarNota` | `NotaServiceTest` | ✅ |
| HU-15 Listar/buscar notas | CA-HU15-1 a 4 | 5.0 | D5 | `NotaService.buscarNotas`, `NotaRepository.buscarPorId` | `NotaServiceTest.buscarNotaPorIdYPorNombreDeExamen` | ✅ |
| HU-16 Modificar nota | CA-HU16-1 a 4 | 5.0 | D5 | `NotaService.modificarNota` | `NotaServiceTest.modificarNotaConValorNuloConservaElValor` | ✅ |
| HU-17 Eliminar nota | CA-HU17-1 a 2 | 5.0 | D5 | `NotaService.eliminarNota` | `NotaServiceTest.eliminarNota` | ✅ |
| HU-18 Ver carreras | CA-HU18-1 | 6.0 | D8 | `ConsultasView.listarCarreras`, `CarreraRepository` | `CarreraTest` (modelo) | ✅ |
| HU-19 Ver materias | CA-HU19-1 | 6.0 | D7, D9 | `ConsultasView.listarMaterias`, `MateriaRepository` | — | ✅ |
| HU-20 Ver profesores | CA-HU20-1 | 6.0 | D10 | `ConsultasView.listarProfesores`, `ProfesorRepository` | — | ✅ |
| HU-21 Ver plan de estudio | CA-HU21-1 | 6.0 | D7 | `ConsultasView.verPlanDeEstudio`, `PlanEstudioRepository` | `PlanEstudioTest` (modelo) | ✅ |
| HU-22 Ver comisiones | CA-HU22-1 | 6.0 | D6 | `ConsultasView.verComisiones`, `ComisionRepository` | `ComisionMateriaTest`, `HorarioClaseTest`, `ModuloHorarioTest` (modelo) | ✅ |
| HU-23 Comisiones por profesor | CA-HU23-1 | 6.0 | D6, D10 | `ConsultasView.verComisionesPorProfesor` | — | ✅ |
| HU-24 Ver alumnos | CA-HU24-1 | 6.0 | D1 | `ConsultasView.listarAlumnos` | `AlumnoRepositoryTest.buscarTodos` | ✅ |
| HU-25 Estado de cursada | Propuestos | — | — | `EstadoCursada` (sin uso) | — | 📋 |
| HU-26 Promedio | Propuestos | — | — | — | — | 📋 |
| HU-27 Correlatividades | Propuestos | — | — | — | — | 📋 |
| HU-28 Consultar situación | Propuestos | 7.0 | D1, D2, D3, D5 | — | — | 📋 |
| HU-29 Constancia regular | Propuestos | 7.0 | D2, D5 | — | — | 📋 |

## 3. Cobertura de pruebas

### 3.1 Inventario

| Clase de prueba | Métodos | Qué cubre |
|---|---|---|
| `AlumnoTest` | 8 | Validaciones y contrato de `Alumno` |
| `AlumnoInscriptoTest` | 9 | Inscripción, asistencias, porcentaje y regularidad |
| `AsistenciaTest` | 9 | Fecha, presente, fecha futura, comparación |
| `CarreraTest` | 6 | Validaciones de `Carrera` |
| `ComisionMateriaTest` | 10 | Comisión, horarios, superposición |
| `HorarioClaseTest` | 10 | Módulos y choques de horario |
| `ModuloHorarioTest` | 11 | Validación de horas y superposición |
| `PlanEstudioTest` | 4 | Validaciones de plan |
| `AlumnoRepositoryTest` | 7 | Alta, búsquedas e inscripciones |
| `AlumnoServiceTest` | 11 | Registro, búsquedas, inscripción y comisiones |
| `ExamenServiceTest` | 5 | Alta con descripción, rechazo de vacía, búsqueda, modificación y baja |
| `NotaServiceTest` | 5 | Alta válida, rango 1-10, modificación, búsqueda y baja |
| `ComisionRepositoryTest` | 4 | Filtro exacto por materia y búsqueda por código |
| `AlumnoControllerTest` | 3 | Inscripción con comisión real y disponibilidad para notas |

**Total:** 105 casos ejecutados (102 métodos, algunos parametrizados).

### 3.2 Cobertura por módulo

| Módulo | Cobertura | Observación |
|---|---|---|
| Alumnos | ✅ Alta | Modelo, repositorio y servicio |
| Inscripciones | ✅ Alta | Servicio, repositorio y controlador (comisión real) |
| Exámenes | ✅ Alta | `ExamenServiceTest` cubre el ciclo completo |
| Asistencias | ⚠️ Parcial | Modelo cubierto; servicio sin pruebas |
| Notas | ✅ Alta | `NotaServiceTest` cubre el ciclo completo |
| Consultas | ⚠️ Baja | Solo modelos auxiliares |

### 3.3 Brechas prioritarias

1. **`AsistenciaServiceTest`**: alta, duplicado por documento y fecha, modificación con conflicto, baja y fecha futura.
2. **Pruebas de vista**: las validaciones de entrada de `ExamenView`, `AlumnoView` y `NotaView` se verificaron manualmente en consola, sin pruebas automatizadas.
3. **`Nota` sin referencia al alumno**: el listado no puede mostrar a quién pertenece cada nota.
4. **Persistencia**: todos los almacenes son en memoria y se pierden al cerrar la aplicación.

## 4. Matriz de reglas

| Regla | Implementación | Prueba |
|---|---|---|
| RN-01 a RN-03 | `Alumno` | `AlumnoTest` |
| RN-04, RN-05 | `NotaService` | `NotaServiceTest` |
| RN-06 a RN-08 | `AsistenciaService` | Parcial |
| RN-09, RN-10 | `AlumnoService` | `AlumnoServiceTest` |
| RN-11, RN-12 | `ComisionMateria`, `HorarioClase`, `ModuloHorario` | ✅ |
| RN-13 | `Examen` | `ExamenServiceTest` |
| RN-14 | `PlanEstudio` | `PlanEstudioTest` |
| RN-15 | Repositorios | Parcial |
| RN-16, RN-17 | Servicios/repositorios | Parcial |
| RN-18 | Servicios | Parcial |
| RD-01, RD-02 | `AlumnoInscripto` | `AlumnoInscriptoTest` |

## 5. Conclusión

La documentación cubre 24 historias implementadas en código, todas operables de punta a punta desde el menú. La deuda principal está en **pruebas del servicio de asistencia y de las vistas**, y en **dos brechas de diseño** ya identificadas: la nota no guarda referencia al alumno y no hay persistencia.
