package ar.edu.itec.view;

import ar.edu.itec.controller.ExamenController;
import ar.edu.itec.enums.TipoEvaluacion;
import ar.edu.itec.model.Carrera;
import ar.edu.itec.model.Examen;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ExamenView {

    private static final DateTimeFormatter FECHA_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final ExamenController examenController;
    private final Scanner scanner;

    public ExamenView(ExamenController examenController, Scanner scanner) {
        this.examenController = examenController;
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
            TipoEvaluacion tipoEvaluacion = pedirTipoEvaluacion();
            Examen examen = examenController.registrarExamen(
                    carrera,
                    pedirTexto("Nombre: "),
                    pedirTexto("Descripción: "),
                    pedirFecha("Fecha (dd-MM-aaaa): "),
                    tipoEvaluacion);
            mostrarMensaje("Examen " + formatearId(examen.getId()) + ": " + examen.getCarrera() + " - " + examen.getNombre() + " - " + formatearFecha(examen.getFecha()));
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
            Carrera carrera = seleccionarCarrera();
            TipoEvaluacion tipoEvaluacion = pedirTipoEvaluacion();
            Examen examen = examenController.modificarExamen(
                    pedirLong("Id del examen: "),
                    carrera,
                    pedirTexto("Nombre: "),
                    pedirTexto("Descripción: "),
                    pedirFecha("Fecha (dd-MM-aaaa): "),
                    tipoEvaluacion);
            mostrarMensaje("Examen " + formatearId(examen.getId()) + ": " + examen.getCarrera() + " - " + examen.getNombre() + " - " + formatearFecha(examen.getFecha()));
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
            System.out.println("Carrera: " + examen.getCarrera());
            System.out.println("Nombre: " + examen.getNombre());
            System.out.println("Descripcion: " + examen.getDescripcion());
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

    private Carrera seleccionarCarrera() {
        System.out.println("Carreras disponibles:");
        List<Carrera> carreras = examenController.listarCarrerasDisponibles();
        for (Carrera carrera : carreras) {
            System.out.println(" - " + carrera.getCodigo() + ": " + carrera.getNombre());
        }
        return examenController.buscarCarreraPorCodigo(pedirTexto("Código de carrera: "));
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
