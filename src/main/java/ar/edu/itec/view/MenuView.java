package ar.edu.itec.view;

import ar.edu.itec.controller.AlumnoController;
import ar.edu.itec.controller.AsistenciaController;
import ar.edu.itec.controller.ExamenController;
import ar.edu.itec.repository.AlumnoRepository;
import ar.edu.itec.repository.AsistenciaRepository;
import ar.edu.itec.repository.CarreraRepository;
import ar.edu.itec.repository.ExamenRepository;
import ar.edu.itec.service.AlumnoService;
import ar.edu.itec.service.AsistenciaService;
import ar.edu.itec.service.ExamenService;

import java.util.Scanner;

public class MenuView {

    private final Scanner scanner;
    private final AlumnoView alumnoView;
    private final ExamenView examenView;
    private final AsistenciaView asistenciaView;

    public MenuView() {
        this.scanner = new Scanner(System.in);

        AlumnoRepository alumnoRepository = new AlumnoRepository();
        CarreraRepository carreraRepository = new CarreraRepository();
        AlumnoService alumnoService = new AlumnoService(alumnoRepository);
        AlumnoController alumnoController = new AlumnoController(alumnoService, alumnoRepository, carreraRepository);

        ExamenRepository examenRepository = new ExamenRepository();
        ExamenService examenService = new ExamenService(examenRepository);
        ExamenController examenController = new ExamenController(examenService, carreraRepository);

        AsistenciaRepository asistenciaRepository = new AsistenciaRepository();
        AsistenciaService asistenciaService = new AsistenciaService(asistenciaRepository);
        AsistenciaController asistenciaController = new AsistenciaController(asistenciaService);

        this.alumnoView = new AlumnoView(alumnoController, scanner);
        this.examenView = new ExamenView(examenController, scanner);
        this.asistenciaView = new AsistenciaView(asistenciaController, scanner);

        precargarDatosIniciales(alumnoController);
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

    private void precargarDatosIniciales(AlumnoController alumnoController) {
        registrarAlumnoInicial(alumnoController, "Axel", "Almada", "40123456", "axel@itec.edu", "LEG-001", "TSP");
        registrarAlumnoInicial(alumnoController, "Lucas", "Giorgi", "41123456", "lucas@itec.edu", "LEG-002", "TAS");
        registrarAlumnoInicial(alumnoController, "Alejandro", "Lopez", "42123456", "alejandro@itec.edu", "LEG-003", "LDS");
        registrarAlumnoInicial(alumnoController, "Leonardo", "Rios", "43123456", "leonardo@itec.edu", "LEG-004", "TSP");
    }

    private void registrarAlumnoInicial(AlumnoController alumnoController, String nombre, String apellido,
                                        String documento, String email, String legajo, String codigoCarrera) {
        try {
            alumnoController.registrarAlumno(nombre, apellido, documento, email, legajo);
            alumnoController.inscribirAlumnoCarrera(documento, codigoCarrera);
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo precargar " + nombre + " " + apellido + ": " + e.getMessage());
        }
    }
}
