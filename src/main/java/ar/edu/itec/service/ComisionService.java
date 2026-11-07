package ar.edu.itec.service;

import ar.edu.itec.model.ComisionMateria;

import java.util.List;
import java.util.Optional;

public interface ComisionService {

    List<ComisionMateria> listarComisiones();

    Optional<ComisionMateria> buscarPorCodigo(String codigo);

    List<ComisionMateria> buscarPorMateria(String nombreMateria);

    List<ComisionMateria> buscarPorProfesor(String apellidoProfesor);
}
