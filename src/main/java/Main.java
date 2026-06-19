

import ar.edu.itec.controller.AlumnoController;
import ar.edu.itec.model.*;
import ar.edu.itec.repository.AlumnoRepository;
import ar.edu.itec.service.AlumnoService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AlumnoRepository repository = new AlumnoRepository();
        AlumnoService service = new AlumnoService(repository);

        System.out.println("=== BACKOFFICE ITEC N°1 ===");
        System.out.println("1. Demo automático");
        System.out.println("2. Menú interactivo");
        System.out.print("Opción: ");
        try {
            Scanner scanner = new Scanner(System.in);
            String opcion = scanner.nextLine().trim();
            if ("2".equals(opcion)) {
                AlumnoController controller = new AlumnoController(service);
                controller.iniciar();
                return;
            }
        } catch (Exception e) {
            System.out.println("(ejecutando demo por defecto)");
        }
        ejecutarDemo(service);
    }

    static void ejecutarDemo(AlumnoService service) {
        System.out.println("\n=== DEMO - Gestión de Alumnos ===");

        // 1. Registrar alumnos
        System.out.println("\n--- Registrar alumnos ---");
        Alumno a1 = service.registrarAlumno("Lucas", "Giorgi", "40123456", "lucas@mail.com", "LEG-001");
        System.out.println("Creado: " + a1);
        Alumno a2 = service.registrarAlumno("Axel", "Almada", "41234567", "axel@mail.com", "LEG-002");
        System.out.println("Creado: " + a2);
        Alumno a3 = service.registrarAlumno("Enrique", "Lopez", "42345678", "enrique@mail.com", "LEG-003");
        System.out.println("Creado: " + a3);

        // 2. Buscar alumnos
        System.out.println("\n--- Buscar alumnos ---");
        List<Alumno> resultados = service.buscarAlumno("40123456");
        System.out.println("Búsqueda por documento '40123456': " + resultados);
        resultados = service.buscarAlumno("LEG-002");
        System.out.println("Búsqueda por legajo 'LEG-002': " + resultados);
        resultados = service.buscarAlumno("Lucas");
        System.out.println("Búsqueda por nombre 'Lucas': " + resultados);
        resultados = service.buscarAlumno("");
        System.out.println("Todos los alumnos: " + resultados);

        // 3. Inscribir en carrera
        System.out.println("\n--- Inscribir en carrera ---");
        Carrera tsd = new Carrera("TSD", "Tecnicatura en Desarrollo de Software");
        PlanEstudio plan2024 = new PlanEstudio(tsd, 2024);
        AlumnoCarrera ac1 = service.inscribirAlumnoCarrera(a1, tsd, plan2024);
        System.out.println("Inscripción: " + ac1);
        AlumnoCarrera ac2 = service.inscribirAlumnoCarrera(a2, tsd, plan2024);
        System.out.println("Inscripción: " + ac2);

        // Intentar duplicado (debe dar error)
        try {
            service.inscribirAlumnoCarrera(a1, tsd, plan2024);
        } catch (IllegalArgumentException e) {
            System.out.println("Error esperado (duplicado): " + e.getMessage());
        }

        // 4. Inscribir en comisión
        System.out.println("\n--- Inscribir en comisión ---");
        HorarioClase lunes = new HorarioClase(java.time.DayOfWeek.MONDAY);
        lunes.agregarModulo(new ModuloHorario(1, LocalTime.of(18, 0), LocalTime.of(20, 0)));
        lunes.agregarModulo(new ModuloHorario(2, LocalTime.of(20, 15), LocalTime.of(22, 15)));

        ComisionMateria prog1 = new ComisionMateria("COM-001", "Programación I");
        prog1.agregarHorario(lunes);

        AlumnoInscripto ai1 = service.inscribirAlumnoComision(a1, prog1);
        System.out.println("Inscripción: " + ai1);

        // 5. Registrar asistencia en la comisión
        System.out.println("\n--- Registrar asistencias ---");
        ai1.registrarAsistencia(new Asistencia(LocalDate.of(2026, 6, 15), true));
        ai1.registrarAsistencia(new Asistencia(LocalDate.of(2026, 6, 16), false));
        ai1.registrarAsistencia(new Asistencia(LocalDate.of(2026, 6, 17), true));
        System.out.println("Asistencias registradas: " + ai1.getAsistencias().size());
        System.out.println("Porcentaje de asistencia: " + String.format("%.1f%%", ai1.calcularPorcentajeAsistencia()));
        System.out.println("Es regular (75%): " + ai1.esRegular(75.0));

        // 6. Consultar carreras y comisiones del alumno
        System.out.println("\n--- Consultas ---");
        System.out.println("Carreras de " + a1.getNombreCompleto() + ": " + service.obtenerCarrerasDeAlumno(a1));
        System.out.println("Comisiones de " + a1.getNombreCompleto() + ": " + service.obtenerComisionesDeAlumno(a1));

        System.out.println("\n=== DEMO FINALIZADA ===");
    }
}