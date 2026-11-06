# Documentación — Backoffice Académico ITEC N°1

Documentación funcional y de análisis del sistema de gestión académica desarrollado en Java para el Instituto Tecnológico N°1.

## Estado de la documentación

| Símbolo | Significado |
|---|---|
| ✅ | Implementado y verificable en el código actual |
| ⚠️ | Implementado con alguna limitación conocida |
| 🧩 | Parcial (existe parte del comportamiento) |
| 📋 | Backlog / planificado, no implementado |

La documentación describe el sistema **tal como está implementado en el código actual**, y marca explícitamente lo que falta. No se documentan como hechas funciones que solo figuran en el README del repositorio.

## Índice

| Documento | Contenido |
|---|---|
| [00 — Alcance](00-alcance.md) | Objetivo, alcance, fuera de alcance, supuestos y restricciones |
| [01 — Actores y glosario](01-actores-glosario.md) | Actores, matriz de acceso y vocabulario del dominio |
| [02 — Reglas de negocio](02-reglas-negocio.md) | Reglas RN y requisitos no funcionales RNF |
| [03 — Historias de usuario](03-historias-usuario.md) | 24 HU implementadas + 5 HU de backlog |
| [04 — Criterios de aceptación](04-criterios-aceptacion.md) | Escenarios Given/When/Then por historia |
| [05 — DFD 0 (contexto)](05-dfd-0-contexto.md) | Diagrama de contexto y flujos externos |
| [06 — DFD 1 (sistema)](06-dfd-1-sistema.md) | Procesos, almacenes, flujos y balanceo |
| [07 — Diccionario de datos](07-diccionario-datos.md) | Composición de entidades, flujos y almacenes |
| [08 — Trazabilidad](08-trazabilidad.md) | HU ↔ CA ↔ DFD ↔ código ↔ tests |
| [09 — Modelo de dominio](09-modelo-dominio.md) | Diagrama UML de clases y relaciones |
| [10 — Manual de uso](10-manual-uso.md) | Recorrido del menú consola y datos iniciales |

## Convenciones de identificación

| Prefijo | Uso | Ejemplo |
|---|---|---|
| `HU-xx` | Historia de usuario | `HU-01` |
| `CA-HUxx-n` | Criterio de aceptación de una HU | `CA-HU01-2` |
| `RN-xx` | Regla de negocio | `RN-04` |
| `RNF-xx` | Requisito no funcional | `RNF-01` |
| `Ex` | Entidad externa del DFD | `E1` |
| `n.0` | Proceso del DFD 1 | `2.0` |
| `Dx` | Almacén de datos lógico | `D1` |
| `Fxx` | Flujo de datos | `F05` |

## Diagramas

Los DFD están en `docs/diagramas/` en dos formatos:

- `dfd-0.svg` / `dfd-1.svg`: versión renderizada que se muestra en los documentos.
- `dfd-0.drawio` / `dfd-1.drawio`: fuente editable para diagrams.net (draw.io).

Notación utilizada: **Gane-Sarson**.

- Entidad externa: rectángulo.
- Proceso: rectángulo con esquinas redondeadas.
- Almacén: rectángulo abierto por el lado derecho.
- Flujo: flecha etiquetada con un sustantivo.

Para editar: abrir el `.drawio` en [diagrams.net](https://app.diagrams.net) o en la extensión de VS Code, modificar y exportar nuevamente el SVG. Los `.svg` incluidos se generaron con el mismo layout que los `.drawio`. Instrucciones detalladas en [`diagramas/README.md`](diagramas/README.md).

## Estado funcional

Los cinco módulos del menú están operativos: alumnos e inscripciones, exámenes, asistencias, consultas y notas. La inscripción trabaja con la comisión real (profesor y horarios) y el alta de exámenes solicita la descripción. Las limitaciones restantes son de alcance (persistencia, autenticación, promedio, correlatividades) y están detalladas en el [manual](10-manual-uso.md#11-limitaciones-conocidas).

## Cómo leer los estados

- Una HU marcada ✅ tiene una implementación identificable en `src/main/java` y, cuando corresponde, pruebas en `src/test/java`.
- Una HU marcada ⚠️ tiene una implementación parcial o una limitación que impide usarla de punta a punta; el detalle está en la sección "Observaciones" de la propia historia y en el manual.
- Las HU 📋 se incluyen para dejar explícito el alcance futuro; sus criterios de aceptación son propuestas, no comportamiento actual.

## Fuente de verdad

1. Código en `src/main/java`.
2. Pruebas en `src/test/java`.
3. Estos documentos.

Ante una discrepancia, el código y las pruebas tienen prioridad y la documentación debe corregirse. Fecha de relevamiento: **octubre de 2026**.
