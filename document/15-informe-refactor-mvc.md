# 15 — Informe del refactor MVC

## 1. Alcance

- Rama de trabajo: `refactor/mvc-capas`, creada desde `dfa7640` (HEAD limpio de `chore/orden-repo`) y no desde `master` (`f0003f4`).
- Objetivo: ordenar las dependencias en `view → controller → service → repository → model` sin cambiar el comportamiento observable.
- Base de comparación: `dfa7640` (120 casos de prueba).
- Resultado del refactor: 5 commits de refactor y 170 casos de prueba en verde.
- Trabajo parcial previo: se guardó en `wip/refactor-parcial` (commit `a9b6dc7`) **solo como referencia**. No se restauró, no se mezcló y no se volvió a tocar.
- No se hizo `git push`, no se borraron ramas y no se usó `--force`.

## 2. Auditoría inicial: violaciones V01–V20

Leyenda: ✅ corregida · ⏳ pendiente/documentada.

| ID | Violación detectada en la base | Evidencia antes | Estado | Dónde se resolvió |
|---|---|---|---|---|
| V01 | Vistas importaban y usaban repositorios directamente. | `MenuView`, `ExamenView`, `NotaView`, `ConsultasView` con `import ar.edu.itec.repository.*`. | ✅ | Fase 4: las vistas solo usan controladores y modelos. |
| V02 | `MenuView` instanciaba servicios concretos. | `new AlumnoService(...)`, `new ExamenService(...)`, etc. dentro del constructor. | ✅ | Fase 4/5: `MenuView` recibe las vistas ya construidas. |
| V03 | La composición de dependencias estaba en la vista. | `MenuView` era la raíz de composición real. | ✅ | Fase 5: `config/AppConfig.construirMenuView()`. |
| V04 | Repositorios y `Scanner` se construían en la capa de presentación. | `new CarreraRepository()`, `new Scanner(System.in)` en `MenuView`. | ✅ | Fase 5: una instancia de cada repositorio y un solo `Scanner` en `AppConfig`. |
| V05 | Las vistas conocían implementaciones concretas de servicios. | Constructores con `AsistenciaService`, `NotaService`, `ExamenService` concretos. | ✅ | Fase 2/4: interfaces en `service/` e implementaciones en `service/impl/`. |
| V06 | `AlumnoController` dependía de `AlumnoRepository`. | Campo `AlumnoRepository repository` y `listarAlumnos()` contra el repositorio. | ✅ | Fase 3: `listarAlumnos()` delega en `AlumnoService`. |
| V07 | `AlumnoController` dependía de `CarreraRepository` para un método sin llamadores. | `listarCarrerasDisponibles()` en el controlador. | ✅ | Fase 3: método eliminado tras verificar con `rg` que no tenía llamadores. |
| V08 | `AlumnoController` dependía de `ComisionRepository` y resolvía la comisión. | `comisionRepository.buscarPorCodigo(...)` dentro del controlador. | ✅ | Fase 3: `AlumnoServiceImpl.inscribirAlumnoComision(dni, codigoComision)`. |
| V09 | El controlador contenía lógica de negocio de inscripción. | Resolución de comisión + llamada al servicio con objetos ya armados. | ✅ | Fase 3: la lógica y las validaciones viven en `AlumnoServiceImpl`. |
| V10 | `AlumnoService` era una clase concreta sin interfaz. | `public class AlumnoService`. | ✅ | Fase 2: `AlumnoService` (interfaz) + `AlumnoServiceImpl`. |
| V11 | Los servicios de asistencia, nota y examen eran concretos y no respetaban los nombres pedidos. | `AsistenciaService`, `NotaService`, `ExamenService` en `service/`. | ✅ | Fase 2: `AlumnoAsistenciaService`, `AlumnoNotaService`, `ProfesorExamenService` + `*Impl`. |
| V12 | No existían servicios de consulta; las vistas iban directo a repositorios. | `ConsultasView` con seis repositorios inyectados. | ✅ | Fase 2: `CarreraService`, `MateriaService`, `PlanEstudioService`, `ComisionService`, `ProfesorConsultaService`. |
| V13 | `ConsultasView` armaba y filtraba datos académicos. | Filtros de planes/materias y recorridos de comisiones en la vista. | ✅ | Fase 4: delegación en `ConsultasController`. |
| V14 | `ExamenView` y `NotaView` resolvían carrera → plan → materia → comisión contra repositorios. | Constructores con `CarreraRepository`, `PlanEstudioRepository`, `ComisionRepository`, etc. | ✅ | Fase 4: selección resuelta con `ConsultasController`. |
| V15 | No había una fachada única de lectura; la lógica estaba duplicada entre vistas. | Búsquedas repetidas en `ExamenView`, `NotaView` y `ConsultasView`. | ✅ | Fase 3/4: un único `ConsultasController` con delegaciones de una línea. |
| V16 | La inscripción quedaba repartida entre controlador y servicio; el servicio no conocía la comisión real. | `AlumnoService` recibía solo `AlumnoRepository`. | ✅ | Fase 2/3: `AlumnoServiceImpl(AlumnoRepository, ComisionRepository)`, sin service → service. |
| V17 | Los controladores dependían de clases concretas en lugar de contratos. | Constructores con servicios concretos. | ✅ | Fase 2/3: todos los controladores reciben interfaces de servicio. |
| V18 | `Main` y `MenuView` concentraban infraestructura y arranque. | `Main` llamaba a `new MenuView()` y este construía todo. | ✅ | Fase 5: `Main` hace `new AppConfig().construirMenuView().iniciar()`. |
| V19 | `ComisionRepository` no tiene operaciones de inscripción por comisión. | Métodos `guardarAlumnoEnComision`, `listarAlumnosDeComision` y `buscarAlumnoInscriptoEnComision` quedaron como TODO en el trabajo parcial de referencia. | ⏳ | No se restauraron ni mezclaron. La inscripción se persiste en `AlumnoRepository`; limitación documentada en `document/09-modelo-dominio.md`. |
| V20 | Brechas de dominio y de pruebas de presentación. | `Nota` no referencia al alumno; persistencia en memoria; validaciones de vistas solo manuales. | ⏳ | Fuera del alcance del refactor de capas. Documentado en `document/08-trazabilidad.md` y `document/09-modelo-dominio.md`. |

**Resultado:** V01–V18 corregidas; V19–V20 pendientes y documentadas, sin afectar el comportamiento observable ni las capas.

## 3. Verificación de capas

### 3.1 Repositorios prohibidos en vista/controlador

```text
rg -n "ar\.edu\.itec\.repository" src/main/java/ar/edu/itec/view src/main/java/ar/edu/itec/controller
```

Resultado real: **sin coincidencias** (exit code 1).

### 3.2 Servicios prohibidos en vista

```text
rg -n "ar\.edu\.itec\.service" src/main/java/ar/edu/itec/view
```

Resultado real: **sin coincidencias** (exit code 1).

### 3.3 Vista/controlador prohibidos en service/repository/model

```text
rg -n "ar\.edu\.itec\.(view|controller)" src/main/java/ar/edu/itec/service src/main/java/ar/edu/itec/repository src/main/java/ar/edu/itec/model
```

Resultado real: **sin coincidencias** (exit code 1).

## 4. Decisiones de diseño

1. **Nombres de servicios fijados por el profesor**: se conservaron `AlumnoAsistenciaService`, `AlumnoNotaService` y `ProfesorExamenService`. No se renombraron.
2. **Interfaz + implementación**: todos los servicios tienen interfaz en `service/` e implementación en `service/impl/`.
3. **Servicios de consulta nuevos**: `CarreraService`, `MateriaService`, `PlanEstudioService`, `ComisionService` y `ProfesorConsultaService`. Este último expone `List<Profesor> listarProfesores()`.
4. **Inscripción**: `AlumnoServiceImpl(AlumnoRepository, ComisionRepository)` implementa `inscribirAlumnoComision(String dni, String codigoComision)` con el orden literal alumno → comisión → duplicado → superposición horaria. Se conservó el overload `(Alumno, ComisionMateria)`. No hay dependencia service → service.
5. **`AlumnoController`**: depende solo de `AlumnoService` y `ComisionService`. Se eliminó `listarCarrerasDisponibles()` después de verificar con `rg` que no tenía llamadores (ni en tests). Se conservó `listarComisionesDisponibles()`.
6. **`ConsultasController`**: una sola fachada de lectura con las siete dependencias pedidas. Cada método es una delegación de una línea, sin lógica.
7. **`ComisionRepository`**: los TODO de las líneas 109-122 pertenecen al trabajo parcial guardado en `wip/refactor-parcial`; **no existen en la base `dfa7640`** y no se restauraron. Se documentaron como limitación en `document/09-modelo-dominio.md` (V19).
8. **Raíz de composición**: `config/AppConfig.construirMenuView()` crea una sola instancia de cada repositorio y un solo `Scanner`, y los inyecta en servicios, controladores y vistas. `MenuView` solo contiene lógica de menú.
9. **`Main`**: quedó en `new AppConfig().construirMenuView().iniciar()`.

## 5. Decisiones de pruebas

1. Se renombraron `NotaServiceTest` → `AlumnoNotaServiceTest` y `ExamenServiceTest` → `ProfesorExamenServiceTest`.
2. Se crearon `AlumnoAsistenciaServiceTest`, `CarreraServiceTest`, `MateriaServiceTest`, `PlanEstudioServiceTest`, `ComisionServiceTest`, `ProfesorConsultaServiceTest` y `ConsultasControllerTest`.
3. `AlumnoControllerTest` y `AlumnoServiceTest` se adaptaron a los constructores nuevos sin debilitar aserciones.
4. Se agregaron pruebas de `listarAlumnos()` y de `inscribirAlumnoComision(dni, codigo)` con código inexistente.
5. En `AlumnoAsistenciaServiceTest` se usaron documentos **no numéricos** (`DOC-...`) para buscar por documento. Esto refleja el comportamiento preexistente de `AsistenciaRepository.buscarPorCriterio`: un criterio numérico se interpreta como ID, no como documento. No se modificó el código de producción.
6. Se ejecutó `mvn clean test` para eliminar clases compiladas viejas de los tests renombrados.

## 6. Decisiones de documentación

- Se actualizaron los nombres definitivos en todo `document/` (`02`, `03`, `06`, `08`, `09`, `10`) y en `README.md`.
- `document/08-trazabilidad.md` quedó con el conteo real: 167 métodos, 170 casos y 21 clases.
- No se modificó ningún diagrama (`.drawio`, `.svg`, `.jpg`) ni el Mermaid de `09-modelo-dominio.md`.
- Este informe reemplaza los archivos sueltos `REFACTOR-auditoria.md` y `REFACTOR-decisiones.md`, que quedaban en la raíz sin commitear. Se unificaron en este documento dentro de `document/`.

## 7. Commits de la rama

| Fase | Commit | Mensaje |
|---|---|---|
| 2 | `4875ff8` | `refactor(service): formaliza interfaces y agrega servicios de consulta` |
| 3 | `45ac2a4` | `refactor(controller): delega alumnos en servicios y agrega ConsultasController` |
| 4 | `a7a0e01` | `refactor(view): desacopla vistas de repositorios y servicios` |
| 5 | `ef65083` | `refactor(config): mueve la composicion de dependencias a AppConfig` |
| 6 | `360c762` | `refactor(test): renombra pruebas y cubre servicios y consultas` |

Después de cada fase se ejecutó `mvn test` con resultado verde.

## 8. Pruebas y verificación manual

| Momento | Casos | Fallos | Errores | Omitidos |
|---|---:|---:|---:|---:|
| Base `dfa7640` | 120 | 0 | 0 | 0 |
| `HEAD` del refactor | 170 | 0 | 0 | 0 |

Detalle del último `mvn test`: 21 clases, 167 métodos declarados y 170 casos ejecutados (la diferencia proviene de los `@ParameterizedTest` de `AlumnoTest`).

Se scripteó el menú real con este recorrido:

1. Registrar alumno `Juan Manual` (DNI `55555555`).
2. Inscribirlo en `Comisión-1` (Programación I).
3. Registrar el examen `Parcial Manual` para `Comisión-1`.
4. Registrar una nota `8.0` con observación `Prueba manual`.

Salida relevante:

```text
Alumno registrado: Juan Manual (DNI: 55555555)
Inscripción exitosa: Juan Manual inscripto en Programación I - Comisión Comisión-1
Examen registrado: Parcial Manual - Comisión-1
Nota registrada: Parcial Manual - Valor 8.0 - Observación Prueba manual
```

El menú solo permite elegir comisiones listadas, por lo que el **código inexistente** se verificó con una invocación directa y scripteada al servicio:

```text
VALIDO: Lucas Giorgi inscripto en Programación I - Comisión Comisión-1
INEXISTENTE: No existe la comisión con código COM-999
```

## 9. Entorno

`mvn` no está en `PATH`. Las pruebas se ejecutaron con:

```powershell
& "C:\Users\Windows 10 Pro\.m2\wrapper\dists\apache-maven-3.9.11\03d7e36a140982eea48e22c1dcac01d8862b2550b2939e09a0809bbc5182a5bc\bin\mvn.cmd" test
```

JDK 17.0.20.1. No se modificó `pom.xml` ni el entorno del proyecto.

## 10. Pendientes reales

1. `ComisionRepository` no implementa las operaciones de inscripción por comisión (V19).
2. `Nota` no guarda referencia al alumno; la relación solo existe en `AlumnoInscripto` (V20).
3. Persistencia en memoria: los datos se pierden al cerrar (V20).
4. Las validaciones de entrada de las vistas siguen sin pruebas automatizadas (V20).
