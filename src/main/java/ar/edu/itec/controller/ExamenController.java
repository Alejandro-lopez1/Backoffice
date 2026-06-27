package ar.edu.itec.controller;

import ar.edu.itec.enums.TipoEvaluacion;
import ar.edu.itec.model.Carrera;
import ar.edu.itec.model.Examen;
import ar.edu.itec.service.ExamenService;
import ar.edu.itec.repository.CarreraRepository;

import java.time.LocalDate;
import java.util.List;

public class ExamenController {

    private final ExamenService examenService;
    private final CarreraRepository carreraRepository;

    public ExamenController(ExamenService examenService, CarreraRepository carreraRepository) {
        this.examenService = examenService;
        this.carreraRepository = carreraRepository;
    }

    public Examen registrarExamen(Carrera carrera, String nombre, String descripcion, LocalDate fecha, TipoEvaluacion tipoEvaluacion) {
        return examenService.registrarExamen(carrera, nombre, descripcion, fecha, tipoEvaluacion);
    }

    public List<Examen> listarExamenes() {
        return examenService.listarExamenes();
    }

    public List<Examen> buscarExamenes(String criterio) {
        return examenService.buscarExamenes(criterio);
    }

    public Examen modificarExamen(Long id, Carrera carrera, String nombre, String descripcion, LocalDate fecha, TipoEvaluacion tipoEvaluacion) {
        return examenService.modificarExamen(id, carrera, nombre, descripcion, fecha, tipoEvaluacion);
    }

    public void eliminarExamen(Long id) {
        examenService.eliminarExamen(id);
    }

    public Examen obtenerExamenPorId(Long id) {
        return examenService.obtenerExamenPorId(id);
    }

    public List<Carrera> listarCarrerasDisponibles() {
        return carreraRepository.listarCarreras();
    }

    public Carrera buscarCarreraPorCodigo(String codigo) {
        return carreraRepository.buscarPorCodigo(codigo)
                .orElseThrow(() -> new IllegalArgumentException("No existe una carrera con código " + codigo));
    }
}
