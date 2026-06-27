package ar.edu.itec.service;

import ar.edu.itec.model.Examen;
import ar.edu.itec.enums.TipoEvaluacion;
import ar.edu.itec.repository.ExamenRepository;

import java.time.LocalDate;
import java.util.List;

public class ExamenService {

    private final ExamenRepository examenRepository;

    public ExamenService(ExamenRepository examenRepository) {
        this.examenRepository = examenRepository;
    }

    public Examen registrarExamen(String nombre, String descripcion, LocalDate fecha, TipoEvaluacion tipoEvaluacion) {
        Examen examen = new Examen(nombre, descripcion, fecha, tipoEvaluacion);
        return examenRepository.guardar(examen);
    }

    public List<Examen> listarExamenes() {
        return examenRepository.buscarTodos();
    }

    public List<Examen> buscarExamenes(String criterio) {
        return examenRepository.buscarPorCriterio(criterio);
    }

    public Examen obtenerExamenPorId(Long id) {
        return examenRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe un examen con id " + id));
    }

    public Examen modificarExamen(Long id, String nombre, String descripcion, LocalDate fecha, TipoEvaluacion tipoEvaluacion) {
        Examen examen = obtenerExamenPorId(id);
        examen.setNombre(nombre);
        examen.setDescripcion(descripcion);
        examen.setFecha(fecha);
        examen.setTipoEvaluacion(tipoEvaluacion);
        return examenRepository.actualizar(examen);
    }

    public void eliminarExamen(Long id) {
        if (!examenRepository.eliminar(id)) {
            throw new IllegalArgumentException("No existe un examen con id " + id);
        }
    }
}
