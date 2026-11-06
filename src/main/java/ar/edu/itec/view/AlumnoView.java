package ar.edu.itec.view;

import ar.edu.itec.controller.AlumnoController;
import ar.edu.itec.model.Alumno;
import ar.edu.itec.model.AlumnoInscripto;
import ar.edu.itec.model.ComisionMateria;
import ar.edu.itec.model.HorarioClase;

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
                case 4 -> modificarAlumno();
                case 5 -> eliminarAlumno();
                case 6 -> inscribirComision();
                case 7 -> verComisiones();
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
        System.out.println("4. Modificar alumno");
        System.out.println("5. Eliminar alumno");
        System.out.println("6. Inscribir alumno en comisión");
        System.out.println("7. Ver comisiones de un alumno");
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

    private void modificarAlumno() {
        try {
            Alumno alumno = alumnoController.obtenerAlumno(pedirTexto("DNI del alumno a modificar: "));
            mostrarMensaje("Datos actuales: " + alumno);
            mostrarMensaje("(Enter para mantener el valor actual)");

            String nombre = pedirTextoOpcional("Nombre [" + alumno.getNombre() + "]: ", alumno.getNombre());
            String apellido = pedirTextoOpcional("Apellido [" + alumno.getApellido() + "]: ", alumno.getApellido());
            String dni = pedirTextoOpcional("DNI [" + alumno.getDni() + "]: ", alumno.getDni());
            String email = pedirTextoOpcional("Email [" + valorActual(alumno.getEmail()) + "]: ", alumno.getEmail());
            String telefono = pedirTextoOpcional("Teléfono [" + valorActual(alumno.getTelefono()) + "]: ", alumno.getTelefono());

            Alumno actualizado = alumnoController.modificarAlumno(
                    alumno.getDni(), nombre, apellido, dni, email, telefono);
            mostrarMensaje("Alumno modificado: " + actualizado);
        } catch (IllegalArgumentException e) {
            mostrarMensaje("Error: " + e.getMessage());
        }
    }

    private void eliminarAlumno() {
        try {
            Alumno alumno = alumnoController.obtenerAlumno(pedirTexto("DNI del alumno a eliminar: "));
            mostrarMensaje("Alumno: " + alumno);
            String confirmacion = pedirTexto("¿Confirma la eliminación? (S/N): ");
            if (!confirmacion.equalsIgnoreCase("S")) {
                mostrarMensaje("Operación cancelada.");
                return;
            }
            alumnoController.eliminarAlumno(alumno.getDni());
            mostrarMensaje("Alumno eliminado correctamente.");
        } catch (IllegalArgumentException e) {
            mostrarMensaje("Error: " + e.getMessage());
        }
    }

    private void inscribirComision() {
        try {
            String dni = pedirTexto("DNI del alumno: ");
            List<ComisionMateria> comisiones = alumnoController.listarComisionesDisponibles();
            if (comisiones.isEmpty()) {
                mostrarMensaje("No hay comisiones disponibles.");
                return;
            }
            mostrarComisionesDisponibles(comisiones);
            ComisionMateria comision = seleccionarComision(comisiones);
            AlumnoInscripto inscripcion = alumnoController.inscribirAlumnoComision(
                    dni, comision.getCodigo());
            mostrarMensaje("Inscripción exitosa: " + inscripcion);
        } catch (IllegalArgumentException e) {
            mostrarMensaje("Error: " + e.getMessage());
        }
    }

    private void mostrarComisionesDisponibles(List<ComisionMateria> comisiones) {
        System.out.println("\n--- COMISIONES DISPONIBLES ---");
        for (int i = 0; i < comisiones.size(); i++) {
            ComisionMateria comision = comisiones.get(i);
            System.out.println("  " + (i + 1) + ". " + comision.getCodigo()
                    + " - " + comision.getMateria().getNombre()
                    + (comision.getProfesor() != null
                            ? " - " + comision.getProfesor().getNombreCompleto()
                            : ""));
            for (HorarioClase horario : comision.getHorarios()) {
                System.out.println("     " + horario.getDiaSemanaEspanol()
                        + ": " + horario.getModulos());
            }
        }
    }

    private ComisionMateria seleccionarComision(List<ComisionMateria> comisiones) {
        while (true) {
            int opcion = leerEnteroConMensaje("Seleccione comisión: ");
            if (opcion >= 1 && opcion <= comisiones.size()) {
                return comisiones.get(opcion - 1);
            }
            mostrarMensaje("Opción inválida, intente nuevamente.");
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

    private String pedirTextoOpcional(String mensaje, String valorActual) {
        String valor = pedirTexto(mensaje);
        return valor.isEmpty() ? valorActual : valor;
    }

    private String valorActual(String valor) {
        return valor == null ? "" : valor;
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
