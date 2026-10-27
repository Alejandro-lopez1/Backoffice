package ar.edu.itec.controller;

import ar.edu.itec.model.Alumno;
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

    public Alumno registrarAlumno(String nombre, String apellido, String dni, String email, String telefono) {
        return alumnoService.registrarAlumno(nombre, apellido, dni, email, telefono);
    }

    public List<Alumno> buscarAlumno(String criterio) {
        return alumnoService.buscarAlumno(criterio);
    }

    public List<Alumno> listarAlumnos() {
        return repository.buscarTodos();
    }

    public List<Carrera> listarCarrerasDisponibles() {
        return carreraRepository.listarCarreras();
    }

    public AlumnoInscripto inscribirAlumnoComision(String dni, String codigoComision, String materia) {
        Alumno alumno = obtenerAlumno(dni);
        ComisionMateria comision = new ComisionMateria(codigoComision, materia);
        return alumnoService.inscribirAlumnoComision(alumno, comision);
    }

    public List<AlumnoInscripto> obtenerComisionesDeAlumno(String dni) {
        return alumnoService.obtenerComisionesDeAlumno(obtenerAlumno(dni));
    }

    private Alumno obtenerAlumno(String dni) {
        return alumnoService.buscarAlumno(dni).stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No se encontró alumno con DNI " + dni));
    }
}
