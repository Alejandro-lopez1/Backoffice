package ar.edu.itec.view;

import ar.edu.itec.controller.AlumnoController;
import ar.edu.itec.controller.AsistenciaController;
import ar.edu.itec.controller.ExamenController;
import ar.edu.itec.controller.NotaController;
import ar.edu.itec.repository.*;
import ar.edu.itec.service.impl.AlumnoServiceImpl;
import ar.edu.itec.service.impl.AlumnoAsistenciaServiceImpl;
import ar.edu.itec.service.impl.ProfesorExamenServiceImpl;
import ar.edu.itec.service.impl.AlumnoNotaServiceImpl;

import java.util.Scanner;

public class MenuView {

    private final Scanner scanner;
    private final AlumnoView alumnoView;
    private final ExamenView examenView;
    private final AsistenciaView asistenciaView;
    private final ConsultasView consultasView;
    private final NotaView notaView;

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
        AlumnoServiceImpl alumnoService = new AlumnoServiceImpl(alumnoRepository, comisionRepository);
        AlumnoController alumnoController = new AlumnoController(
                alumnoService, alumnoRepository, carreraRepository, comisionRepository);

        ExamenRepository examenRepository = new ExamenRepository();
        ProfesorExamenServiceImpl examenService = new ProfesorExamenServiceImpl(examenRepository);
        ExamenController examenController = new ExamenController(examenService);

        AsistenciaRepository asistenciaRepository = new AsistenciaRepository();
        AlumnoAsistenciaServiceImpl asistenciaService = new AlumnoAsistenciaServiceImpl(asistenciaRepository);
        AsistenciaController asistenciaController = new AsistenciaController(asistenciaService);

        NotaRepository notaRepository = new NotaRepository();
        AlumnoNotaServiceImpl notaService = new AlumnoNotaServiceImpl(notaRepository);
        NotaController notaController = new NotaController(notaService);

        this.alumnoView = new AlumnoView(alumnoController, scanner);
        this.examenView = new ExamenView(examenController, carreraRepository,
                planEstudioRepository, comisionRepository, scanner);
        this.asistenciaView = new AsistenciaView(asistenciaController, scanner);
        this.consultasView = new ConsultasView(carreraRepository, materiaRepository, profesorRepository, 
                                               planEstudioRepository, comisionRepository, alumnoRepository, scanner);
        this.notaView = new NotaView(notaController, carreraRepository, planEstudioRepository,
                comisionRepository, alumnoRepository, examenRepository, scanner);
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
