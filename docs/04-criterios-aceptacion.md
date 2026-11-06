# 04 — Criterios de aceptación

## 1. Convenciones

Cada criterio se identifica como `CA-HUxx-n` y se escribe en formato **Given/When/Then** (Dado/Cuando/Entonces).

- **Ejecutable desde el menú:** el escenario se puede reproducir con la aplicación en consola.
- **Nivel servicio:** el escenario se verifica invocando el servicio o el modelo (no hay opción de menú que lo provoque).
- **Propuesto:** comportamiento esperado que todavía no está implementado (backlog o corrección pendiente); no es verificable en la versión actual.

### Datos de prueba sugeridos

| Dato | Valor |
|---|---|
| Alumno existente (semilla) | DNI `40123456` |
| Alumno nuevo | DNI `50000001`, nombre `Zoe`, apellido `Prueba` |
| Carrera | `TSP` — Tecnicatura Superior en Programación |
| Materia | `Programación I` |
| Comisión de semilla | `Comisión-1` |
| Fecha de examen válida | `15-11-2026` |

---

## 2. Alumnos y padrón

### HU-01 · Registrar alumno

**CA-HU01-1 · Alta exitosa** *(menú)*
```gherkin
Dado que no existe un alumno con DNI 50000001
Cuando ingreso nombre "Zoe", apellido "Prueba", DNI "50000001", email "zoe@mail.com" y teléfono "3764000001"
Entonces el sistema muestra "Alumno registrado: Zoe Prueba (DNI: 50000001)"
Y el alumno aparece en el listado
```

**CA-HU01-2 · DNI duplicado** *(menú)*
```gherkin
Dado un alumno registrado con DNI 40123456
Cuando intento registrar otro alumno con el mismo DNI
Entonces el sistema muestra "Error: Ya existe un alumno con el DNI 40123456"
Y el padrón no se modifica
```

**CA-HU01-3 · Campos obligatorios vacíos** *(menú)*
```gherkin
Cuando dejo vacío el nombre, el apellido o el DNI
Entonces el sistema rechaza el alta
Y muestra un mensaje que indica que el campo no puede ser nulo o vacío
```

**CA-HU01-4 · Email y teléfono opcionales** *(menú / modelo)*
```gherkin
Cuando registro un alumno con email y teléfono vacíos
Entonces el alta se completa correctamente
Y el alumno queda con email y teléfono nulos
```

### HU-02 · Buscar alumno

**CA-HU02-1 · Búsqueda por DNI** *(menú)*
```gherkin
Dado un alumno con DNI 50000001
Cuando busco "50000001"
Entonces el sistema muestra exactamente ese alumno
```

**CA-HU02-2 · Búsqueda parcial por nombre o apellido** *(menú)*
```gherkin
Dado un alumno llamado "Zoe Prueba"
Cuando busco "Zoe" o "Prue"
Entonces el sistema muestra el alumno encontrado
```

**CA-HU02-3 · Sin resultados** *(menú)*
```gherkin
Cuando busco "Inexistente"
Entonces el sistema no muestra alumnos
```

**CA-HU02-4 · Criterio vacío** *(menú)*
```gherkin
Cuando busco sin ingresar texto
Entonces el sistema muestra todos los alumnos registrados
```

### HU-03 · Listar alumnos

**CA-HU03-1 · Listado completo** *(menú)*
```gherkin
Cuando selecciono "Listar todos los alumnos"
Entonces el sistema muestra los alumnos existentes
Y se incluyen los cuatro alumnos precargados
```

### HU-04 · Inscribir alumno en comisión

**CA-HU04-1 · Inscripción exitosa** *(menú)*
```gherkin
Dado un alumno con DNI 40123456 y la comisión "Comisión-1" de "Programación I"
Cuando ingreso el DNI y selecciono "Comisión-1" de la lista de comisiones disponibles
Entonces el sistema muestra "Inscripción exitosa"
Y la inscripción conserva el profesor y los horarios de la comisión real
Y la comisión aparece al consultar las comisiones del alumno
```

**CA-HU04-2 · Inscripción duplicada** *(menú)*
```gherkin
Dado un alumno ya inscripto en la comisión "Comisión-1"
Cuando intento inscribirlo nuevamente en esa comisión
Entonces el sistema muestra un error indicando que ya está inscripto
```

**CA-HU04-3 · Alumno inexistente** *(menú)*
```gherkin
Cuando intento inscribir un DNI que no existe
Entonces el sistema muestra "No se encontró alumno con DNI ..."
```

**CA-HU04-4 · Superposición horaria** *(nivel servicio)*
```gherkin
Dado un alumno inscripto en una comisión con horario lunes 18:00-20:00
Cuando el servicio intenta inscribirlo en otra comisión con horario lunes 19:00-21:00
Entonces se lanza IllegalArgumentException por superposición horaria
Y no se guarda la segunda inscripción
```
> Las comisiones precargadas no se superponen entre sí, por lo que el escenario se verifica con comisiones construidas en la prueba, no desde el menú. La vista ya opera con comisiones reales que tienen horarios.

**CA-HU04-5 · Comisión inexistente** *(nivel servicio)*
```gherkin
Cuando intento inscribir a un alumno con un código de comisión que no pertenece a la estructura académica
Entonces el sistema muestra "No existe la comisión con código ..."
Y no se guarda la inscripción
```

### HU-05 · Consultar comisiones de un alumno

**CA-HU05-1 · Alumno con inscripciones** *(menú)*
```gherkin
Dado un alumno inscripto en una comisión
Cuando consulto sus comisiones por DNI
Entonces el sistema lista la inscripción con código, materia y fecha
```

**CA-HU05-2 · Alumno sin inscripciones** *(menú)*
```gherkin
Dado un alumno sin inscripciones
Cuando consulto sus comisiones
Entonces el sistema no muestra comisiones
```

---

## 3. Exámenes

### HU-06 · Registrar examen

**CA-HU06-1 · Alta exitosa** *(menú)*
```gherkin
Dado la carrera TSP, la materia "Programación I" y la comisión "Comisión-1"
Cuando ingreso título "Parcial 1", descripción "Unidades 1 a 3", fecha "15-11-2026" y tipo "Parcial"
Entonces el sistema registra el examen con un ID generado
Y el examen aparece en el listado
```

**CA-HU06-2 · Descripción vacía** *(menú)*
```gherkin
Dado que completo título, fecha y tipo
Cuando dejo vacía la descripción
Entonces el sistema rechaza el registro con "La descripción no puede ser nula o vacía"
Y el examen no se registra
```

**CA-HU06-3 · Título vacío** *(menú)*
```gherkin
Cuando dejo vacío el título
Entonces el sistema rechaza el registro con "El nombre no puede ser nulo o vacío"
```

**CA-HU06-4 · Tipo de evaluación inválido** *(modelo)*
```gherkin
Cuando se intenta crear un examen con tipo nulo
Entonces el modelo lanza IllegalArgumentException
```

### HU-07 · Listar y buscar exámenes

**CA-HU07-1 · Listado completo** *(menú)*
```gherkin
Cuando selecciono "Listar exámenes"
Entonces el sistema muestra todos los exámenes con su ID, comisión, materia, fecha y tipo
```

**CA-HU07-2 · Búsqueda por ID** *(menú)*
```gherkin
Cuando ingreso el ID de un examen existente
Entonces el sistema muestra solo ese examen
```

**CA-HU07-3 · Búsqueda por nombre** *(menú)*
```gherkin
Cuando ingreso parte del nombre de un examen
Entonces el sistema muestra los exámenes cuyo nombre contiene ese texto
```

**CA-HU07-4 · Criterio vacío** *(menú)*
```gherkin
Cuando busco sin ingresar texto
Entonces el sistema lista todos los exámenes
```

### HU-08 · Modificar examen

**CA-HU08-1 · Modificación exitosa** *(menú)*
```gherkin
Dado un examen existente
Cuando ingreso su ID y cambio la fecha o el tipo
Entonces el sistema guarda los nuevos datos
Y se reflejan al consultar el examen
```

**CA-HU08-2 · Examen inexistente** *(menú)*
```gherkin
Cuando ingreso un ID que no existe
Entonces el sistema muestra "No existe un examen con id ..."
```

### HU-09 · Eliminar examen

**CA-HU09-1 · Baja exitosa** *(menú)*
```gherkin
Dado un examen existente
Cuando ingreso su ID en "Eliminar examen"
Entonces el examen deja de aparecer en el listado
```

**CA-HU09-2 · Examen inexistente** *(menú)*
```gherkin
Cuando ingreso un ID que no existe
Entonces el sistema muestra un error y no elimina nada
```

---

## 4. Asistencias

### HU-10 · Registrar asistencia del día

**CA-HU10-1 · Registro de presente** *(menú)*
```gherkin
Dado que hoy no existe asistencia para el documento 40123456
Cuando registro al alumno como presente
Entonces el sistema guarda la asistencia con la fecha de hoy
Y la muestra en el listado
```

**CA-HU10-2 · Asistencia duplicada en la fecha** *(menú)*
```gherkin
Dado que ya existe una asistencia para el documento 40123456 en la fecha de hoy
Cuando intento registrar otra asistencia para el mismo documento
Entonces el sistema muestra un error de asistencia duplicada
```

**CA-HU10-3 · Documento vacío** *(menú)*
```gherkin
Cuando registro una asistencia sin documento
Entonces el sistema rechaza el registro
```

### HU-11 · Listar y buscar asistencias

**CA-HU11-1 · Listado completo** *(menú)*
```gherkin
Cuando selecciono "Listar asistencias"
Entonces el sistema muestra todas las asistencias con ID, documento, fecha y estado
```

**CA-HU11-2 · Búsqueda por ID** *(menú)*
```gherkin
Cuando ingreso un ID numérico existente
Entonces el sistema muestra esa asistencia
```

**CA-HU11-3 · Búsqueda por documento** *(menú)*
```gherkin
Cuando ingreso el documento de un alumno
Entonces el sistema muestra todas sus asistencias
```

### HU-12 · Modificar asistencia

**CA-HU12-1 · Corrección de estado** *(menú)*
```gherkin
Dado una asistencia existente
Cuando cambio su estado de presente a ausente
Entonces el sistema guarda el cambio
```

**CA-HU12-2 · Fecha futura** *(menú / modelo)*
```gherkin
Cuando intento modificar la fecha a un valor posterior a hoy
Entonces el sistema rechaza el cambio con "No se puede registrar una asistencia con fecha futura"
```

**CA-HU12-3 · Duplicado al modificar** *(menú)*
```gherkin
Dado dos asistencias del mismo documento en fechas distintas
Cuando modifico una para que coincida con la otra
Entonces el sistema rechaza la modificación por duplicado
```

**CA-HU12-4 · Asistencia inexistente** *(menú)*
```gherkin
Cuando ingreso un ID que no existe
Entonces el sistema muestra un error
```

### HU-13 · Eliminar asistencia

**CA-HU13-1 · Baja exitosa** *(menú)*
```gherkin
Dado una asistencia existente
Cuando ingreso su ID en "Eliminar asistencia"
Entonces la asistencia deja de aparecer en el listado
```

**CA-HU13-2 · Asistencia inexistente** *(menú)*
```gherkin
Cuando ingreso un ID que no existe
Entonces el sistema muestra un error
```

---

## 5. Notas

### HU-14 · Registrar nota

**CA-HU14-1 · Nota válida** *(menú)*
```gherkin
Dado un alumno inscripto en la comisión "Comisión-1" y un examen "Parcial 1" de esa comisión
Cuando selecciono el alumno y el examen, y registro el valor 8 con observación "Aprobado"
Entonces el sistema guarda la nota con la fecha de hoy
Y el valor 8 se muestra al consultar la nota
```

**CA-HU14-2 · Nota fuera de rango** *(menú)*
```gherkin
Cuando ingreso un valor menor a 1 o mayor a 10
Entonces el sistema muestra "La nota debe estar entre 1 y 10"
Y no guarda el registro
```

**CA-HU14-3 · Observación opcional** *(menú)*
```gherkin
Cuando registro una nota sin observación
Entonces la nota se guarda con observación nula
```

**CA-HU14-4 · Alumno no inscripto** *(menú)*
```gherkin
Dado un alumno que no está inscripto en la comisión del examen
Cuando busco un alumno para registrar la nota
Entonces el sistema no ofrece ese alumno en la selección
Y no permite registrar la nota
```

### HU-15 · Listar y buscar notas

**CA-HU15-1 · Listado completo** *(menú)*
```gherkin
Cuando selecciono "Listar notas"
Entonces el sistema muestra todas las notas registradas con ID, examen, valor, observación y fecha
```

**CA-HU15-2 · Búsqueda por ID** *(menú)*
```gherkin
Cuando ingreso un ID numérico existente
Entonces el sistema muestra esa nota
```

**CA-HU15-3 · Búsqueda por examen** *(menú)*
```gherkin
Cuando ingreso parte del nombre de un examen
Entonces el sistema muestra las notas de los exámenes cuyo nombre coincide
```

**CA-HU15-4 · Criterio vacío** *(menú)*
```gherkin
Cuando busco sin ingresar texto
Entonces el sistema lista todas las notas
```

### HU-16 · Modificar nota

**CA-HU16-1 · Corrección de valor** *(menú)*
```gherkin
Dado una nota existente con valor 6
Cuando ingreso el ID y el nuevo valor 7
Entonces la nota queda con valor 7
```

**CA-HU16-2 · Valor fuera de rango** *(menú)*
```gherkin
Cuando ingreso un valor menor a 1 o mayor a 10
Entonces el sistema rechaza la modificación
```

**CA-HU16-3 · Valor nulo conserva el actual** *(nivel servicio)*
```gherkin
Cuando se modifica una nota pasando valor null
Entonces el valor anterior se conserva
Y solo se actualiza la observación
```

**CA-HU16-4 · Nota inexistente** *(menú)*
```gherkin
Cuando ingreso un ID que no existe
Entonces el sistema muestra "No existe una nota con id ..."
```

### HU-17 · Eliminar nota

**CA-HU17-1 · Baja exitosa** *(menú)*
```gherkin
Dado una nota existente
Cuando ingreso su ID en "Eliminar nota"
Entonces la nota deja de aparecer en el listado
```

**CA-HU17-2 · Nota inexistente** *(menú)*
```gherkin
Cuando ingreso un ID que no existe
Entonces el sistema muestra un error
```

---

## 6. Consultas

### HU-18 · Ver carreras

**CA-HU18-1 · Listado** *(menú)*
```gherkin
Cuando selecciono "Ver todas las carreras"
Entonces el sistema muestra las carreras TSP, TAS y LDS con su nombre
```

### HU-19 · Ver materias

**CA-HU19-1 · Materias con planes y comisiones** *(menú)*
```gherkin
Cuando selecciono "Ver todas las materias"
Entonces se muestran las materias y, para cada una, sus planes de estudio y comisiones asociadas
```

### HU-20 · Ver profesores

**CA-HU20-1 · Listado** *(menú)*
```gherkin
Cuando selecciono "Ver todos los profesores"
Entonces el sistema muestra nombre, email y teléfono de cada profesor
```

### HU-21 · Ver plan de estudio

**CA-HU21-1 · Plan por carrera** *(menú)*
```gherkin
Dado la carrera TSP
Cuando selecciono "Ver plan de estudio de una carrera"
Entonces el sistema muestra las materias agrupadas por año
```

### HU-22 · Ver comisiones

**CA-HU22-1 · Comisiones con horarios** *(menú)*
```gherkin
Cuando selecciono "Ver todas las comisiones"
Entonces se muestra código, materia, profesor, cuatrimestre y horarios de cada comisión
```

### HU-23 · Ver comisiones por profesor

**CA-HU23-1 · Filtro por profesor** *(menú)*
```gherkin
Cuando selecciono un profesor
Entonces el sistema muestra solo las comisiones asignadas a ese profesor
```

### HU-24 · Ver alumnos

**CA-HU24-1 · Padrón completo** *(menú)*
```gherkin
Cuando selecciono "Ver todos los alumnos"
Entonces el sistema muestra el listado completo del padrón
```

---

## 7. Backlog (criterios propuestos)

> Estos escenarios **no son ejecutables** en la versión actual. Se incluyen para orientar la implementación futura.

### HU-25 · Estado de cursada
```gherkin
Dado un alumno con 80% de asistencia y promedio 7
Cuando se calcula su estado de cursada
Entonces el sistema lo marca como REGULAR o PROMOCIONADO según la regla configurada
```

### HU-26 · Promedio
```gherkin
Dado un alumno con notas 6, 8 y 10
Cuando consulto su promedio
Entonces el sistema muestra 8.0
```

### HU-27 · Correlatividades
```gherkin
Dado un alumno que no aprobó "Programación I"
Cuando intenta inscribirse en "Programación II"
Entonces el sistema rechaza la inscripción por correlatividad
```

### HU-28 · Consulta del alumno
```gherkin
Dado un alumno autenticado
Cuando consulta su situación académica
Entonces el sistema muestra sus notas, asistencias y estado de cursada
```

### HU-29 · Constancia de alumno regular
```gherkin
Dado un alumno en condición regular
Cuando solicita una constancia
Entonces el sistema genera el documento con sus datos y la condición
```
