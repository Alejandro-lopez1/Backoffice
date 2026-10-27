package ar.edu.itec.view;

import ar.edu.itec.controller.AlumnoController;
import ar.edu.itec.model.Alumno;
import ar.edu.itec.model.AlumnoInscripto;

import java.util.List;
import java.util.Scanner;

public class AlumnoView {

    private final AlumnoController alumnoController;
    private final Scanner scanner;

    public AlumnoView(AlumnoController alumnoController, Scanner scanner) {
        this.alumnoController = alumnoController;
        this.scanner = scanner;
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarTitulo();
            opcion = leerEnteroConMensaje("Opción: ");
            switch (opcion) {
                case 1 -> registrarAlumno();
                case 2 -> buscarAlumno();
                case 3 -> listarAlumnos();
                case 4 -> inscribirComision();
                case 5 -> verComisiones();
                case 0 -> mostrarMensaje("Volviendo...");
                default -> mostrarMensaje("Opción inválida");
            }
        } while (opcion != 0);
    }

    private void mostrarTitulo() {
        System.out.println("\n=== GESTIÓN DE ALUMNOS E INSCRIPCIONES ===");
        System.out.println("1. Registrar alumno");
        System.out.println("2. Buscar alumno");
        System.out.println("3. Listar todos los alumnos");
        System.out.println("4. Inscribir alumno en comisión");
        System.out.println("5. Ver comisiones de un alumno");
        System.out.println("0. Volver");
    }

    private void registrarAlumno() {
        try {
            Alumno alumno = alumnoController.registrarAlumno(
                    pedirTexto("Nombre: "),
                    pedirTexto("Apellido: "),
                    pedirTexto("DNI: "),
                    pedirTexto("Email: "),
                    pedirTexto("Teléfono: "));
            mostrarMensaje("Alumno registrado: " + alumno);
        } catch (IllegalArgumentException e) {
            mostrarMensaje("Error: " + e.getMessage());
        }
    }

    private void buscarAlumno() {
        List<Alumno> resultados = alumnoController.buscarAlumno(pedirTexto("Ingrese DNI, nombre o apellido: "));
        mostrarAlumnos(resultados);
    }

    private void listarAlumnos() {
        mostrarAlumnos(alumnoController.listarAlumnos());
    }

    private void inscribirComision() {
        try {
            AlumnoInscripto inscripcion = alumnoController.inscribirAlumnoComision(
                    pedirTexto("DNI del alumno: "),
                    pedirTexto("Código de comisión: "),
                    pedirTexto("Materia: "));
            mostrarMensaje("Inscripción exitosa: " + inscripcion);
        } catch (IllegalArgumentException e) {
            mostrarMensaje("Error: " + e.getMessage());
        }
    }

    private void verComisiones() {
        try {
            mostrarComisiones(alumnoController.obtenerComisionesDeAlumno(pedirTexto("DNI del alumno: ")));
        } catch (IllegalArgumentException e) {
            mostrarMensaje("Error: " + e.getMessage());
        }
    }

    private String pedirTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
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

    private void mostrarAlumnos(List<Alumno> alumnos) {
        if (alumnos.isEmpty()) {
            mostrarMensaje("No se encontraron alumnos.");
            return;
        }
        alumnos.forEach(System.out::println);
    }

    private void mostrarComisiones(List<AlumnoInscripto> comisiones) {
        if (comisiones.isEmpty()) {
            mostrarMensaje("El alumno no está inscripto en ninguna comisión.");
            return;
        }
        comisiones.forEach(System.out::println);
    }
}
