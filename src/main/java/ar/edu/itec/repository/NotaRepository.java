package ar.edu.itec.repository;

import ar.edu.itec.model.Nota;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NotaRepository {

    private final List<Nota> notas;
    private long siguienteId;

    public NotaRepository() {
        this.notas = new ArrayList<>();
        this.siguienteId = 1L;
    }

    public Nota guardar(Nota nota) {
        if (nota == null) {
            throw new IllegalArgumentException("La nota no puede ser null");
        }
        if (nota.getId() == null) {
            nota.setId(siguienteId++);
        } else {
            siguienteId = Math.max(siguienteId, nota.getId() + 1);
        }
        notas.add(nota);
        return nota;
    }

    public List<Nota> buscarTodos() {
        return new ArrayList<>(notas);
    }

    public Optional<Nota> buscarPorId(Long id) {
        if (id == null) return Optional.empty();
        return notas.stream()
                .filter(n -> id.equals(n.getId()))
                .findFirst();
    }

    public List<Nota> buscarPorExamen(Long examenId) {
        return notas.stream()
                .filter(n -> n.getExamen() != null && examenId.equals(n.getExamen().getId()))
                .toList();
    }

    public Nota actualizar(Nota nota) {
        if (nota == null || nota.getId() == null) {
            throw new IllegalArgumentException("La nota a actualizar debe tener un id");
        }
        for (int i = 0; i < notas.size(); i++) {
            if (notas.get(i).getId().equals(nota.getId())) {
                notas.set(i, nota);
                return nota;
            }
        }
        throw new IllegalArgumentException("No existe una nota con id " + nota.getId());
    }

    public boolean eliminar(Long id) {
        return notas.removeIf(n -> n.getId().equals(id));
    }
}
