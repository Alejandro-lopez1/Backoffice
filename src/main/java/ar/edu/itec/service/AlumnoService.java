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

    public Alumno registrarAlumno(String nombre, String apellido, String dni, String email, String telefono) {
        if (repository.buscarPorDni(dni).isPresent()) {
            throw new IllegalArgumentException("Ya existe un alumno con el DNI " + dni);
        }
        Alumno alumno = new Alumno(nombre, apellido, dni, email, telefono);
        repository.guardar(alumno);
        return alumno;
    }

    public List<Alumno> buscarAlumno(String criterio) {
        if (criterio == null || criterio.isBlank()) {
            return repository.buscarTodos();
        }
        Optional<Alumno> porDni = repository.buscarPorDni(criterio);
        if (porDni.isPresent()) {
            return List.of(porDni.get());
        }
        return repository.buscarPorNombre(criterio);
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

    public List<AlumnoInscripto> obtenerComisionesDeAlumno(Alumno alumno) {
        return repository.buscarInscripcionesComisionPorAlumno(alumno);
    }
}
