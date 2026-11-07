package ar.edu.itec.service.impl;

import ar.edu.itec.model.PlanEstudio;
import ar.edu.itec.repository.PlanEstudioRepository;
import ar.edu.itec.service.PlanEstudioService;

import java.util.List;

public class PlanEstudioServiceImpl implements PlanEstudioService {

    private final PlanEstudioRepository planEstudioRepository;

    public PlanEstudioServiceImpl(PlanEstudioRepository planEstudioRepository) {
        this.planEstudioRepository = planEstudioRepository;
    }

    @Override
    public List<PlanEstudio> listarPlanes() {
        return planEstudioRepository.listarPlanes();
    }

    @Override
    public List<PlanEstudio> buscarPorCarrera(String codigoCarrera) {
        return planEstudioRepository.buscarPorCarrera(codigoCarrera);
    }
}
