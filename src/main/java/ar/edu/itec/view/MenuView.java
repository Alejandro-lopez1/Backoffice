package ar.edu.itec.view;

import ar.edu.itec.controller.AlumnoController;
import ar.edu.itec.controller.AsistenciaController;
import ar.edu.itec.controller.ExamenController;
import ar.edu.itec.repository.*;
import ar.edu.itec.service.AlumnoService;
import ar.edu.itec.service.AsistenciaService;
import ar.edu.itec.service.ExamenService;

import java.util.Scanner;

public class MenuView {

    private final Scanner scanner;
    private final AlumnoView alumnoView;
    private final ExamenView examenView;
    private final AsistenciaView asistenciaView;
    private final ConsultasView consultasView;

    public MenuView() {
        this.scanner = new Scanner(System.in);

        // Repositorios base
        CarreraRepository carreraRepository = new CarreraRepository();
        MateriaRepository materiaRepository = new MateriaRepository();
        ProfesorRepository profesorRepository = new ProfesorRepository();
        
        // Repositorios con relaciones
        PlanEstudioRepository planEstudioRepository = new PlanEstudioRepository(carreraRepository, materiaRepository);
        ComisionRepository comisionRepository = new ComisionRepository(materiaRepository, profesorRepository);
        
        AlumnoRepository alumnoRepository = new AlumnoRepository();
        AlumnoService alumnoService = new AlumnoService(alumnoRepository);
        AlumnoController alumnoController = new AlumnoController(alumnoService, alumnoRepository, carreraRepository);

        ExamenRepository examenRepository = new ExamenRepository();
        ExamenService examenService = new ExamenService(examenRepository);
        ExamenController examenController = new ExamenController(examenService);

        AsistenciaRepository asistenciaRepository = new AsistenciaRepository();
        AsistenciaService asistenciaService = new AsistenciaService(asistenciaRepository);
        AsistenciaController asistenciaController = new AsistenciaController(asistenciaService);

        this.alumnoView = new AlumnoView(alumnoController, scanner);
        this.examenView = new ExamenView(examenController, scanner);
        this.asistenciaView = new AsistenciaView(asistenciaController, scanner);
        this.consultasView = new ConsultasView(carreraRepository, materiaRepository, profesorRepository, 
                                               planEstudioRepository, comisionRepository, alumnoRepository, scanner);
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
