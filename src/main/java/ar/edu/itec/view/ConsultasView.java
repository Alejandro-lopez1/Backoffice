package ar.edu.itec.view;

import ar.edu.itec.model.*;
import ar.edu.itec.repository.*;

import java.util.List;
import java.util.Scanner;

public class ConsultasView {

    private final CarreraRepository carreraRepository;
    private final MateriaRepository materiaRepository;
    private final ProfesorRepository profesorRepository;
    private final PlanEstudioRepository planEstudioRepository;
    private final ComisionRepository comisionRepository;
    private final AlumnoRepository alumnoRepository;
    private final Scanner scanner;

    public ConsultasView(CarreraRepository carreraRepository, 
                        MateriaRepository materiaRepository,
                        ProfesorRepository profesorRepository,
                        PlanEstudioRepository planEstudioRepository,
                        ComisionRepository comisionRepository,
                        AlumnoRepository alumnoRepository,
                        Scanner scanner) {
        this.carreraRepository = carreraRepository;
        this.materiaRepository = materiaRepository;
        this.profesorRepository = profesorRepository;
        this.planEstudioRepository = planEstudioRepository;
        this.comisionRepository = comisionRepository;
        this.alumnoRepository = alumnoRepository;
        this.scanner = scanner;
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero();
            switch (opcion) {
                case 1 -> listarCarreras();
                case 2 -> listarMaterias();
                case 3 -> listarProfesores();
                case 4 -> verPlanDeEstudio();
                case 5 -> verComisiones();
                case 6 -> verComisionesPorProfesor();
                case 7 -> listarAlumnos();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción inválida");
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("\n=== CONSULTAS Y REPORTES ===");
        System.out.println("1. Ver todas las carreras");
        System.out.println("2. Ver todas las materias");
        System.out.println("3. Ver todos los profesores");
        System.out.println("4. Ver plan de estudio de una carrera");
        System.out.println("5. Ver todas las comisiones");
        System.out.println("6. Ver comisiones por profesor");
        System.out.println("7. Ver todos los alumnos");
        System.out.println("0. Volver");
        System.out.print("Opción: ");
    }

    private void listarCarreras() {
        System.out.println("\n--- CARRERAS DISPONIBLES ---");
        List<Carrera> carreras = carreraRepository.listarCarreras();
        for (Carrera carrera : carreras) {
            System.out.println(" - " + carrera.getCodigo() + ": " + carrera.getNombre());
        }
    }

    private void listarMaterias() {
        System.out.println("\n--- MATERIAS DISPONIBLES ---");
        List<Materia> materias = materiaRepository.listarMaterias();
        List<PlanEstudio> planes = planEstudioRepository.listarPlanes();
        List<ComisionMateria> comisiones = comisionRepository.listarComisiones();
        for (Materia materia : materias) {
            System.out.println(" - " + materia.getNombre());

            List<PlanEstudio> planesMateria = planes.stream()
                    .filter(p -> p.getMateria() != null && p.getMateria().equals(materia))
                    .toList();
            if (!planesMateria.isEmpty()) {
                System.out.print("   Carreras: ");
                for (int i = 0; i < planesMateria.size(); i++) {
                    PlanEstudio p = planesMateria.get(i);
                    if (i > 0) System.out.print(", ");
                    System.out.print(p.getCarrera().getCodigo() + " (" + p.getAnio() + "° año)");
                }
                System.out.println();
            }

            List<ComisionMateria> comisionesMateria = comisiones.stream()
                    .filter(c -> c.getMateria().equals(materia))
                    .toList();
            if (!comisionesMateria.isEmpty()) {
                System.out.println("   Comisiones:");
                for (ComisionMateria c : comisionesMateria) {
                    System.out.print("     " + c.getCodigo());
                    if (c.getProfesor() != null) {
                        System.out.print(" (" + c.getProfesor().getNombreCompleto() + ")");
                    }
                    System.out.println();
                }
            }
            System.out.println();
        }
    }

    private void listarProfesores() {
        System.out.println("\n--- PROFESORES ---");
        List<Profesor> profesores = profesorRepository.listarProfesores();
        List<ComisionMateria> comisiones = comisionRepository.listarComisiones();
        for (Profesor profesor : profesores) {
            System.out.println(" - " + profesor.getNombreCompleto());
            System.out.println("   Email: " + profesor.getEmail());
            System.out.println("   Tel: " + profesor.getTelefono());

            List<ComisionMateria> comisionesProfe = comisiones.stream()
                    .filter(c -> c.getProfesor() != null && c.getProfesor().equals(profesor))
                    .toList();
            if (!comisionesProfe.isEmpty()) {
                String materias = comisionesProfe.stream()
                        .map(c -> c.getMateria().getNombre())
                        .distinct()
                        .collect(java.util.stream.Collectors.joining(", "));
                String carreras = comisionesProfe.stream()
                        .map(c -> c.getMateria())
                        .flatMap(m -> planEstudioRepository.listarPlanes().stream()
                                .filter(p -> p.getMateria() != null && p.getMateria().equals(m)))
                        .map(p -> p.getCarrera().getCodigo())
                        .distinct()
                        .collect(java.util.stream.Collectors.joining(", "));
                System.out.println("   Materias: " + materias);
                if (!carreras.isEmpty()) {
                    System.out.println("   Carreras: " + carreras);
                }
            }
            System.out.println();
        }
    }

    private void verPlanDeEstudio() {
        System.out.print("\nIngrese código de carrera: ");
        String codigo = scanner.nextLine().trim();
        
        List<PlanEstudio> planes = planEstudioRepository.buscarPorCarrera(codigo);
        if (planes.isEmpty()) {
            System.out.println("No se encontró plan de estudios para la carrera " + codigo);
            return;
        }

        System.out.println("\n--- PLAN DE ESTUDIO: " + planes.get(0).getCarrera().getNombre() + " ---");
        int anioActual = -1;
        for (PlanEstudio plan : planes) {
            if (plan.getAnio() != anioActual) {
                anioActual = plan.getAnio();
                System.out.println("\n" + anioActual + "° Año:");
            }
            System.out.println("  - " + plan.getMateria().getNombre());
        }
    }

    private void verComisiones() {
        System.out.println("\n--- COMISIONES DISPONIBLES ---");
        List<ComisionMateria> comisiones = comisionRepository.listarComisiones();
        for (ComisionMateria comision : comisiones) {
            mostrarComision(comision);
        }
    }

    private void verComisionesPorProfesor() {
        System.out.print("\nIngrese apellido del profesor: ");
        String apellido = scanner.nextLine().trim();
        
        List<ComisionMateria> comisiones = comisionRepository.buscarPorProfesor(apellido);
        if (comisiones.isEmpty()) {
            System.out.println("No se encontraron comisiones para el profesor " + apellido);
            return;
        }

        System.out.println("\n--- COMISIONES DEL PROFESOR " + apellido.toUpperCase() + " ---");
        for (ComisionMateria comision : comisiones) {
            mostrarComision(comision);
        }
    }

    private void listarAlumnos() {
        System.out.println("\n--- ALUMNOS REGISTRADOS ---");
        List<Alumno> alumnos = alumnoRepository.buscarTodos();
        for (Alumno alumno : alumnos) {
            System.out.println(" - " + alumno.getNombreCompleto());
            System.out.println("   DNI: " + alumno.getDni());
            System.out.println("   Email: " + alumno.getEmail());
            System.out.println("   Tel: " + alumno.getTelefono());
            System.out.println();
        }
    }

    private void mostrarComision(ComisionMateria comision) {
        System.out.println("\nCódigo: " + comision.getCodigo());
        System.out.println("Materia: " + comision.getMateria().getNombre());
        if (comision.getProfesor() != null) {
            System.out.println("Profesor: " + comision.getProfesor().getNombreCompleto());
        }
        if (comision.getCuatrimestre() != null) {
            System.out.println("Cuatrimestre: " + comision.getCuatrimestre());
        }
        System.out.println("Horarios:");
        for (HorarioClase horario : comision.getHorarios()) {
            System.out.println("  " + horario.getDiaSemanaEspanol() + ": " + horario.getModulos());
        }
        System.out.println("---");
    }

    private int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
