package ar.edu.itec.view;

import ar.edu.itec.controller.NotaController;
import ar.edu.itec.model.*;
import ar.edu.itec.repository.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class NotaView {

    private static final DateTimeFormatter FECHA_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final NotaController notaController;
    private final CarreraRepository carreraRepository;
    private final PlanEstudioRepository planEstudioRepository;
    private final ComisionRepository comisionRepository;
    private final AlumnoRepository alumnoRepository;
    private final ExamenRepository examenRepository;
    private final Scanner scanner;

    public NotaView(NotaController notaController, CarreraRepository carreraRepository,
                    PlanEstudioRepository planEstudioRepository, ComisionRepository comisionRepository,
                    AlumnoRepository alumnoRepository, ExamenRepository examenRepository,
                    Scanner scanner) {
        this.notaController = notaController;
        this.carreraRepository = carreraRepository;
        this.planEstudioRepository = planEstudioRepository;
        this.comisionRepository = comisionRepository;
        this.alumnoRepository = alumnoRepository;
        this.examenRepository = examenRepository;
        this.scanner = scanner;
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero();
            switch (opcion) {
                case 1 -> registrarNota();
                case 2 -> listarNotas();
                case 3 -> buscarNota();
                case 4 -> modificarNota();
                case 5 -> eliminarNota();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción inválida");
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("\n=== GESTIÓN DE NOTAS ===");
        System.out.println("1. Registrar nota");
        System.out.println("2. Listar notas");
        System.out.println("3. Buscar nota");
        System.out.println("4. Modificar nota");
        System.out.println("5. Eliminar nota");
        System.out.println("0. Volver");
    }

    private void registrarNota() {
        try {
            Carrera carrera = seleccionarCarrera();
            Materia materia = seleccionarMateria(carrera);
            ComisionMateria comision = seleccionarComision(materia);
            AlumnoInscripto inscripto = seleccionarAlumno(comision);
            Examen examen = seleccionarExamen(comision);
            Double valor = pedirValor();
            String observacion = pedirTexto("Observación (opcional): ");
            Nota nota = notaController.registrarNota(inscripto, examen, valor, observacion);
            System.out.println("\n--- NOTA REGISTRADA ---");
            mostrarNota(nota);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listarNotas() {
        List<Nota> notas = notaController.listarNotas();
        if (notas.isEmpty()) {
            System.out.println("No hay notas registradas.");
            return;
        }
        for (Nota nota : notas) {
            mostrarNota(nota);
        }
    }

    private void buscarNota() {
        String criterio = pedirTexto("Ingrese id o nombre de examen: ");
        List<Nota> resultados = notaController.buscarNotas(criterio);
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron notas.");
            return;
        }
        for (Nota nota : resultados) {
            mostrarNota(nota);
        }
    }

    private void modificarNota() {
        try {
            Long id = pedirLong("Id de la nota: ");
            Double valor = pedirValor();
            String observacion = pedirTexto("Observación: ");
            Nota nota = notaController.modificarNota(id, valor, observacion);
            System.out.println("Nota modificada:");
            mostrarNota(nota);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminarNota() {
        try {
            Long id = pedirLong("Id de la nota: ");
            notaController.eliminarNota(id);
            System.out.println("Nota eliminada.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // --- Selection helpers ---

    private Carrera seleccionarCarrera() {
        List<Carrera> carreras = carreraRepository.listarCarreras();
        System.out.println("\n--- CARRERAS DISPONIBLES ---");
        for (Carrera c : carreras) {
            System.out.println("  " + c.getCodigo() + " - " + c.getNombre());
        }
        while (true) {
            System.out.print("Seleccione código de carrera: ");
            String codigo = scanner.nextLine().trim();
            Carrera carrera = carreraRepository.buscarPorCodigo(codigo).orElse(null);
            if (carrera != null) return carrera;
            System.out.println("Código inválido, intente nuevamente.");
        }
    }

    private Materia seleccionarMateria(Carrera carrera) {
        List<PlanEstudio> planes = planEstudioRepository.buscarPorCarrera(carrera.getCodigo());
        if (planes.isEmpty()) {
            throw new IllegalArgumentException("La carrera " + carrera.getCodigo() + " no tiene materias cargadas.");
        }
        System.out.println("\n--- MATERIAS DE " + carrera.getCodigo().toUpperCase() + " ---");
        for (int i = 0; i < planes.size(); i++) {
            PlanEstudio p = planes.get(i);
            System.out.println("  " + (i + 1) + ". " + p.getMateria().getNombre() + " (" + p.getAnio() + "° año)");
        }
        while (true) {
            int opcion = leerEntero("Seleccione materia: ");
            if (opcion >= 1 && opcion <= planes.size()) {
                return planes.get(opcion - 1).getMateria();
            }
            System.out.println("Opción inválida, intente nuevamente.");
        }
    }

    private ComisionMateria seleccionarComision(Materia materia) {
        List<ComisionMateria> comisiones = comisionRepository.buscarPorMateria(materia.getNombre());
        if (comisiones.isEmpty()) {
            throw new IllegalArgumentException("No hay comisiones para la materia " + materia.getNombre());
        }
        System.out.println("\n--- COMISIONES DE " + materia.getNombre().toUpperCase() + " ---");
        for (int i = 0; i < comisiones.size(); i++) {
            ComisionMateria c = comisiones.get(i);
            System.out.println("  " + (i + 1) + ". " + c.getCodigo()
                    + (c.getProfesor() != null ? " - " + c.getProfesor().getNombreCompleto() : ""));
        }
        while (true) {
            int opcion = leerEntero("Seleccione comisión: ");
            if (opcion >= 1 && opcion <= comisiones.size()) {
                return comisiones.get(opcion - 1);
            }
            System.out.println("Opción inválida, intente nuevamente.");
        }
    }

    private AlumnoInscripto seleccionarAlumno(ComisionMateria comision) {
        List<AlumnoInscripto> inscritos = alumnoRepository.buscarInscripcionesPorComision(comision);
        if (inscritos.isEmpty()) {
            throw new IllegalArgumentException("No hay alumnos inscriptos en " + comision.getCodigo());
        }
        System.out.println("\n--- ALUMNOS INSCRIPTOS EN " + comision.getCodigo().toUpperCase() + " ---");
        for (int i = 0; i < inscritos.size(); i++) {
            AlumnoInscripto ai = inscritos.get(i);
            System.out.println("  " + (i + 1) + ". " + ai.getAlumno().getNombreCompleto() + " (DNI: " + ai.getAlumno().getDni() + ")");
        }
        while (true) {
            int opcion = leerEntero("Seleccione alumno: ");
            if (opcion >= 1 && opcion <= inscritos.size()) {
                return inscritos.get(opcion - 1);
            }
            System.out.println("Opción inválida, intente nuevamente.");
        }
    }

    private Examen seleccionarExamen(ComisionMateria comision) {
        List<Examen> examenes = examenRepository.buscarPorComision(comision);
        if (examenes.isEmpty()) {
            throw new IllegalArgumentException("No hay exámenes registrados para " + comision.getCodigo());
        }
        System.out.println("\n--- EXÁMENES DE " + comision.getCodigo().toUpperCase() + " ---");
        for (int i = 0; i < examenes.size(); i++) {
            Examen e = examenes.get(i);
            System.out.println("  " + (i + 1) + ". " + e.getNombre() + " - " + e.getFecha().format(FECHA_FORMATTER));
        }
        while (true) {
            int opcion = leerEntero("Seleccione examen: ");
            if (opcion >= 1 && opcion <= examenes.size()) {
                return examenes.get(opcion - 1);
            }
            System.out.println("Opción inválida, intente nuevamente.");
        }
    }

    // --- Input helpers ---

    private String pedirTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private Long pedirLong(String mensaje) {
        System.out.print(mensaje);
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Double pedirValor() {
        while (true) {
            System.out.print("Nota (1-10): ");
            try {
                double valor = Double.parseDouble(scanner.nextLine().trim());
                if (valor >= 1 && valor <= 10) return valor;
                System.out.println("La nota debe estar entre 1 y 10.");
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido.");
            }
        }
    }

    private int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        return leerEntero();
    }

    // --- Display ---

    private void mostrarNota(Nota nota) {
        System.out.println("--------------------------------------------------");
        System.out.println("ID: " + formatearId(nota.getId()));
        if (nota.getExamen() != null) {
            System.out.println("Examen: " + nota.getExamen().getNombre());
            System.out.println("Materia: " + nota.getExamen().getComision().getMateria());
            System.out.println("Comisión: " + nota.getExamen().getComision().getCodigo());
        }
        System.out.println("Valor: " + nota.getValor());
        if (nota.getObservacion() != null && !nota.getObservacion().isBlank()) {
            System.out.println("Observación: " + nota.getObservacion());
        }
        System.out.println("Fecha de registro: " + (nota.getFechaRegistro() != null ? nota.getFechaRegistro().format(FECHA_FORMATTER) : "-"));
        System.out.println("--------------------------------------------------");
    }

    private String formatearId(Long id) {
        return id == null ? "---" : String.format("%03d", id);
    }
}
