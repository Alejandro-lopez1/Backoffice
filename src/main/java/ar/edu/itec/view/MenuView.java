package ar.edu.itec.view;

import java.util.Scanner;

public class MenuView {

    private final Scanner scanner;
    private final AlumnoView alumnoView;
    private final ExamenView examenView;
    private final AsistenciaView asistenciaView;
    private final ConsultasView consultasView;
    private final NotaView notaView;

    public MenuView(AlumnoView alumnoView, ExamenView examenView, AsistenciaView asistenciaView,
                    ConsultasView consultasView, NotaView notaView, Scanner scanner) {
        this.alumnoView = alumnoView;
        this.examenView = examenView;
        this.asistenciaView = asistenciaView;
        this.consultasView = consultasView;
        this.notaView = notaView;
        this.scanner = scanner;
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero();
            try {
                switch (opcion) {
                    case 1 -> alumnoView.iniciar();
                    case 2 -> examenView.iniciar();
                    case 3 -> asistenciaView.iniciar();
                    case 4 -> consultasView.iniciar();
                    case 5 -> notaView.iniciar();
                    case 0 -> System.out.println("Saliendo...");
                    default -> System.out.println("Opción inválida");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("\n=== BACKOFFICE ITEC N°1 ===");
        System.out.println("1. Gestión de alumnos");
        System.out.println("2. Gestión de exámenes");
        System.out.println("3. Gestión de asistencias");
        System.out.println("4. Consultas y reportes");
        System.out.println("5. Gestión de notas");
        System.out.println("0. Salir");
        System.out.print("Opción: ");
    }

    private int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
