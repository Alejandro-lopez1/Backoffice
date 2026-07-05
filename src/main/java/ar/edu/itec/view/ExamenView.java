package ar.edu.itec.view;

import ar.edu.itec.controller.ExamenController;
import ar.edu.itec.enums.TipoEvaluacion;
import ar.edu.itec.model.*;
import ar.edu.itec.repository.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ExamenView {

    private static final DateTimeFormatter FECHA_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final ExamenController examenController;
    private final CarreraRepository carreraRepository;
    private final PlanEstudioRepository planEstudioRepository;
    private final ComisionRepository comisionRepository;
    private final Scanner scanner;

    public ExamenView(ExamenController examenController, CarreraRepository carreraRepository,
                      PlanEstudioRepository planEstudioRepository, ComisionRepository comisionRepository,
                      Scanner scanner) {
        this.examenController = examenController;
        this.carreraRepository = carreraRepository;
        this.planEstudioRepository = planEstudioRepository;
        this.comisionRepository = comisionRepository;
        this.scanner = scanner;
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarTitulo();
            opcion = leerEnteroConMensaje("Opción: ");
            switch (opcion) {
                case 1 -> registrarExamen();
                case 2 -> listarExamenes();
                case 3 -> buscarExamen();
                case 4 -> modificarExamen();
                case 5 -> eliminarExamen();
                case 0 -> mostrarMensaje("Volviendo...");
                default -> mostrarMensaje("Opción inválida");
            }
        } while (opcion != 0);
    }

    public void mostrarTitulo() {
        System.out.println("\n=== GESTIÓN DE EXÁMENES ===");
        System.out.println("1. Registrar examen");
        System.out.println("2. Listar exámenes");
        System.out.println("3. Buscar examen");
        System.out.println("4. Modificar examen");
        System.out.println("5. Eliminar examen");
        System.out.println("0. Volver");
    }

    private void registrarExamen() {
        try {
            Carrera carrera = seleccionarCarrera();
            Materia materia = seleccionarMateria(carrera);
            ComisionMateria comision = seleccionarComision(materia);
            LocalDate fecha = pedirFecha("Fecha (dd-MM-aaaa): ");
            String nombre = pedirTexto("Título del examen: ");
            TipoEvaluacion tipoEvaluacion = pedirTipoEvaluacion();
            Examen examen = examenController.registrarExamen(
                    comision, nombre, "", fecha, tipoEvaluacion);
            System.out.println("\n--- EXAMEN REGISTRADO ---");
            mostrarExamen(examen);
        } catch (IllegalArgumentException e) {
            mostrarMensaje("Error: " + e.getMessage());
        }
    }

    private void listarExamenes() {
        mostrarExamenes(examenController.listarExamenes());
    }

    private void buscarExamen() {
        List<Examen> resultados = examenController.buscarExamenes(pedirTexto("Ingrese id o nombre: "));
        mostrarExamenes(resultados);
    }

    private void modificarExamen() {
        try {
            Long id = pedirLong("Id del examen: ");
            Carrera carrera = seleccionarCarrera();
            Materia materia = seleccionarMateria(carrera);
            ComisionMateria comision = seleccionarComision(materia);
            String nombre = pedirTexto("Nombre: ");
            String descripcion = pedirTexto("Descripción: ");
            LocalDate fecha = pedirFecha("Fecha (dd-MM-aaaa): ");
            TipoEvaluacion tipoEvaluacion = pedirTipoEvaluacion();
            Examen examen = examenController.modificarExamen(
                    id, comision, nombre, descripcion, fecha, tipoEvaluacion);
            mostrarMensaje("Examen modificado:");
            mostrarExamen(examen);
        } catch (IllegalArgumentException e) {
            mostrarMensaje("Error: " + e.getMessage());
        }
    }

    private void eliminarExamen() {
        try {
            examenController.eliminarExamen(pedirLong("Id del examen: "));
            mostrarMensaje("Examen eliminado.");
        } catch (IllegalArgumentException e) {
            mostrarMensaje("Error: " + e.getMessage());
        }
    }

    public String pedirTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    public Long pedirLong(String mensaje) {
        System.out.print(mensaje);
        String valor = scanner.nextLine().trim();
        try {
            return Long.parseLong(valor);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public LocalDate pedirFecha(String mensaje) {
        System.out.print(mensaje);
        String valor = scanner.nextLine().trim();
        try {
            return LocalDate.parse(valor, FECHA_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public TipoEvaluacion pedirTipoEvaluacion() {
        System.out.println("Tipo de examen:");
        System.out.println("1. Parcial");
        System.out.println("2. Recuperatorio");
        System.out.println("3. Final");
        int opcion = leerEnteroConMensaje("Opción: ");
        return switch (opcion) {
            case 1 -> TipoEvaluacion.PARCIAL;
            case 2 -> TipoEvaluacion.RECUPERATORIO;
            case 3 -> TipoEvaluacion.FINAL;
            default -> null;
        };
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarExamenes(List<Examen> examenes) {
        if (examenes.isEmpty()) {
            mostrarMensaje("No hay exámenes registrados.");
            return;
        }
        for (Examen examen : examenes) {
            System.out.println("--------------------------------------------------");
            System.out.println("ID: " + formatearId(examen.getId()));
            System.out.println("Comisión: " + examen.getComision().getCodigo());
            System.out.println("Materia: " + examen.getComision().getMateria());
            if (examen.getComision().getProfesor() != null) {
                System.out.println("Profesor: " + examen.getComision().getProfesor().getNombreCompleto());
            }
            System.out.println("Nombre: " + examen.getNombre());
            System.out.println("Descripción: " + examen.getDescripcion());
            System.out.println("Fecha: " + formatearFecha(examen.getFecha()));
            System.out.println("Tipo: " + formatearTipo(examen.getTipoEvaluacion()));
        }
        System.out.println("--------------------------------------------------");
    }

    private int leerEnteroConMensaje(String mensaje) {
        System.out.print(mensaje);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private String formatearFecha(LocalDate fecha) {
        return fecha == null ? "-" : fecha.format(FECHA_FORMATTER);
    }

    private void mostrarExamen(Examen examen) {
        System.out.println("--------------------------------------------------");
        System.out.println("ID: " + formatearId(examen.getId()));
        System.out.println("Comisión: " + examen.getComision().getCodigo());
        System.out.println("Materia: " + examen.getComision().getMateria());
        if (examen.getComision().getProfesor() != null) {
            System.out.println("Profesor: " + examen.getComision().getProfesor().getNombreCompleto());
        }
        System.out.println("Nombre: " + examen.getNombre());
        System.out.println("Descripción: " + examen.getDescripcion());
        System.out.println("Fecha: " + formatearFecha(examen.getFecha()));
        System.out.println("Tipo: " + formatearTipo(examen.getTipoEvaluacion()));
        System.out.println("--------------------------------------------------");
    }

    private Carrera seleccionarCarrera() {
        List<Carrera> carreras = carreraRepository.listarCarreras();
        System.out.println("\n--- CARRERAS DISPONIBLES ---");
        for (Carrera c : carreras) {
            System.out.println("  " + c.getCodigo() + " - " + c.getNombre());
        }
        while (true) {
            System.out.print("Seleccione código de carrera: ");
            String codigo = scanner.nextLine().trim();
            Carrera carrera = carreraRepository.buscarPorCodigo(codigo).orElse(null);
            if (carrera != null) {
                return carrera;
            }
            mostrarMensaje("Código inválido, intente nuevamente.");
        }
    }

    private Materia seleccionarMateria(Carrera carrera) {
        List<PlanEstudio> planes = planEstudioRepository.buscarPorCarrera(carrera.getCodigo());
        if (planes.isEmpty()) {
            throw new IllegalArgumentException("La carrera " + carrera.getCodigo() + " no tiene materias cargadas.");
        }
        System.out.println("\n--- MATERIAS DE " + carrera.getCodigo().toUpperCase() + " ---");
        for (int i = 0; i < planes.size(); i++) {
            PlanEstudio p = planes.get(i);
            System.out.println("  " + (i + 1) + ". " + p.getMateria().getNombre() + " (" + p.getAnio() + "° año)");
        }
        while (true) {
            int opcion = leerEnteroConMensaje("Seleccione materia: ");
            if (opcion >= 1 && opcion <= planes.size()) {
                return planes.get(opcion - 1).getMateria();
            }
            mostrarMensaje("Opción inválida, intente nuevamente.");
        }
    }

    private ComisionMateria seleccionarComision(Materia materia) {
        List<ComisionMateria> comisiones = comisionRepository.buscarPorMateria(materia.getNombre());
        if (comisiones.isEmpty()) {
            throw new IllegalArgumentException("No hay comisiones para la materia " + materia.getNombre());
        }
        System.out.println("\n--- COMISIONES DE " + materia.getNombre().toUpperCase() + " ---");
        for (int i = 0; i < comisiones.size(); i++) {
            ComisionMateria c = comisiones.get(i);
            System.out.println("  " + (i + 1) + ". " + c.getCodigo()
                    + (c.getProfesor() != null ? " - " + c.getProfesor().getNombreCompleto() : ""));
        }
        while (true) {
            int opcion = leerEnteroConMensaje("Seleccione comisión: ");
            if (opcion >= 1 && opcion <= comisiones.size()) {
                return comisiones.get(opcion - 1);
            }
            mostrarMensaje("Opción inválida, intente nuevamente.");
        }
    }

    private String formatearId(Long id) {
        return id == null ? "---" : String.format("%03d", id);
    }

    private String formatearTipo(TipoEvaluacion tipoEvaluacion) {
        if (tipoEvaluacion == null) {
            return "-";
        }
        return switch (tipoEvaluacion) {
            case PARCIAL -> "Parcial";
            case RECUPERATORIO -> "Recuperatorio";
            case FINAL -> "Final";
        };
    }
}
