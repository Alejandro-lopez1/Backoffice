package ar.edu.itec.service;

import ar.edu.itec.model.*;
import ar.edu.itec.repository.AlumnoRepository;

import java.util.List;
import java.util.Optional;

public class AlumnoService {

    private final AlumnoRepository repository;

    public AlumnoService(AlumnoRepository repository) {
        this.repository = repository;
    }

    public Alumno registrarAlumno(String nombre, String apellido, String documento, String email, String legajo) {
        if (repository.buscarPorDocumento(documento).isPresent()) {
            throw new IllegalArgumentException("Ya existe un alumno con el documento " + documento);
        }
        if (repository.buscarPorLegajo(legajo).isPresent()) {
            throw new IllegalArgumentException("Ya existe un alumno con el legajo " + legajo);
        }
        Alumno alumno = new Alumno(nombre, apellido, documento, email, legajo);
        repository.guardar(alumno);
        return alumno;
    }

    public List<Alumno> buscarAlumno(String criterio) {
        if (criterio == null || criterio.isBlank()) {
            return repository.buscarTodos();
        }
        Optional<Alumno> porDocumento = repository.buscarPorDocumento(criterio);
        if (porDocumento.isPresent()) {
            return List.of(porDocumento.get());
        }
        Optional<Alumno> porLegajo = repository.buscarPorLegajo(criterio);
        if (porLegajo.isPresent()) {
            return List.of(porLegajo.get());
        }
        return repository.buscarPorNombre(criterio);
    }

    public AlumnoCarrera inscribirAlumnoCarrera(Alumno alumno, Carrera carrera) {
        if (repository.existeInscripcionCarrera(alumno, carrera.getCodigo())) {
            throw new IllegalArgumentException(
                    "El alumno " + alumno.getNombreCompleto() + " ya está inscripto en la carrera " + carrera.getNombre());
        }
        AlumnoCarrera inscripcion = new AlumnoCarrera(alumno, carrera);
        repository.guardarInscripcionCarrera(inscripcion);
        return inscripcion;
    }

    public AlumnoInscripto inscribirAlumnoComision(Alumno alumno, ComisionMateria comision) {
        if (repository.existeInscripcionComision(alumno, comision.getCodigo())) {
            throw new IllegalArgumentException(
                    "El alumno " + alumno.getNombreCompleto() + " ya está inscripto en la comisión " + comision.getCodigo());
        }
        List<AlumnoInscripto> existentes = repository.buscarInscripcionesComisionPorAlumno(alumno);
        for (AlumnoInscripto existente : existentes) {
            if (existente.getComision().chocaCon(comision)) {
                throw new IllegalArgumentException(
                        "La comisión " + comision + " tiene superposición horaria con " + existente.getComision());
            }
        }
        AlumnoInscripto inscripcion = new AlumnoInscripto(alumno, comision);
        repository.guardarInscripcionComision(inscripcion);
        return inscripcion;
    }

    public List<AlumnoCarrera> obtenerCarrerasDeAlumno(Alumno alumno) {
        return repository.buscarInscripcionesCarreraPorAlumno(alumno);
    }

    public List<AlumnoInscripto> obtenerComisionesDeAlumno(Alumno alumno) {
        return repository.buscarInscripcionesComisionPorAlumno(alumno);
    }
}
