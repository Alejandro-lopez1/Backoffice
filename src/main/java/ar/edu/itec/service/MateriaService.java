package ar.edu.itec.service;

import ar.edu.itec.model.Materia;

import java.util.List;
import java.util.Optional;

public interface MateriaService {

    List<Materia> listarMaterias();

    Optional<Materia> buscarPorNombre(String nombre);
}
