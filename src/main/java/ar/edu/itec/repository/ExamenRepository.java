package ar.edu.itec.repository;

import ar.edu.itec.model.ComisionMateria;
import ar.edu.itec.model.Examen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExamenRepository {

    private final List<Examen> examenes;
    private long siguienteId;

    public ExamenRepository() {
        this.examenes = new ArrayList<>();
        this.siguienteId = 1L;
    }

    public Examen guardar(Examen examen) {
        if (examen == null) {
            throw new IllegalArgumentException("El examen no puede ser null");
        }
        if (examen.getId() == null) {
            examen.setId(siguienteId++);
        } else {
            siguienteId = Math.max(siguienteId, examen.getId() + 1);
        }
        examenes.add(examen);
        return examen;
    }

    public List<Examen> buscarTodos() {
        return new ArrayList<>(examenes);
    }

    public Optional<Examen> buscarPorId(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return examenes.stream()
                .filter(examen -> id.equals(examen.getId()))
                .findFirst();
    }

    public List<Examen> buscarPorComision(ComisionMateria comision) {
        return examenes.stream()
                .filter(e -> e.getComision().equals(comision))
                .toList();
    }

    public List<Examen> buscarPorCriterio(String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            return buscarTodos();
        }

        String normalizado = criterio.trim();
        List<Examen> resultados = new ArrayList<>();

        try {
            Long id = Long.parseLong(normalizado);
            buscarPorId(id).ifPresent(resultados::add);
        } catch (NumberFormatException ignored) {
            String criterioMinusculas = normalizado.toLowerCase();
            resultados.addAll(examenes.stream()
                    .filter(examen -> examen.getNombre() != null
                            && examen.getNombre().toLowerCase().contains(criterioMinusculas))
                    .toList());
        }

        return resultados;
    }

    public Examen actualizar(Examen examen) {
        if (examen == null || examen.getId() == null) {
            throw new IllegalArgumentException("El examen a actualizar debe tener un id");
        }

        for (int i = 0; i < examenes.size(); i++) {
            if (examenes.get(i).getId().equals(examen.getId())) {
                examenes.set(i, examen);
                return examen;
            }
        }

        throw new IllegalArgumentException("No existe un examen con id " + examen.getId());
    }

    public boolean eliminar(Long id) {
        return examenes.removeIf(examen -> examen.getId().equals(id));
    }
}
