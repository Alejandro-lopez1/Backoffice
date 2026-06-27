package ar.edu.itec.controller;

import ar.edu.itec.model.AsistenciaAlumno;
import ar.edu.itec.service.AsistenciaService;

import java.time.LocalDate;
import java.util.List;

public class AsistenciaController {

    private final AsistenciaService asistenciaService;

    public AsistenciaController(AsistenciaService asistenciaService) {
        this.asistenciaService = asistenciaService;
    }

    public AsistenciaAlumno registrarAsistencia(String documentoAlumno, boolean presente) {
        return asistenciaService.registrarAsistencia(documentoAlumno, presente);
    }

    public List<AsistenciaAlumno> listarAsistencias() {
        return asistenciaService.listarAsistencias();
    }

    public List<AsistenciaAlumno> buscarAsistencias(String criterio) {
        return asistenciaService.buscarAsistencias(criterio);
    }

    public AsistenciaAlumno modificarAsistencia(Long id, String documentoAlumno, LocalDate fecha, boolean presente) {
        return asistenciaService.modificarAsistencia(id, documentoAlumno, fecha, presente);
    }

    public void eliminarAsistencia(Long id) {
        asistenciaService.eliminarAsistencia(id);
    }

    public AsistenciaAlumno obtenerAsistenciaPorId(Long id) {
        return asistenciaService.obtenerAsistenciaPorId(id);
    }
}
