package ar.edu.itec.service;

import ar.edu.itec.repository.CarreraRepository;
import ar.edu.itec.service.impl.CarreraServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarreraServiceTest {

    private CarreraServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new CarreraServiceImpl(new CarreraRepository());
    }

    @Test
    void listarCarrerasDevuelveLasIniciales() {
        assertEquals(3, service.listarCarreras().size());
    }

    @Test
    void buscarPorCodigoExistente() {
        assertTrue(service.buscarPorCodigo("TSP").isPresent());
        assertTrue(service.buscarPorCodigo("tsp").isPresent());
    }

    @Test
    void buscarPorCodigoInexistenteONulo() {
        assertTrue(service.buscarPorCodigo("XXX").isEmpty());
        assertTrue(service.buscarPorCodigo(null).isEmpty());
    }
}
