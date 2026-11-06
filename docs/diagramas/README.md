# Diagramas — DFD 0 y DFD 1

Esta carpeta contiene los diagramas de flujo de datos del sistema en dos formatos.

| Archivo | Contenido | Uso |
|---|---|---|
| [`dfd-0.svg`](dfd-0.svg) | DFD 0 — contexto | Visualización en los documentos |
| [`dfd-1.svg`](dfd-1.svg) | DFD 1 — sistema | Visualización en los documentos |
| [`dfd-0.drawio`](dfd-0.drawio) | DFD 0 — fuente editable | Edición en diagrams.net |
| [`dfd-1.drawio`](dfd-1.drawio) | DFD 1 — fuente editable | Edición en diagrams.net |

## Notación

Se utiliza **Gane-Sarson**:

| Elemento | Símbolo |
|---|---|
| Entidad externa | Rectángulo |
| Proceso | Rectángulo con esquinas redondeadas |
| Almacén de datos | Rectángulo abierto por el lado derecho |
| Flujo de datos | Flecha con etiqueta |

Convenciones propias:

- `E1`, `E2`, `E3`: entidades externas.
- `n.0`: procesos del DFD 1.
- `D1` a `D10`: almacenes lógicos.
- Línea discontinua: elemento planificado (proceso `7.0` y entidad `E3`).
- Flecha de doble punta: el proceso lee y escribe el almacén, o la entidad envía y recibe datos. Es una simplificación para no duplicar flechas; el detalle está en las tablas de [`06-dfd-1-sistema.md`](../06-dfd-1-sistema.md).

## Cómo editar

1. Abrir [diagrams.net](https://app.diagrams.net) o la extensión Draw.io de VS Code.
2. Abrir el archivo `.drawio` correspondiente.
3. Modificar el diagrama.
4. Exportar como SVG:
   - `Archivo` → `Exportar como` → `SVG`.
   - Mantener el tamaño de página y el fondo transparente o blanco.
   - No incluir sombras ni temas oscuros.
5. Guardar el SVG en esta carpeta con el mismo nombre (`dfd-0.svg` o `dfd-1.svg`).

Los `.svg` incluidos se generaron con el mismo layout que los `.drawio`. Si se edita el `.drawio`, conviene volver a exportar el `.svg` para que ambos queden alineados.

## Cómo regenerar los SVG

Los SVG actuales se generaron con un script Python externo al repositorio (no versionado). Para evitar depender de él:

- Editar el `.drawio` y exportar el SVG desde diagrams.net, o
- reconstruir el SVG con la herramienta de su preferencia manteniendo los identificadores y el layout.

El `.drawio` es la fuente editable recomendada.

## Consistencia con la documentación

Si se agrega, elimina o renombra un proceso, almacén, entidad o flujo:

1. Actualizar el `.drawio` y el `.svg`.
2. Actualizar las tablas de [`05-dfd-0-contexto.md`](../05-dfd-0-contexto.md) y [`06-dfd-1-sistema.md`](../06-dfd-1-sistema.md).
3. Revisar el balanceo entre niveles.
4. Actualizar el diccionario en [`07-diccionario-datos.md`](../07-diccionario-datos.md) si cambia la composición de un flujo o almacén.
5. Revisar la trazabilidad en [`08-trazabilidad.md`](../08-trazabilidad.md).
