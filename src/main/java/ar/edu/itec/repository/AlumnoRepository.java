package ar.edu.itec.repository;

import ar.edu.itec.model.Alumno;
import ar.edu.itec.model.AlumnoInscripto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AlumnoRepository {

    private final List<Alumno> alumnos;
    private final List<AlumnoInscripto> inscripcionesComision;

    public AlumnoRepository() {
        this.alumnos = new ArrayList<>();
        this.inscripcionesComision = new ArrayList<>();
        cargarAlumnosIniciales();
    }

    private void cargarAlumnosIniciales() {
        alumnos.add(new Alumno("Juan", "Pérez", "12345678", "juan.perez@mail.com", "1122334455"));
        alumnos.add(new Alumno("María", "González", "23456789", "maria.gonzalez@mail.com", "1133445566"));
        alumnos.add(new Alumno("Carlos", "Rodríguez", "34567890", "carlos.rodriguez@mail.com", "1144556677"));
        alumnos.add(new Alumno("Ana", "Martínez", "45678901", "ana.martinez@mail.com", "1155667788"));
    }

    // --- Alumno ---

    public void guardar(Alumno alumno) {
        alumnos.add(alumno);
    }

    public Optional<Alumno> buscarPorDni(String dni) {
        return alumnos.stream()
                .filter(a -> a.getDni().equalsIgnoreCase(dni))
                .findFirst();
    }

    public List<Alumno> buscarPorNombre(String nombre) {
        return alumnos.stream()
                .filter(a -> a.getNombre().toLowerCase().contains(nombre.toLowerCase())
                        || a.getApellido().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Alumno> buscarTodos() {
        return new ArrayList<>(alumnos);
    }

    // --- AlumnoInscripto ---

    public void guardarInscripcionComision(AlumnoInscripto inscripcion) {
        inscripcionesComision.add(inscripcion);
    }

    public boolean existeInscripcionComision(Alumno alumno, String codigoComision) {
        return inscripcionesComision.stream()
                .anyMatch(ic -> ic.getAlumno().equals(alumno)
                        && ic.getComision().getCodigo().equalsIgnoreCase(codigoComision));
    }

    public List<AlumnoInscripto> buscarInscripcionesComisionPorAlumno(Alumno alumno) {
        return inscripcionesComision.stream()
                .filter(ic -> ic.getAlumno().equals(alumno))
                .collect(Collectors.toList());
    }

    public List<AlumnoInscripto> buscarTodasLasInscripcionesComision() {
        return new ArrayList<>(inscripcionesComision);
    }
}
