package ar.edu.itec.view;

import ar.edu.itec.controller.AsistenciaController;
import ar.edu.itec.model.AsistenciaAlumno;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class AsistenciaView {

    private static final DateTimeFormatter FECHA_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final AsistenciaController asistenciaController;
    private final Scanner scanner;

    public AsistenciaView(AsistenciaController asistenciaController, Scanner scanner) {
        this.asistenciaController = asistenciaController;
        this.scanner = scanner;
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarTitulo();
            opcion = leerEnteroConMensaje("Opción: ");
            switch (opcion) {
                case 1 -> registrarAsistencia();
                case 2 -> listarAsistencias();
                case 3 -> buscarAsistencia();
                case 4 -> modificarAsistencia();
                case 5 -> eliminarAsistencia();
                case 0 -> mostrarMensaje("Volviendo...");
                default -> mostrarMensaje("Opción inválida");
            }
        } while (opcion != 0);
    }

    private void mostrarTitulo() {
        System.out.println("\n=== GESTIÓN DE ASISTENCIAS ===");
        System.out.println("1. Registrar asistencia");
        System.out.println("2. Listar asistencias");
        System.out.println("3. Buscar asistencia");
        System.out.println("4. Modificar asistencia");
        System.out.println("5. Eliminar asistencia");
        System.out.println("0. Volver");
    }

    private void registrarAsistencia() {
        try {
            AsistenciaAlumno asistencia = asistenciaController.registrarAsistencia(
                    pedirTexto("Documento del alumno: "),
                    pedirPresente());
            mostrarMensaje("Asistencia " + formatearId(asistencia.getId()) + ": " + asistencia.getDocumentoAlumno() + " - " + formatearFecha(asistencia.getFecha()));
        } catch (IllegalArgumentException e) {
            mostrarMensaje("Error: " + e.getMessage());
        }
    }

    private void listarAsistencias() {
        mostrarAsistencias(asistenciaController.listarAsistencias());
    }

    private void buscarAsistencia() {
        mostrarAsistencias(asistenciaController.buscarAsistencias(pedirTexto("Ingrese id o documento: ")));
    }

    private void modificarAsistencia() {
        try {
            AsistenciaAlumno asistencia = asistenciaController.modificarAsistencia(
                    pedirLong("Id de la asistencia: "),
                    pedirTexto("Documento del alumno: "),
                    pedirFecha("Fecha (dd-MM-aaaa): "),
                    pedirPresente());
            mostrarMensaje("Asistencia " + formatearId(asistencia.getId()) + ": " + asistencia.getDocumentoAlumno() + " - " + formatearFecha(asistencia.getFecha()));
        } catch (IllegalArgumentException e) {
            mostrarMensaje("Error: " + e.getMessage());
        }
    }

    private void eliminarAsistencia() {
        try {
            asistenciaController.eliminarAsistencia(pedirLong("Id de la asistencia: "));
            mostrarMensaje("Asistencia eliminada.");
        } catch (IllegalArgumentException e) {
            mostrarMensaje("Error: " + e.getMessage());
        }
    }

    private String pedirTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private Long pedirLong(String mensaje) {
        System.out.print(mensaje);
        String valor = scanner.nextLine().trim();
        try {
            return Long.parseLong(valor);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private LocalDate pedirFecha(String mensaje) {
        System.out.print(mensaje);
        String valor = scanner.nextLine().trim();
        try {
            return LocalDate.parse(valor, FECHA_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private boolean pedirPresente() {
        System.out.print("Presente (s/n): ");
        String valor = scanner.nextLine().trim().toLowerCase();
        return valor.equals("s") || valor.equals("si") || valor.equals("sí") || valor.equals("1");
    }

    private int leerEnteroConMensaje(String mensaje) {
        System.out.print(mensaje);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    private void mostrarAsistencias(List<AsistenciaAlumno> asistencias) {
        if (asistencias.isEmpty()) {
            mostrarMensaje("No hay asistencias registradas.");
            return;
        }
        for (AsistenciaAlumno asistencia : asistencias) {
            System.out.println("--------------------------------------------------");
            System.out.println("ID: " + formatearId(asistencia.getId()));
            System.out.println("Documento alumno: " + asistencia.getDocumentoAlumno());
            System.out.println("Fecha: " + formatearFecha(asistencia.getFecha()));
            System.out.println("Estado: " + (asistencia.isPresente() ? "Presente" : "Ausente"));
        }
        System.out.println("--------------------------------------------------");
    }

    private String formatearFecha(LocalDate fecha) {
        return fecha == null ? "-" : fecha.format(FECHA_FORMATTER);
    }

    private String formatearId(Long id) {
        return id == null ? "---" : String.format("%03d", id);
    }
}
