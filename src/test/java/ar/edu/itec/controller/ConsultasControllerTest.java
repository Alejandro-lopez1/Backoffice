package ar.edu.itec.controller;

import ar.edu.itec.enums.TipoEvaluacion;
import ar.edu.itec.model.ComisionMateria;
import ar.edu.itec.repository.AlumnoRepository;
import ar.edu.itec.repository.CarreraRepository;
import ar.edu.itec.repository.ComisionRepository;
import ar.edu.itec.repository.ExamenRepository;
import ar.edu.itec.repository.MateriaRepository;
import ar.edu.itec.repository.PlanEstudioRepository;
import ar.edu.itec.repository.ProfesorRepository;
import ar.edu.itec.service.impl.AlumnoServiceImpl;
import ar.edu.itec.service.impl.CarreraServiceImpl;
import ar.edu.itec.service.impl.ComisionServiceImpl;
import ar.edu.itec.service.impl.MateriaServiceImpl;
import ar.edu.itec.service.impl.PlanEstudioServiceImpl;
import ar.edu.itec.service.impl.ProfesorConsultaServiceImpl;
import ar.edu.itec.service.impl.ProfesorExamenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ConsultasControllerTest {

    private ConsultasController controller;
    private AlumnoServiceImpl alumnoService;
    private ProfesorExamenServiceImpl profesorExamenService;
    private ComisionRepository comisionRepository;

    @BeforeEach
    void setUp() {
        CarreraRepository carreraRepository = new CarreraRepository();
        MateriaRepository materiaRepository = new MateriaRepository();
        ProfesorRepository profesorRepository = new ProfesorRepository();
        PlanEstudioRepository planEstudioRepository =
                new PlanEstudioRepository(carreraRepository, materiaRepository);
        comisionRepository = new ComisionRepository(materiaRepository, profesorRepository);
        AlumnoRepository alumnoRepository = new AlumnoRepository();
        ExamenRepository examenRepository = new ExamenRepository();

        CarreraServiceImpl carreraService = new CarreraServiceImpl(carreraRepository);
        MateriaServiceImpl materiaService = new MateriaServiceImpl(materiaRepository);
        ProfesorConsultaServiceImpl profesorConsultaService =
                new ProfesorConsultaServiceImpl(profesorRepository);
        PlanEstudioServiceImpl planEstudioService = new PlanEstudioServiceImpl(planEstudioRepository);
        ComisionServiceImpl comisionService = new ComisionServiceImpl(comisionRepository);
        alumnoService = new AlumnoServiceImpl(alumnoRepository, comisionRepository);
        profesorExamenService = new ProfesorExamenServiceImpl(examenRepository);

        controller = new ConsultasController(
                carreraService, materiaService, profesorConsultaService, planEstudioService,
                comisionService, alumnoService, profesorExamenService);
    }

    @Test
    void listarCarrerasDelegaEnElServicio() {
        assertEquals(3, controller.listarCarreras().size());
    }

    @Test
    void buscarCarreraPorCodigoExistenteEInexistente() {
        assertTrue(controller.buscarCarreraPorCodigo("TSP").isPresent());
        assertTrue(controller.buscarCarreraPorCodigo("XXX").isEmpty());
    }

    @Test
    void listarMateriasDelegaEnElServicio() {
        assertEquals(3, controller.listarMaterias().size());
    }

    @Test
    void listarProfesoresDelegaEnElServicio() {
        assertEquals(4, controller.listarProfesores().size());
    }

    @Test
    void listarPlanesDelegaEnElServicio() {
        assertEquals(9, controller.listarPlanes().size());
    }

    @Test
    void buscarPlanesPorCarreraExistenteEInexistente() {
        assertEquals(3, controller.buscarPlanesPorCarrera("TSP").size());
        assertTrue(controller.buscarPlanesPorCarrera("XXX").isEmpty());
    }

    @Test
    void listarComisionesDelegaEnElServicio() {
        assertEquals(4, controller.listarComisiones().size());
    }

    @Test
    void buscarComisionesPorMateriaDelegaEnElServicio() {
        assertEquals(2, controller.buscarComisionesPorMateria("Programación I").size());
    }

    @Test
    void buscarComisionesPorProfesorDelegaEnElServicio() {
        assertEquals(2, controller.buscarComisionesPorProfesor("García").size());
    }

    @Test
    void listarAlumnosDelegaEnElServicio() {
        assertEquals(4, controller.listarAlumnos().size());
    }

    @Test
    void listarInscripcionesPorComisionDelegaEnElServicio() {
        ComisionMateria comision = comisionRepository.buscarPorCodigo("Comisión-1").orElseThrow();
        alumnoService.inscribirAlumnoComision("40123456", "Comisión-1");

        assertEquals(1, controller.listarInscripcionesPorComision(comision).size());
    }

    @Test
    void listarExamenesPorComisionDelegaEnElServicio() {
        ComisionMateria comision = comisionRepository.buscarPorCodigo("Comisión-1").orElseThrow();
        profesorExamenService.registrarExamen(comision, "Parcial 1", "Unidades 1 a 3",
                LocalDate.of(2026, 11, 15), TipoEvaluacion.PARCIAL);

        assertEquals(1, controller.listarExamenesPorComision(comision).size());
    }
}
