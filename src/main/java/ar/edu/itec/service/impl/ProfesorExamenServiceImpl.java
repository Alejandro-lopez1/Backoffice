package ar.edu.itec.service.impl;

import ar.edu.itec.enums.TipoEvaluacion;
import ar.edu.itec.model.ComisionMateria;
import ar.edu.itec.model.Examen;
import ar.edu.itec.repository.ExamenRepository;
import ar.edu.itec.service.ProfesorExamenService;

import java.time.LocalDate;
import java.util.List;

public class ProfesorExamenServiceImpl implements ProfesorExamenService {

    private final ExamenRepository examenRepository;

    public ProfesorExamenServiceImpl(ExamenRepository examenRepository) {
        this.examenRepository = examenRepository;
    }

    @Override
    public Examen registrarExamen(ComisionMateria comision, String nombre, String descripcion,
                                  LocalDate fecha, TipoEvaluacion tipoEvaluacion) {
        Examen examen = new Examen(comision, nombre, descripcion, fecha, tipoEvaluacion);
        return examenRepository.guardar(examen);
    }

    @Override
    public List<Examen> listarExamenes() {
        return examenRepository.buscarTodos();
    }

    @Override
    public List<Examen> buscarExamenes(String criterio) {
        return examenRepository.buscarPorCriterio(criterio);
    }

    @Override
    public Examen obtenerExamenPorId(Long id) {
        return examenRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe un examen con id " + id));
    }

    @Override
    public Examen modificarExamen(Long id, ComisionMateria comision, String nombre, String descripcion,
                                  LocalDate fecha, TipoEvaluacion tipoEvaluacion) {
        Examen examen = obtenerExamenPorId(id);
        examen.setComision(comision);
        examen.setNombre(nombre);
        examen.setDescripcion(descripcion);
        examen.setFecha(fecha);
        examen.setTipoEvaluacion(tipoEvaluacion);
        return examenRepository.actualizar(examen);
    }

    @Override
    public void eliminarExamen(Long id) {
        if (!examenRepository.eliminar(id)) {
            throw new IllegalArgumentException("No existe un examen con id " + id);
        }
    }

    @Override
    public List<Examen> buscarPorComision(ComisionMateria comision) {
        return examenRepository.buscarPorComision(comision);
    }
}
