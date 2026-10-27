package ar.edu.itec.repository;

import ar.edu.itec.model.Materia;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MateriaRepository {

    private final List<Materia> materias;

    public MateriaRepository() {
        this.materias = new ArrayList<>();
        cargarMateriasIniciales();
    }

    private void cargarMateriasIniciales() {
        materias.add(new Materia("Programación I"));
        materias.add(new Materia("Programación II"));
        materias.add(new Materia("Base de Datos"));
    }

    public List<Materia> listarMaterias() {
        return new ArrayList<>(materias);
    }

    public Optional<Materia> buscarPorNombre(String nombre) {
        return materias.stream()
                .filter(m -> m.getNombre().equalsIgnoreCase(nombre))
                .findFirst();
    }

    public void guardar(Materia materia) {
        materias.add(materia);
    }
}
