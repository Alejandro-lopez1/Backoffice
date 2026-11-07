# 11 — Arquitectura

## Diagrama de capas

```mermaid
flowchart TB
    U(["Operador<br/>teclado / consola"])
    CFG["<b>AppConfig</b> · config<br/>única raíz de composición:<br/>crea 1 instancia de cada repositorio y 1 Scanner<br/>e inyecta por constructor"]
    V["<b>VISTA</b> · view<br/>MenuView · AlumnoView · ExamenView<br/>AsistenciaView · NotaView · ConsultasView"]
    C["<b>CONTROLADOR</b> · controller<br/>AlumnoController · ExamenController · AsistenciaController<br/>NotaController · ConsultasController"]
    S["<b>SERVICIO</b> · service (interfaces) + service/impl<br/>AlumnoService · AlumnoAsistenciaService · AlumnoNotaService<br/>ProfesorExamenService · CarreraService · MateriaService<br/>PlanEstudioService · ComisionService · ProfesorConsultaService"]
    R["<b>REPOSITORIO</b> · repository<br/>AlumnoRepository · AsistenciaRepository · ExamenRepository<br/>NotaRepository · CarreraRepository · MateriaRepository<br/>ProfesorRepository · PlanEstudioRepository · ComisionRepository<br/><i>persistencia en memoria (ArrayList)</i>"]
    M["<b>MODELO</b> · model + enums<br/>sin dependencias hacia otras capas"]

    U --> V
    V -->|"invoca"| C
    C -->|"delega"| S
    S -->|"consulta / guarda"| R
    R -.->|"almacena"| M
    S -.->|"usa"| M
    CFG -.->|"ensambla"| V
```

## Propósito

El diagrama muestra la separación en capas del sistema y la dirección de las dependencias.
Permite ubicar responsabilidades y verificar que la implementación respete esa separación.

- Los servicios son interfaz en `service/` con implementación en `service/impl/`.
- `AppConfig` es el único lugar que conoce todas las capas: crea repositorios, servicios, controladores y vistas.
- Los datos maestros (carreras, materias, profesores, planes y comisiones) se precargan al iniciar y son de solo lectura.
