package ar.edu.itec.controller;

import ar.edu.itec.model.Alumno;
import ar.edu.itec.model.AlumnoInscripto;
import ar.edu.itec.model.Carrera;
import ar.edu.itec.model.ComisionMateria;
import ar.edu.itec.repository.CarreraRepository;
import ar.edu.itec.repository.AlumnoRepository;
import ar.edu.itec.repository.ComisionRepository;
import ar.edu.itec.service.AlumnoService;

import java.util.List;

public class AlumnoController {

    private final AlumnoService alumnoService;
    private final AlumnoRepository repository;
    private final CarreraRepository carreraRepository;
    private final ComisionRepository comisionRepository;

    public AlumnoController(AlumnoService alumnoService, AlumnoRepository repository,
                            CarreraRepository carreraRepository, ComisionRepository comisionRepository) {
        this.alumnoService = alumnoService;
        this.repository = repository;
        this.carreraRepository = carreraRepository;
        this.comisionRepository = comisionRepository;
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

    public List<ComisionMateria> listarComisionesDisponibles() {
        return comisionRepository.listarComisiones();
    }

    public AlumnoInscripto inscribirAlumnoComision(String dni, String codigoComision) {
        Alumno alumno = obtenerAlumno(dni);
        ComisionMateria comision = comisionRepository.buscarPorCodigo(codigoComision)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe la comisión con código " + codigoComision));
        return alumnoService.inscribirAlumnoComision(alumno, comision);
    }

    public List<AlumnoInscripto> obtenerComisionesDeAlumno(String dni) {
        return alumnoService.obtenerComisionesDeAlumno(obtenerAlumno(dni));
    }

    public Alumno modificarAlumno(String dniOriginal, String nombre, String apellido,
                                   String nuevoDni, String email, String telefono) {
        return alumnoService.modificarAlumno(dniOriginal, nombre, apellido, nuevoDni, email, telefono);
    }

    public void eliminarAlumno(String dni) {
        alumnoService.eliminarAlumno(dni);
    }

    public Alumno obtenerAlumno(String dni) {
        return alumnoService.obtenerAlumno(dni);
    }
}
