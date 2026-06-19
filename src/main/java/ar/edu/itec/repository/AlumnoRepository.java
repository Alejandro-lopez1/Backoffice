package ar.edu.itec.repository;

import ar.edu.itec.model.Alumno;
import ar.edu.itec.model.AlumnoCarrera;
import ar.edu.itec.model.AlumnoInscripto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AlumnoRepository {

    private final List<Alumno> alumnos;
    private final List<AlumnoCarrera> inscripcionesCarrera;
    private final List<AlumnoInscripto> inscripcionesComision;

    public AlumnoRepository() {
        this.alumnos = new ArrayList<>();
        this.inscripcionesCarrera = new ArrayList<>();
        this.inscripcionesComision = new ArrayList<>();
    }

    // --- Alumno ---

    public void guardar(Alumno alumno) {
        alumnos.add(alumno);
    }

    public Optional<Alumno> buscarPorDocumento(String documento) {
        return alumnos.stream()
                .filter(a -> a.getDocumento().equalsIgnoreCase(documento))
                .findFirst();
    }

    public Optional<Alumno> buscarPorLegajo(String legajo) {
        return alumnos.stream()
                .filter(a -> a.getLegajo().equalsIgnoreCase(legajo))
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

    // --- AlumnoCarrera ---

    public void guardarInscripcionCarrera(AlumnoCarrera inscripcion) {
        inscripcionesCarrera.add(inscripcion);
    }

    public boolean existeInscripcionCarrera(Alumno alumno, String codigoCarrera) {
        return inscripcionesCarrera.stream()
                .anyMatch(ic -> ic.getAlumno().equals(alumno)
                        && ic.getCarrera().getCodigo().equalsIgnoreCase(codigoCarrera));
    }

    public List<AlumnoCarrera> buscarInscripcionesCarreraPorAlumno(Alumno alumno) {
        return inscripcionesCarrera.stream()
                .filter(ic -> ic.getAlumno().equals(alumno))
                .collect(Collectors.toList());
    }

    public List<AlumnoCarrera> buscarTodasLasInscripcionesCarrera() {
        return new ArrayList<>(inscripcionesCarrera);
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
