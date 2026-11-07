package ar.edu.itec.service.impl;

import ar.edu.itec.model.AsistenciaAlumno;
import ar.edu.itec.repository.AsistenciaRepository;
import ar.edu.itec.service.AlumnoAsistenciaService;

import java.time.LocalDate;
import java.util.List;

public class AlumnoAsistenciaServiceImpl implements AlumnoAsistenciaService {

    private final AsistenciaRepository asistenciaRepository;

    public AlumnoAsistenciaServiceImpl(AsistenciaRepository asistenciaRepository) {
        this.asistenciaRepository = asistenciaRepository;
    }

    @Override
    public AsistenciaAlumno registrarAsistencia(String documentoAlumno, boolean presente) {
        LocalDate fecha = LocalDate.now();
        validarDocumento(documentoAlumno);
        if (existeAsistencia(documentoAlumno, fecha)) {
            throw new IllegalArgumentException(
                    "Ya existe una asistencia registrada para el documento " + documentoAlumno + " en la fecha " + fecha);
        }
        return asistenciaRepository.guardar(new AsistenciaAlumno(documentoAlumno, fecha, presente));
    }

    @Override
    public List<AsistenciaAlumno> listarAsistencias() {
        return asistenciaRepository.buscarTodos();
    }

    @Override
    public List<AsistenciaAlumno> buscarAsistencias(String criterio) {
        return asistenciaRepository.buscarPorCriterio(criterio);
    }

    @Override
    public AsistenciaAlumno obtenerAsistenciaPorId(Long id) {
        return asistenciaRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe una asistencia con id " + id));
    }

    @Override
    public AsistenciaAlumno modificarAsistencia(Long id, String documentoAlumno, LocalDate fecha, boolean presente) {
        AsistenciaAlumno asistencia = obtenerAsistenciaPorId(id);
        validarDocumento(documentoAlumno);
        validarFecha(fecha);
        if (!asistencia.getDocumentoAlumno().equalsIgnoreCase(documentoAlumno) || !asistencia.getFecha().equals(fecha)) {
            if (existeAsistencia(documentoAlumno, fecha)) {
                throw new IllegalArgumentException(
                        "Ya existe una asistencia registrada para el documento " + documentoAlumno + " en la fecha " + fecha);
            }
        }
        asistencia.setDocumentoAlumno(documentoAlumno);
        asistencia.setFecha(fecha);
        asistencia.setPresente(presente);
        return asistenciaRepository.actualizar(asistencia);
    }

    @Override
    public void eliminarAsistencia(Long id) {
        if (!asistenciaRepository.eliminar(id)) {
            throw new IllegalArgumentException("No existe una asistencia con id " + id);
        }
    }

    private boolean existeAsistencia(String documentoAlumno, LocalDate fecha) {
        return asistenciaRepository.buscarPorDocumento(documentoAlumno).stream()
                .anyMatch(asistencia -> asistencia.getFecha().equals(fecha));
    }

    private void validarDocumento(String documentoAlumno) {
        if (documentoAlumno == null || documentoAlumno.trim().isEmpty()) {
            throw new IllegalArgumentException("El documento del alumno no puede ser nulo o vacío");
        }
    }

    private void validarFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser null");
        }
    }
}
