package ar.edu.itec.controller;

import ar.edu.itec.model.Alumno;
import ar.edu.itec.model.AlumnoCarrera;
import ar.edu.itec.model.AlumnoInscripto;
import ar.edu.itec.model.Carrera;
import ar.edu.itec.model.ComisionMateria;
import ar.edu.itec.repository.CarreraRepository;
import ar.edu.itec.repository.AlumnoRepository;
import ar.edu.itec.service.AlumnoService;

import java.util.List;

public class AlumnoController {

    private final AlumnoService alumnoService;
    private final AlumnoRepository repository;
    private final CarreraRepository carreraRepository;

    public AlumnoController(AlumnoService alumnoService, AlumnoRepository repository, CarreraRepository carreraRepository) {
        this.alumnoService = alumnoService;
        this.repository = repository;
        this.carreraRepository = carreraRepository;
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

    public AlumnoCarrera inscribirAlumnoCarrera(String documento, String codigoCarrera) {
        Alumno alumno = obtenerAlumno(documento);
        Carrera carrera = carreraRepository.buscarPorCodigo(codigoCarrera)
                .orElseThrow(() -> new IllegalArgumentException("No existe una carrera con código " + codigoCarrera));
        return alumnoService.inscribirAlumnoCarrera(alumno, carrera);
    }

    public List<Carrera> listarCarrerasDisponibles() {
        return carreraRepository.listarCarreras();
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
