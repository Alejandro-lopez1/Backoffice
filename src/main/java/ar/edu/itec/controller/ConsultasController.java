package ar.edu.itec.controller;

import ar.edu.itec.model.Alumno;
import ar.edu.itec.model.AlumnoInscripto;
import ar.edu.itec.model.Carrera;
import ar.edu.itec.model.ComisionMateria;
import ar.edu.itec.model.Examen;
import ar.edu.itec.model.Materia;
import ar.edu.itec.model.PlanEstudio;
import ar.edu.itec.model.Profesor;
import ar.edu.itec.service.AlumnoService;
import ar.edu.itec.service.CarreraService;
import ar.edu.itec.service.ComisionService;
import ar.edu.itec.service.MateriaService;
import ar.edu.itec.service.PlanEstudioService;
import ar.edu.itec.service.ProfesorConsultaService;
import ar.edu.itec.service.ProfesorExamenService;

import java.util.List;
import java.util.Optional;

public class ConsultasController {

    private final CarreraService carreraService;
    private final MateriaService materiaService;
    private final ProfesorConsultaService profesorConsultaService;
    private final PlanEstudioService planEstudioService;
    private final ComisionService comisionService;
    private final AlumnoService alumnoService;
    private final ProfesorExamenService profesorExamenService;

    public ConsultasController(CarreraService carreraService,
                               MateriaService materiaService,
                               ProfesorConsultaService profesorConsultaService,
                               PlanEstudioService planEstudioService,
                               ComisionService comisionService,
                               AlumnoService alumnoService,
                               ProfesorExamenService profesorExamenService) {
        this.carreraService = carreraService;
        this.materiaService = materiaService;
        this.profesorConsultaService = profesorConsultaService;
        this.planEstudioService = planEstudioService;
        this.comisionService = comisionService;
        this.alumnoService = alumnoService;
        this.profesorExamenService = profesorExamenService;
    }

    public List<Carrera> listarCarreras() {
        return carreraService.listarCarreras();
    }

    public Optional<Carrera> buscarCarreraPorCodigo(String codigo) {
        return carreraService.buscarPorCodigo(codigo);
    }

    public List<Materia> listarMaterias() {
        return materiaService.listarMaterias();
    }

    public List<Profesor> listarProfesores() {
        return profesorConsultaService.listarProfesores();
    }

    public List<PlanEstudio> listarPlanes() {
        return planEstudioService.listarPlanes();
    }

    public List<PlanEstudio> buscarPlanesPorCarrera(String codigoCarrera) {
        return planEstudioService.buscarPorCarrera(codigoCarrera);
    }

    public List<ComisionMateria> listarComisiones() {
        return comisionService.listarComisiones();
    }

    public List<ComisionMateria> buscarComisionesPorMateria(String nombreMateria) {
        return comisionService.buscarPorMateria(nombreMateria);
    }

    public List<ComisionMateria> buscarComisionesPorProfesor(String apellidoProfesor) {
        return comisionService.buscarPorProfesor(apellidoProfesor);
    }

    public List<Alumno> listarAlumnos() {
        return alumnoService.listarAlumnos();
    }

    public List<AlumnoInscripto> listarInscripcionesPorComision(ComisionMateria comision) {
        return alumnoService.buscarInscripcionesPorComision(comision);
    }

    public List<Examen> listarExamenesPorComision(ComisionMateria comision) {
        return profesorExamenService.buscarPorComision(comision);
    }
}
