package ar.edu.itec.service;

import ar.edu.itec.model.PlanEstudio;

import java.util.List;

public interface PlanEstudioService {

    List<PlanEstudio> listarPlanes();

    List<PlanEstudio> buscarPorCarrera(String codigoCarrera);
}
