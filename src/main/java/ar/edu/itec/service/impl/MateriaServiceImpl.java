package ar.edu.itec.service.impl;

import ar.edu.itec.model.Materia;
import ar.edu.itec.repository.MateriaRepository;
import ar.edu.itec.service.MateriaService;

import java.util.List;
import java.util.Optional;

public class MateriaServiceImpl implements MateriaService {

    private final MateriaRepository materiaRepository;

    public MateriaServiceImpl(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    @Override
    public List<Materia> listarMaterias() {
        return materiaRepository.listarMaterias();
    }

    @Override
    public Optional<Materia> buscarPorNombre(String nombre) {
        return materiaRepository.buscarPorNombre(nombre);
    }
}
