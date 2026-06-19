package ar.edu.itec.repository;

import ar.edu.itec.entity.Examen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExamenRepository {

    private final List<Examen> examenes;

    public ExamenRepository() {
        this.examenes = new ArrayList<>();
    }

    public void guardar(Examen examen) {
        examenes.add(examen);
    }

    public Optional<Examen> buscarPorId(Long id) {
        for (Examen examen : examenes) {
            if (examen.getId() != null && examen.getId().equals(id)) {
                return Optional.of(examen);
            }
        }
        return Optional.empty();
    }

    public List<Examen> listarTodos() {
        return examenes;
    }
}
