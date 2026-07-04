package ar.edu.itec.controller;

import ar.edu.itec.enums.TipoEvaluacion;
import ar.edu.itec.model.ComisionMateria;
import ar.edu.itec.model.Examen;
import ar.edu.itec.service.ExamenService;

import java.time.LocalDate;
import java.util.List;

public class ExamenController {

    private final ExamenService examenService;

    public ExamenController(ExamenService examenService) {
        this.examenService = examenService;
    }

    public Examen registrarExamen(ComisionMateria comision, String nombre, String descripcion, LocalDate fecha, TipoEvaluacion tipoEvaluacion) {
        return examenService.registrarExamen(comision, nombre, descripcion, fecha, tipoEvaluacion);
    }

    public List<Examen> listarExamenes() {
        return examenService.listarExamenes();
    }

    public List<Examen> buscarExamenes(String criterio) {
        return examenService.buscarExamenes(criterio);
    }

    public Examen modificarExamen(Long id, ComisionMateria comision, String nombre, String descripcion, LocalDate fecha, TipoEvaluacion tipoEvaluacion) {
        return examenService.modificarExamen(id, comision, nombre, descripcion, fecha, tipoEvaluacion);
    }

    public void eliminarExamen(Long id) {
        examenService.eliminarExamen(id);
    }

    public Examen obtenerExamenPorId(Long id) {
        return examenService.obtenerExamenPorId(id);
    }
}
