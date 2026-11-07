package ar.edu.itec.service.impl;

import ar.edu.itec.model.Carrera;
import ar.edu.itec.repository.CarreraRepository;
import ar.edu.itec.service.CarreraService;

import java.util.List;
import java.util.Optional;

public class CarreraServiceImpl implements CarreraService {

    private final CarreraRepository carreraRepository;

    public CarreraServiceImpl(CarreraRepository carreraRepository) {
        this.carreraRepository = carreraRepository;
    }

    @Override
    public List<Carrera> listarCarreras() {
        return carreraRepository.listarCarreras();
    }

    @Override
    public Optional<Carrera> buscarPorCodigo(String codigo) {
        return carreraRepository.buscarPorCodigo(codigo);
    }
}
