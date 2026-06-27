package ar.edu.itec.controller;

import ar.edu.itec.model.Alumno;
import ar.edu.itec.model.AlumnoCarrera;
import ar.edu.itec.model.AlumnoInscripto;
import ar.edu.itec.model.Carrera;
import ar.edu.itec.model.ComisionMateria;
import ar.edu.itec.repository.AlumnoRepository;
import ar.edu.itec.service.AlumnoService;

import java.util.List;

public class AlumnoController {

    private final AlumnoService alumnoService;
    private final AlumnoRepository repository;

    public AlumnoController(AlumnoService alumnoService, AlumnoRepository repository) {
        this.alumnoService = alumnoService;
        this.repository = repository;
    }

    public Alumno registrarAlumno(String nombre, String apellido, String documento, String email, String legajo) {
        return alumnoService.registrarAlumno(nombre, apellido, documento, email, legajo);
    }

    public List<Alumno> buscarAlumno(String criterio) {
        return alumnoService.buscarAlumno(criterio);
    }

    public List<Alumno> listarAlumnos() {
        return repository.buscarTodos();
    }

    public AlumnoCarrera inscribirAlumnoCarrera(String documento, String codigoCarrera, String nombreCarrera) {
        Alumno alumno = obtenerAlumno(documento);
        Carrera carrera = new Carrera(codigoCarrera, nombreCarrera);
        return alumnoService.inscribirAlumnoCarrera(alumno, carrera);
    }

    public AlumnoInscripto inscribirAlumnoComision(String documento, String codigoComision, String materia) {
        Alumno alumno = obtenerAlumno(documento);
        ComisionMateria comision = new ComisionMateria(codigoComision, materia);
        return alumnoService.inscribirAlumnoComision(alumno, comision);
    }

    public List<AlumnoCarrera> obtenerCarrerasDeAlumno(String documento) {
        return alumnoService.obtenerCarrerasDeAlumno(obtenerAlumno(documento));
    }

    public List<AlumnoInscripto> obtenerComisionesDeAlumno(String documento) {
        return alumnoService.obtenerComisionesDeAlumno(obtenerAlumno(documento));
    }

    private Alumno obtenerAlumno(String documento) {
        return alumnoService.buscarAlumno(documento).stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No se encontró alumno con documento " + documento));
    }
}
