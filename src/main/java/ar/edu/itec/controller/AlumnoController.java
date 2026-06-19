package ar.edu.itec.controller;

import ar.edu.itec.model.*;
import ar.edu.itec.service.AlumnoService;

import java.util.List;
import java.util.Scanner;

public class AlumnoController {

    private final AlumnoService alumnoService;
    private final Scanner scanner;

    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion;
        do {
            System.out.println("\n=== GESTIÓN DE ALUMNOS E INSCRIPCIONES ===");
            System.out.println("1. Registrar alumno");
            System.out.println("2. Buscar alumno");
            System.out.println("3. Listar todos los alumnos");
            System.out.println("4. Inscribir alumno en carrera");
            System.out.println("5. Inscribir alumno en comisión");
            System.out.println("6. Ver carreras de un alumno");
            System.out.println("7. Ver comisiones de un alumno");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            opcion = leerEntero();
            switch (opcion) {
                case 1 -> registrarAlumno();
                case 2 -> buscarAlumno();
                case 3 -> listarAlumnos();
                case 4 -> inscribirCarrera();
                case 5 -> inscribirComision();
                case 6 -> verCarreras();
                case 7 -> verComisiones();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción inválida");
            }
        } while (opcion != 0);
    }

    private void registrarAlumno() {
        System.out.println("\n--- Registrar Alumno ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine().trim();
        System.out.print("Documento: ");
        String documento = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Legajo: ");
        String legajo = scanner.nextLine().trim();
        try {
            Alumno alumno = alumnoService.registrarAlumno(nombre, apellido, documento, email, legajo);
            System.out.println("Alumno registrado: " + alumno);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void buscarAlumno() {
        System.out.println("\n--- Buscar Alumno ---");
        System.out.print("Ingrese documento, legajo, nombre o apellido: ");
        String criterio = scanner.nextLine().trim();
        List<Alumno> resultados = alumnoService.buscarAlumno(criterio);
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron alumnos.");
        } else {
            resultados.forEach(System.out::println);
        }
    }

    private void listarAlumnos() {
        System.out.println("\n--- Lista de Alumnos ---");
        List<Alumno> alumnos = alumnoService.buscarAlumno("");
        if (alumnos.isEmpty()) {
            System.out.println("No hay alumnos registrados.");
        } else {
            alumnos.forEach(System.out::println);
        }
    }

    private void inscribirCarrera() {
        System.out.println("\n--- Inscribir Alumno en Carrera ---");
        System.out.print("Documento del alumno: ");
        String documento = scanner.nextLine().trim();
        System.out.print("Código de carrera: ");
        String codCarrera = scanner.nextLine().trim();
        System.out.print("Nombre de carrera: ");
        String nombreCarrera = scanner.nextLine().trim();
        System.out.print("Año del plan de estudio: ");
        int anioPlan = leerEntero();
        try {
            Alumno alumno = alumnoService.buscarAlumno(documento).stream()
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No se encontró alumno con documento " + documento));
            Carrera carrera = new Carrera(codCarrera, nombreCarrera);
            PlanEstudio plan = new PlanEstudio(carrera, anioPlan);
            AlumnoCarrera inscripcion = alumnoService.inscribirAlumnoCarrera(alumno, carrera, plan);
            System.out.println("Inscripción exitosa: " + inscripcion);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void inscribirComision() {
        System.out.println("\n--- Inscribir Alumno en Comisión ---");
        System.out.print("Documento del alumno: ");
        String documento = scanner.nextLine().trim();
        System.out.print("Código de comisión: ");
        String codComision = scanner.nextLine().trim();
        System.out.print("Materia: ");
        String materia = scanner.nextLine().trim();
        try {
            Alumno alumno = alumnoService.buscarAlumno(documento).stream()
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No se encontró alumno con documento " + documento));
            ComisionMateria comision = new ComisionMateria(codComision, materia);
            AlumnoInscripto inscripcion = alumnoService.inscribirAlumnoComision(alumno, comision);
            System.out.println("Inscripción exitosa: " + inscripcion);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void verCarreras() {
        System.out.println("\n--- Carreras de un Alumno ---");
        System.out.print("Documento del alumno: ");
        String documento = scanner.nextLine().trim();
        try {
            Alumno alumno = alumnoService.buscarAlumno(documento).stream()
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No se encontró alumno con documento " + documento));
            List<AlumnoCarrera> carreras = alumnoService.obtenerCarrerasDeAlumno(alumno);
            if (carreras.isEmpty()) {
                System.out.println("El alumno no está inscripto en ninguna carrera.");
            } else {
                carreras.forEach(System.out::println);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void verComisiones() {
        System.out.println("\n--- Comisiones de un Alumno ---");
        System.out.print("Documento del alumno: ");
        String documento = scanner.nextLine().trim();
        try {
            Alumno alumno = alumnoService.buscarAlumno(documento).stream()
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No se encontró alumno con documento " + documento));
            List<AlumnoInscripto> comisiones = alumnoService.obtenerComisionesDeAlumno(alumno);
            if (comisiones.isEmpty()) {
                System.out.println("El alumno no está inscripto en ninguna comisión.");
            } else {
                comisiones.forEach(System.out::println);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
