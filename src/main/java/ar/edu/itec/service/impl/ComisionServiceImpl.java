package ar.edu.itec.service.impl;

import ar.edu.itec.model.ComisionMateria;
import ar.edu.itec.repository.ComisionRepository;
import ar.edu.itec.service.ComisionService;

import java.util.List;
import java.util.Optional;

public class ComisionServiceImpl implements ComisionService {

    private final ComisionRepository comisionRepository;

    public ComisionServiceImpl(ComisionRepository comisionRepository) {
        this.comisionRepository = comisionRepository;
    }

    @Override
    public List<ComisionMateria> listarComisiones() {
        return comisionRepository.listarComisiones();
    }

    @Override
    public Optional<ComisionMateria> buscarPorCodigo(String codigo) {
        return comisionRepository.buscarPorCodigo(codigo);
    }

    @Override
    public List<ComisionMateria> buscarPorMateria(String nombreMateria) {
        return comisionRepository.buscarPorMateria(nombreMateria);
    }

    @Override
    public List<ComisionMateria> buscarPorProfesor(String apellidoProfesor) {
        return comisionRepository.buscarPorProfesor(apellidoProfesor);
    }
}
