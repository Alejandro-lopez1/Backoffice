package ar.edu.itec.service;

import ar.edu.itec.repository.CarreraRepository;
import ar.edu.itec.repository.MateriaRepository;
import ar.edu.itec.repository.PlanEstudioRepository;
import ar.edu.itec.service.impl.PlanEstudioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlanEstudioServiceTest {

    private PlanEstudioServiceImpl service;

    @BeforeEach
    void setUp() {
        CarreraRepository carreraRepository = new CarreraRepository();
        MateriaRepository materiaRepository = new MateriaRepository();
        service = new PlanEstudioServiceImpl(
                new PlanEstudioRepository(carreraRepository, materiaRepository));
    }

    @Test
    void listarPlanesDevuelveLosIniciales() {
        assertEquals(9, service.listarPlanes().size());
    }

    @Test
    void buscarPorCarreraExistente() {
        assertEquals(3, service.buscarPorCarrera("TSP").size());
    }

    @Test
    void buscarPorCarreraInexistenteONula() {
        assertTrue(service.buscarPorCarrera("XXX").isEmpty());
        assertTrue(service.buscarPorCarrera(null).isEmpty());
    }
}
