package ar.edu.itec.repository;

import ar.edu.itec.entity.Nota;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NotaRepository {

    private final List<Nota> notas;

    public NotaRepository() {
        this.notas = new ArrayList<>();
    }

    public void guardar(Nota nota) {
        notas.add(nota);
    }

    public Optional<Nota> buscarPorId(Long id) {
        for (Nota nota : notas) {
            if (nota.getId() != null && nota.getId().equals(id)) {
                return Optional.of(nota);
            }
        }
        return Optional.empty();
    }

    public List<Nota> buscarPorAlumnoId(Long alumnoId) {
        List<Nota> notasDelAlumno = new ArrayList<>();

        for (Nota nota : notas) {
            if (nota.getAlumnoId() != null && nota.getAlumnoId().equals(alumnoId)) {
                notasDelAlumno.add(nota);
            }
        }

        return notasDelAlumno;
    }

    public List<Nota> listarTodas() {
        return notas;
    }
}
