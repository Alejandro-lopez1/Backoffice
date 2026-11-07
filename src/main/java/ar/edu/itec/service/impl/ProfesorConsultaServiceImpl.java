package ar.edu.itec.service.impl;

import ar.edu.itec.model.Profesor;
import ar.edu.itec.repository.ProfesorRepository;
import ar.edu.itec.service.ProfesorConsultaService;

import java.util.List;

public class ProfesorConsultaServiceImpl implements ProfesorConsultaService {

    private final ProfesorRepository profesorRepository;

    public ProfesorConsultaServiceImpl(ProfesorRepository profesorRepository) {
        this.profesorRepository = profesorRepository;
    }

    @Override
    public List<Profesor> listarProfesores() {
        return profesorRepository.listarProfesores();
    }
}
