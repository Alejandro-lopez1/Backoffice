package ar.edu.itec.service;

import ar.edu.itec.model.Carrera;

import java.util.List;
import java.util.Optional;

public interface CarreraService {

    List<Carrera> listarCarreras();

    Optional<Carrera> buscarPorCodigo(String codigo);
}
