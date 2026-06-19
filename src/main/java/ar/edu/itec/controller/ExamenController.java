package ar.edu.itec.controller;

import ar.edu.itec.entity.Examen;
import ar.edu.itec.service.ExamenService;

import java.util.List;
import java.util.Optional;

public class ExamenController {

    private final ExamenService examenService;

    public ExamenController(ExamenService examenService) {
        this.examenService = examenService;
    }

    public void registrarExamen(Examen examen) {
        examenService.registrarExamen(examen);
    }

    public Optional<Examen> obtenerExamenPorId(Long id) {
        return examenService.obtenerExamenPorId(id);
    }

    public List<Examen> listarExamenes() {
        return examenService.listarExamenes();
    }
}
