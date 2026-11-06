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

    public Alumno obtenerAlumno(String dni) {
        if (dni == null || dni.isBlank()) {
            throw new IllegalArgumentException("El DNI no puede ser nulo o vacío");
        }
        return repository.buscarPorDni(dni.trim())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró alumno con DNI " + dni));
    }

    public Alumno modificarAlumno(String dniOriginal, String nombre, String apellido,
                                   String nuevoDni, String email, String telefono) {
        Alumno alumno = obtenerAlumno(dniOriginal);

        String nombreFinal = validarTextoObligatorio(nombre, "El nombre");
        String apellidoFinal = validarTextoObligatorio(apellido, "El apellido");
        String dniFinal = validarTextoObligatorio(nuevoDni, "El DNI");

        boolean cambiaDni = !dniFinal.equalsIgnoreCase(alumno.getDni());
        if (cambiaDni && repository.buscarPorDni(dniFinal).isPresent()) {
            throw new IllegalArgumentException("Ya existe un alumno con el DNI " + dniFinal);
        }

        alumno.setNombre(nombreFinal);
        alumno.setApellido(apellidoFinal);
        alumno.setDni(dniFinal);
        alumno.setEmail(email);
        alumno.setTelefono(telefono);
        return repository.actualizar(dniOriginal, alumno);
    }

    public void eliminarAlumno(String dni) {
        Alumno alumno = obtenerAlumno(dni);
        if (!repository.eliminar(alumno.getDni())) {
            throw new IllegalArgumentException("No se encontró alumno con DNI " + dni);
        }
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

    private String validarTextoObligatorio(String valor, String descripcion) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(descripcion + " no puede ser nulo o vacío");
        }
        return valor.trim();
    }
}
