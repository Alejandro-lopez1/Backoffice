package ar.edu.itec.service;

import ar.edu.itec.model.AsistenciaAlumno;

import java.time.LocalDate;
import java.util.List;

public interface AlumnoAsistenciaService {

    AsistenciaAlumno registrarAsistencia(String documentoAlumno, boolean presente);

    List<AsistenciaAlumno> listarAsistencias();

    List<AsistenciaAlumno> buscarAsistencias(String criterio);

    AsistenciaAlumno obtenerAsistenciaPorId(Long id);

    AsistenciaAlumno modificarAsistencia(Long id, String documentoAlumno, LocalDate fecha, boolean presente);

    void eliminarAsistencia(Long id);
}
