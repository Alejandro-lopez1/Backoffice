# 10 — Manual de uso

Manual operativo de la aplicación de consola del Backoffice Académico ITEC N°1.

> Este manual describe el comportamiento **real** de la versión actual. Las funciones planificadas o con limitaciones se indican explícitamente.

## 1. Requisitos y ejecución

### 1.1 Requisitos

- JDK 17 o superior.
- Consola (PowerShell, CMD, terminal de Linux/macOS o terminal integrada del IDE).
- Maven es opcional: solo se usa para compilar y ejecutar las pruebas.

### 1.2 Compilar y ejecutar

Con Maven instalado:

```bash
mvn -q compile
java -cp target/classes Main
```

Sin Maven, desde la raíz del proyecto:

```bash
javac -encoding UTF-8 -d target/classes $(find src/main/java -name "*.java")
java -cp target/classes Main
```

En PowerShell, el comando equivalente para compilar es:

```powershell
javac -encoding UTF-8 -d target\classes (Get-ChildItem -Recurse src\main\java -Filter *.java).FullName
java -cp target\classes Main
```

También se puede ejecutar `Main` desde el IDE.

### 1.3 Pruebas automatizadas

```bash
mvn test
```

Se ejecutan 170 casos de JUnit 5. Están cubiertos alumnos, inscripciones, exámenes, asistencias, notas y consultas.

### 1.4 Codificación de la consola

Algunos archivos fuente tienen codificación mixta y los acentos pueden verse como `Opci��n` o `Programaci?n`. Para reducir el problema en Windows:

```powershell
chcp 65001
java "-Dfile.encoding=UTF-8" -cp target\classes Main
```

Aun así, la codificación de la entrada puede alterar los acentos. La inscripción, los exámenes y las notas se eligen por número desde listas, así que no es necesario tipear códigos con tilde. En las búsquedas, escriba el texto como lo muestra la aplicación.

## 2. Inicio y navegación

Al ejecutar `Main` aparece el menú principal:

```text
=== BACKOFFICE ITEC N°1 ===
1. Gestión de alumnos
2. Gestión de exámenes
3. Gestión de asistencias
4. Consultas y reportes
5. Gestión de notas
0. Salir
Opción:
```

| Opción | Módulo | Estado |
|---|---|---|
| 1 | Alumnos e inscripciones | Operativo |
| 2 | Exámenes | Operativo |
| 3 | Asistencias | Operativo |
| 4 | Consultas y reportes | Operativo |
| 5 | Notas | Operativo |

Reglas de navegación:

- Cada submódulo se recorre con números y se abandona con `0`.
- Una opción no numérica o fuera de rango muestra `Opción inválida` y vuelve al menú.
- Los datos viven en memoria: **al elegir `0. Salir` se pierde todo lo cargado**.
- No hay login: cualquier persona que ejecute la aplicación accede a todos los módulos.

## 3. Datos precargados

### 3.1 Alumnos

| Nombre | DNI | Email | Teléfono |
|---|---|---|---|
| Lucas Giorgi | 40123456 | lucas@ejemplo.com | 3764123456 |
| Axel Almada | 41123456 | axel@ejemplo.com | 3764987456 |
| Enrique Lopez | 42123456 | enrique@ejemplo.com | 3764745698 |
| Leonardo Rios | 43123456 | leonardoi@ejemplo.com | 3764456321 |

### 3.2 Carreras y materias

| Carrera | Nombre |
|---|---|
| TSP | Tecnicatura Superior en Programacion |
| TAS | Tecnicatura en Analisis de Sistemas |
| LDS | Licenciatura en Desarrollo de Software |

| Materia |
|---|
| Programación I |
| Programación II |
| Base de Datos |

### 3.3 Planes de estudio

| Carrera | Año | Materia |
|---|---|---|
| TSP | 1 | Programación I |
| TSP | 2 | Programación II |
| TSP | 2 | Base de Datos |
| TAS | 1 | Programación I |
| TAS | 1 | Base de Datos |
| TAS | 2 | Programación II |
| LDS | 1 | Programación I |
| LDS | 2 | Base de Datos |
| LDS | 3 | Programación II |

### 3.4 Profesores

| Apellido, nombre | Email | Teléfono |
|---|---|---|
| García, Roberto | roberto.garcia@itec.edu.ar | 1122334455 |
| Fernández, Laura | laura.fernandez@itec.edu.ar | 1133445566 |
| López, Martín | martin.lopez@itec.edu.ar | 1144556677 |
| Sánchez, Patricia | patricia.sanchez@itec.edu.ar | 1155667788 |

### 3.5 Comisiones

| Código | Materia | Profesor | Cuatrimestre | Horarios |
|---|---|---|---|---|
| Comisión-1 | Programación I | Roberto García | 2026C1 | Lunes y miércoles 08:00–10:00 |
| Comisión-2 | Programación I | Laura Fernández | 2026C1 | Martes y jueves 18:00–20:00 |
| Comisión-3 | Programación II | Martín López | 2026C2 | Lunes y miércoles 10:00–12:00 |
| Comisión-4 | Base de Datos | Roberto García | 2026C2 | Martes y viernes 14:00–16:00 |

### 3.6 Datos vacíos al iniciar

- Inscripciones.
- Asistencias.
- Exámenes.
- Notas.

## 4. Módulo 1 — Alumnos e inscripciones

```text
=== GESTIÓN DE ALUMNOS E INSCRIPCIONES ===
1. Registrar alumno
2. Buscar alumno
3. Listar todos los alumnos
4. Inscribir alumno en comisión
5. Ver comisiones de un alumno
0. Volver
```

### 4.1 Registrar alumno

1. Elegir `1`.
2. Completar: `Nombre`, `Apellido`, `DNI`, `Email`, `Teléfono`.
3. El sistema muestra `Alumno registrado: <nombre> <apellido> (DNI: <dni>)`.

Validaciones:

- Nombre, apellido y DNI son obligatorios.
- El DNI no puede repetirse.
- Email y teléfono son opcionales; se pueden dejar vacíos.

Ejemplo:

```text
Nombre: Zoe
Apellido: Prueba
DNI: 50000001
Email: zoe@mail.com
Teléfono: 3764000001
Alumno registrado: Zoe Prueba (DNI: 50000001)
```

Si el DNI ya existe:

```text
Error: Ya existe un alumno con el DNI 40123456
```

### 4.2 Buscar alumno

1. Elegir `2`.
2. Ingresar `DNI, nombre o apellido`.

Comportamiento:

- Si el texto coincide exactamente con un DNI, devuelve ese alumno.
- Si no, busca coincidencias parciales en nombre o apellido, sin distinguir mayúsculas.
- Si el criterio está vacío, lista todos.
- Si no hay coincidencias: `No se encontraron alumnos.`

### 4.3 Listar todos los alumnos

Elegir `3`. Muestra los cuatro alumnos precargados y los agregados en la sesión.

### 4.4 Inscribir alumno en comisión

1. Elegir `4`.
2. Ingresar `DNI del alumno`.
3. El sistema muestra las comisiones disponibles con materia, profesor y horarios.
4. Elegir la comisión por número.

Ejemplo:

```text
DNI del alumno: 40123456

--- COMISIONES DISPONIBLES ---
  1. Comisión-1 - Programación I - Roberto García
     Lunes: [Módulo 1 (08:00 - 10:00)]
     Miércoles: [Módulo 1 (08:00 - 10:00)]
  2. Comisión-2 - Programación I - Laura Fernández
     Martes: [Módulo 4 (18:00 - 20:00)]
     Jueves: [Módulo 4 (18:00 - 20:00)]
  ...
Seleccione comisión: 1
Inscripción exitosa: Lucas Giorgi inscripto en Programación I - Comisión Comisión-1
```

Validaciones:

- El alumno debe existir.
- No se permite inscribir dos veces al mismo alumno en la misma comisión.
- No se permite superponer horarios con otra comisión del mismo alumno.
- La inscripción usa la comisión real, por lo que conserva profesor, cuatrimestre y horarios.
- Si la lista está vacía, muestra `No hay comisiones disponibles.`
- Una opción fuera de rango se rechaza con `Opción inválida, intente nuevamente.`

### 4.5 Ver comisiones de un alumno

1. Elegir `5`.
2. Ingresar el DNI.

Muestra cada inscripción como `<alumno> inscripto en <materia> - Comisión <código>`. Si no tiene ninguna: `El alumno no está inscripto en ninguna comisión.`

## 5. Módulo 2 — Exámenes

```text
=== GESTIÓN DE EXÁMENES ===
1. Registrar examen
2. Listar exámenes
3. Buscar examen
4. Modificar examen
5. Eliminar examen
0. Volver
```

> Las comisiones se eligen por número, así que no es necesario conocer el código de memoria.

### 5.1 Registrar examen

1. Elegir `1`.
2. Seleccionar carrera por código (`TSP`, `TAS`, `LDS`).
3. Seleccionar materia por número.
4. Seleccionar comisión por número.
5. Ingresar fecha con formato `dd-MM-yyyy` (por ejemplo, `15-11-2026`).
6. Ingresar el título del examen.
7. Ingresar la descripción.
8. Elegir tipo: `1` Parcial, `2` Recuperatorio, `3` Final.

Ejemplo:

```text
Seleccione código de carrera: TSP
Seleccione materia: 1
Seleccione comisión: 1
Fecha (dd-MM-aaaa): 15-11-2026
Título del examen: Parcial 1
Descripción: Unidades 1 a 3
Tipo de examen: 1

--- EXAMEN REGISTRADO ---
ID: 001
Comisión: Comisión-1
Materia: Programación I
Profesor: Roberto García
Nombre: Parcial 1
Descripción: Unidades 1 a 3
Fecha: 15-11-2026
Tipo: Parcial
```

La descripción es obligatoria: si se deja vacía, el sistema responde `Error: La descripción no puede ser nula o vacía` y el examen no se guarda.

### 5.2 Listar exámenes

Elegir `2`. Muestra ID, comisión, materia, profesor, nombre, descripción, fecha y tipo. Si no hay: `No hay exámenes registrados.`

### 5.3 Buscar examen

1. Elegir `3`.
2. Ingresar un ID numérico o parte del nombre.

- ID numérico: búsqueda exacta.
- Texto: coincidencia parcial en el nombre, sin distinguir mayúsculas.
- Criterio vacío: lista todos.

### 5.4 Modificar examen

1. Elegir `4`.
2. Ingresar el ID.
3. Seleccionar carrera, materia y comisión.
4. Ingresar nombre, descripción, fecha (`dd-MM-yyyy`) y tipo.

Si el ID no existe: `Error: No existe un examen con id <id>`.

### 5.5 Eliminar examen

1. Elegir `5`.
2. Ingresar el ID.

Si existe, muestra `Examen eliminado.`; si no, `Error: No existe un examen con id <id>`. No pide confirmación.

## 6. Módulo 3 — Asistencias

```text
=== GESTIÓN DE ASISTENCIAS ===
1. Registrar asistencia
2. Listar asistencias
3. Buscar asistencia
4. Modificar asistencia
5. Eliminar asistencia
0. Volver
```

### 6.1 Registrar asistencia

1. Elegir `1`.
2. Ingresar `Documento del alumno`.
3. Ingresar `Presente (s/n)`.

La fecha se asigna automáticamente a **hoy**. Se consideran afirmativos `s`, `si`, `sí` y `1`; cualquier otra respuesta registra ausencia.

Ejemplo:

```text
Documento del alumno: 40123456
Presente (s/n): s
Asistencia 001: 40123456 - 27-10-2026
```

Validaciones:

- El documento es obligatorio.
- No se puede registrar dos veces el mismo documento en la misma fecha.
- No se valida que el documento exista en el padrón de alumnos.

Si ya hay una asistencia hoy:

```text
Error: Ya existe una asistencia registrada para el documento 40123456 en la fecha 2026-10-27
```

### 6.2 Listar asistencias

Elegir `2`. Muestra ID, documento, fecha y estado (`Presente`/`Ausente`). Si no hay: `No hay asistencias registradas.`

### 6.3 Buscar asistencia

1. Elegir `3`.
2. Ingresar un ID numérico o un documento.

- ID numérico: búsqueda exacta por ID.
- Texto: búsqueda exacta por documento.
- Criterio vacío: lista todas.

### 6.4 Modificar asistencia

1. Elegir `4`.
2. Ingresar `Id de la asistencia`.
3. Ingresar documento, fecha (`dd-MM-yyyy`) y presencia.

Validaciones:

- El ID debe existir.
- La fecha no puede ser futura.
- No puede quedar duplicada la combinación documento + fecha.
- Una fecha inválida o vacía se interpreta como `null` y muestra `Error: La fecha no puede ser null`.

### 6.5 Eliminar asistencia

1. Elegir `5`.
2. Ingresar el ID.

Muestra `Asistencia eliminada.` o el error correspondiente. No pide confirmación.

## 7. Módulo 4 — Consultas y reportes

```text
=== CONSULTAS Y REPORTES ===
1. Ver todas las carreras
2. Ver todas las materias
3. Ver todos los profesores
4. Ver plan de estudio de una carrera
5. Ver todas las comisiones
6. Ver comisiones por profesor
7. Ver todos los alumnos
0. Volver
```

| Opción | Qué muestra | Datos que pide |
|---|---|---|
| 1 | Código y nombre de las tres carreras | — |
| 2 | Materias con carreras/años y comisiones asociadas | — |
| 3 | Profesores con email, teléfono, materias y carreras | — |
| 4 | Materias agrupadas por año | Código de carrera |
| 5 | Comisiones con materia, profesor, cuatrimestre y horarios | — |
| 6 | Comisiones de un profesor | Apellido |
| 7 | Alumnos con DNI, email y teléfono | — |

Ejemplos:

- Para el plan de TSP, ingresar `TSP`.
- Para las comisiones de Roberto García, ingresar `García` (o `Garcia`, porque la búsqueda ignora mayúsculas pero no tildes).

La opción 2 muestra cada materia y, debajo, sus carreras y comisiones. La opción 5 permite revisar la oferta completa de comisiones antes de inscribir.

## 8. Módulo 5 — Notas

```text
=== GESTIÓN DE NOTAS ===
1. Registrar nota
2. Listar notas
3. Buscar nota
4. Modificar nota
5. Eliminar nota
0. Volver
```

### 8.1 Registrar nota

1. Elegir `1`.
2. Seleccionar carrera, materia y comisión.
3. Seleccionar un alumno inscripto en esa comisión.
4. Seleccionar un examen de esa comisión.
5. Ingresar la nota (`1` a `10`, admite decimales).
6. Ingresar una observación opcional.

Si no hay exámenes:

```text
Error: No hay exámenes registrados para Comisión-1
```

Si no hay inscriptos:

```text
Error: No hay alumnos inscriptos en Comisión-1
```

El alumno aparece en la lista porque la inscripción se hizo sobre la comisión real desde el módulo 1. Si no aparece, verifique con `1 → 5` que esté inscripto en esa comisión.

Ejemplo:

```text
Seleccione código de carrera: TSP
Seleccione materia: 1
Seleccione comisión: 1

--- ALUMNOS INSCRIPTOS EN COMISIÓN-1 ---
  1. Lucas Giorgi (DNI: 40123456)
Seleccione alumno: 1

--- EXÁMENES DE COMISIÓN-1 ---
  1. Parcial 1 - 15-11-2026
Seleccione examen: 1
Nota (1-10): 8
Observación (opcional): Aprobado

--- NOTA REGISTRADA ---
ID: 001
Examen: Parcial 1
Materia: Programación I
Comisión: Comisión-1
Valor: 8.0
Observación: Aprobado
Fecha de registro: 27-10-2026
```

### 8.2 Listar notas

Elegir `2`. Muestra ID, examen, materia, comisión, valor, observación y fecha de registro. **No muestra el alumno**, porque `Nota` no guarda esa referencia.

### 8.3 Buscar nota

1. Elegir `3`.
2. Ingresar un ID numérico o parte del nombre del examen.

- ID numérico: búsqueda exacta.
- Texto: coincidencias parciales en el nombre del examen.
- Criterio vacío: lista todas.

### 8.4 Modificar nota

1. Elegir `4`.
2. Ingresar el ID.
3. Ingresar la nueva nota (`1` a `10`).
4. Ingresar la observación.

La vista siempre pide un valor; si se deja vacío o fuera de rango, vuelve a pedirlo. La posibilidad de conservar el valor anterior enviando `null` existe solo a nivel de servicio.

### 8.5 Eliminar nota

1. Elegir `5`.
2. Ingresar el ID.

Muestra `Nota eliminada.` o `Error: No existe una nota con id <id>`. No pide confirmación.

## 9. Flujos de ejemplo

### 9.1 Alta de alumno, búsqueda e inscripción

```text
1 → 1 → Zoe / Prueba / 50000001 / zoe@mail.com / 3764000001
1 → 2 → 50000001
1 → 4 → 40123456 → 1
1 → 5 → 40123456
4 → 5
```

Resultado esperado:

- El alumno Zoe Prueba queda en el padrón.
- Lucas Giorgi queda inscripto en Comisión-1, con su profesor y horarios.
- La consulta de comisiones de Lucas muestra la inscripción.
- La opción 5 de consultas muestra las cuatro comisiones con horarios.

### 9.2 Registro de asistencia

```text
3 → 1 → 40123456 / s
3 → 2
3 → 3 → 40123456
```

Resultado esperado:

- Se crea una asistencia con la fecha de hoy.
- El listado muestra `Presente`.
- La búsqueda por documento devuelve esa asistencia.
- Un segundo registro del mismo documento en el mismo día falla por duplicado.

### 9.3 Alta de examen y nota

Requiere que el alumno esté inscripto (9.1).

```text
2 → 1 → TSP → 1 → 1 → 15-11-2026 → Parcial 1 → Unidades 1 a 3 → 1
5 → 1 → TSP → 1 → 1 → 1 → 1 → 8 → Aprobado
5 → 2
```

Resultado esperado:

- Se crea el examen `Parcial 1` con ID `001`, descripción `Unidades 1 a 3`, fecha `15-11-2026` y tipo `Parcial`.
- Al registrar la nota, el alumno Lucas Giorgi aparece entre los inscriptos de Comisión-1.
- Se guarda la nota `8.0` con observación `Aprobado`.
- El listado de notas muestra la nota con su examen, materia, comisión, valor, observación y fecha.

## 10. Mensajes y errores frecuentes

| Mensaje | Causa | Qué hacer |
|---|---|---|
| `Opción inválida` | Texto no numérico o número fuera de rango | Ingresar una opción del menú |
| `Error: Ya existe un alumno con el DNI ...` | DNI duplicado | Buscar el alumno existente |
| `Error: No se encontró alumno con DNI ...` | El DNI no está en el padrón | Registrarlo primero |
| `Error: El alumno ... ya está inscripto en la comisión ...` | Inscripción duplicada | Consultar sus comisiones |
| `Error: La descripción no puede ser nula o vacía` | Se dejó vacía la descripción del examen | Completar el campo |
| `Error: No hay exámenes registrados para ...` | No existen exámenes de la comisión | Registrar un examen para esa comisión |
| `Error: No hay alumnos inscriptos en ...` | No hay inscripciones en esa comisión | Inscribir al alumno con `1 → 4` |
| `Error: La nota debe ser un valor entre 1 y 10` | Valor fuera de rango | Ingresar 1–10 |
| `Error: No existe un examen con id ...` | ID inexistente | Verificar el ID |
| `Error: No existe una asistencia con id ...` | ID inexistente | Verificar el ID |
| `Error: No existe una nota con id ...` | ID inexistente | Verificar el ID |
| `Error: La fecha no puede ser null` | Fecha vacía o con formato inválido | Usar `dd-MM-yyyy` |
| `Error: No se puede registrar una asistencia con fecha futura: ...` | Fecha posterior a hoy | Usar hoy o una fecha pasada |
| `Error: Ya existe una asistencia registrada para el documento ...` | Duplicado documento + fecha | Modificar la existente |
| `No se encontraron alumnos.` | Búsqueda sin coincidencias | Probar otro criterio |
| `No hay exámenes registrados.` | Repositorio vacío | Registrar un examen con `2 → 1` |
| `No hay asistencias registradas.` | Sin registros | Registrar una asistencia |
| `No hay notas registradas.` | Sin registros | Registrar una nota con `5 → 1` |

## 11. Limitaciones conocidas

### 11.1 Notas sin referencia al alumno

`Nota` guarda examen, valor, observación y fecha, pero no el alumno. La relación existe solo en `AlumnoInscripto.notas`, que no se reconstruye al listar. Por eso el listado de notas no permite saber a quién pertenecen.

### 11.2 Asistencias duplicadas en el modelo

Hay dos representaciones: `AsistenciaAlumno` (repositorio y menú) y `Asistencia` (dentro de `AlumnoInscripto`). Registrar una no actualiza la otra, por lo que el porcentaje de asistencia y la regularidad calculados en el modelo no reflejan lo cargado desde el menú.

### 11.3 Codificación mixta

Varios archivos fuente tienen acentos mal codificados. La consola puede mostrar `Opci��n`, `Programaci?n` o `Comisi?n`. Esto afecta la legibilidad. La selección por número evita tener que tipear códigos con tilde.

### 11.4 Sin persistencia ni autenticación

- Todo se guarda en `ArrayList` en memoria; al salir se pierde.
- No hay login, roles ni permisos.
- No hay auditoría de operaciones.

### 11.5 Sin confirmación de borrado

Las bajas de exámenes, asistencias y notas se ejecutan directamente al ingresar el ID.

### 11.6 Cobertura de pruebas incompleta

Hay 170 casos. Existen pruebas para alumnos, inscripciones, exámenes, asistencias, notas y consultas; las validaciones de entrada de las vistas siguen verificándose manualmente.

## 12. Preguntas frecuentes

**¿Por qué no aparece el alumno al registrar una nota?**
Porque no hay inscripciones en esa comisión. Verifique con `1 → 5` que el alumno esté inscripto y con `4 → 5` que la comisión exista.

**¿Por qué se pierden los datos al cerrar?**
Porque no hay persistencia. Es una limitación de diseño de esta versión (RNF-01).

**¿Cómo sé qué comisión elegir?**
El sistema muestra la lista de comisiones con materia, profesor y horarios; se elige por número. También puede consultarlas con `4 → 5`.

**¿Puedo cargar una asistencia de una fecha anterior?**
El alta desde el menú siempre usa la fecha de hoy. Para otra fecha, registre y luego modifique con `3 → 4`; no se permiten fechas futuras.

**¿Las notas admiten decimales?**
Sí, cualquier valor entre 1 y 10 (por ejemplo, `7.5`). Se muestran como `7.5` o `8.0` según el valor.

**¿Qué pasa si ingreso una letra en un menú?**
Se interpreta como opción inválida (`-1`) y se muestra `Opción inválida`.

**¿Cómo salgo?**
`0` en el menú principal. Los datos de la sesión se descartan.

## 13. Referencias

- [Alcance](00-alcance.md)
- [Actores y glosario](01-actores-glosario.md)
- [Reglas de negocio](02-reglas-negocio.md)
- [Historias de usuario](03-historias-usuario.md)
- [Criterios de aceptación](04-criterios-aceptacion.md)
- [DFD 0](05-dfd-0-contexto.md) y [DFD 1](06-dfd-1-sistema.md)
- [Diccionario de datos](07-diccionario-datos.md)
- [Trazabilidad](08-trazabilidad.md)
- [Modelo de dominio](09-modelo-dominio.md)
