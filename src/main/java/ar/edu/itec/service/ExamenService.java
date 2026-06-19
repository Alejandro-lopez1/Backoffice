package ar.edu.itec.service;

import ar.edu.itec.entity.Examen;
import ar.edu.itec.repository.ExamenRepository;

import java.util.List;
import java.util.Optional;

public class ExamenService {

    private final ExamenRepository examenRepository;

    public ExamenService(ExamenRepository examenRepository) {
        this.examenRepository = examenRepository;
    }

    public void registrarExamen(Examen examen) {
        examenRepository.guardar(examen);
    }

    public Optional<Examen> obtenerExamenPorId(Long id) {
        return examenRepository.buscarPorId(id);
    }

    public List<Examen> listarExamenes() {
        return examenRepository.listarTodos();
    }
}
