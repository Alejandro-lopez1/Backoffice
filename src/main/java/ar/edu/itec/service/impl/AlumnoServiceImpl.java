package ar.edu.itec.service.impl;

import ar.edu.itec.model.Alumno;
import ar.edu.itec.model.AlumnoInscripto;
import ar.edu.itec.model.ComisionMateria;
import ar.edu.itec.repository.AlumnoRepository;
import ar.edu.itec.repository.ComisionRepository;
import ar.edu.itec.service.AlumnoService;

import java.util.List;
import java.util.Optional;

public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final ComisionRepository comisionRepository;

    public AlumnoServiceImpl(AlumnoRepository alumnoRepository, ComisionRepository comisionRepository) {
        this.alumnoRepository = alumnoRepository;
        this.comisionRepository = comisionRepository;
    }

    @Override
    public Alumno registrarAlumno(String nombre, String apellido, String dni, String email, String telefono) {
        if (alumnoRepository.buscarPorDni(dni).isPresent()) {
            throw new IllegalArgumentException("Ya existe un alumno con el DNI " + dni);
        }
        Alumno alumno = new Alumno(nombre, apellido, dni, email, telefono);
        alumnoRepository.guardar(alumno);
        return alumno;
    }

    @Override
    public List<Alumno> buscarAlumno(String criterio) {
        if (criterio == null || criterio.isBlank()) {
            return alumnoRepository.buscarTodos();
        }
        Optional<Alumno> porDni = alumnoRepository.buscarPorDni(criterio);
        if (porDni.isPresent()) {
            return List.of(porDni.get());
        }
        return alumnoRepository.buscarPorNombre(criterio);
    }

    @Override
    public List<Alumno> listarAlumnos() {
        return alumnoRepository.buscarTodos();
    }

    @Override
    public Alumno obtenerAlumno(String dni) {
        if (dni == null || dni.isBlank()) {
            throw new IllegalArgumentException("El DNI no puede ser nulo o vacío");
        }
        return alumnoRepository.buscarPorDni(dni.trim())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró alumno con DNI " + dni));
    }

    @Override
    public Alumno modificarAlumno(String dniOriginal, String nombre, String apellido,
                                  String nuevoDni, String email, String telefono) {
        Alumno alumno = obtenerAlumno(dniOriginal);

        String nombreFinal = validarTextoObligatorio(nombre, "El nombre");
        String apellidoFinal = validarTextoObligatorio(apellido, "El apellido");
        String dniFinal = validarTextoObligatorio(nuevoDni, "El DNI");

        boolean cambiaDni = !dniFinal.equalsIgnoreCase(alumno.getDni());
        if (cambiaDni && alumnoRepository.buscarPorDni(dniFinal).isPresent()) {
            throw new IllegalArgumentException("Ya existe un alumno con el DNI " + dniFinal);
        }

        alumno.setNombre(nombreFinal);
        alumno.setApellido(apellidoFinal);
        alumno.setDni(dniFinal);
        alumno.setEmail(email);
        alumno.setTelefono(telefono);
        return alumnoRepository.actualizar(dniOriginal, alumno);
    }

    @Override
    public void eliminarAlumno(String dni) {
        Alumno alumno = obtenerAlumno(dni);
        if (!alumnoRepository.eliminar(alumno.getDni())) {
            throw new IllegalArgumentException("No se encontró alumno con DNI " + dni);
        }
    }

    @Override
    public AlumnoInscripto inscribirAlumnoComision(Alumno alumno, ComisionMateria comision) {
        if (alumnoRepository.existeInscripcionComision(alumno, comision.getCodigo())) {
            throw new IllegalArgumentException(
                    "El alumno " + alumno.getNombreCompleto() + " ya está inscripto en la comisión " + comision.getCodigo());
        }
        List<AlumnoInscripto> existentes = alumnoRepository.buscarInscripcionesComisionPorAlumno(alumno);
        for (AlumnoInscripto existente : existentes) {
            if (existente.getComision().chocaCon(comision)) {
                throw new IllegalArgumentException(
                        "La comisión " + comision + " tiene superposición horaria con " + existente.getComision());
            }
        }
        AlumnoInscripto inscripcion = new AlumnoInscripto(alumno, comision);
        alumnoRepository.guardarInscripcionComision(inscripcion);
        return inscripcion;
    }

    @Override
    public AlumnoInscripto inscribirAlumnoComision(String dni, String codigoComision) {
        Alumno alumno = obtenerAlumno(dni);
        ComisionMateria comision = comisionRepository.buscarPorCodigo(codigoComision)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe la comisión con código " + codigoComision));
        return inscribirAlumnoComision(alumno, comision);
    }

    @Override
    public List<AlumnoInscripto> obtenerComisionesDeAlumno(Alumno alumno) {
        return alumnoRepository.buscarInscripcionesComisionPorAlumno(alumno);
    }

    @Override
    public List<AlumnoInscripto> buscarInscripcionesPorComision(ComisionMateria comision) {
        return alumnoRepository.buscarInscripcionesPorComision(comision);
    }

    private String validarTextoObligatorio(String valor, String descripcion) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(descripcion + " no puede ser nulo o vacío");
        }
        return valor.trim();
    }
}
