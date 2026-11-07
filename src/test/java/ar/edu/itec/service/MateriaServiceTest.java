package ar.edu.itec.service;

import ar.edu.itec.repository.MateriaRepository;
import ar.edu.itec.service.impl.MateriaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MateriaServiceTest {

    private MateriaServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new MateriaServiceImpl(new MateriaRepository());
    }

    @Test
    void listarMateriasDevuelveLasIniciales() {
        assertEquals(3, service.listarMaterias().size());
    }

    @Test
    void buscarPorNombreExistente() {
        assertTrue(service.buscarPorNombre("Programación I").isPresent());
        assertTrue(service.buscarPorNombre("programación i").isPresent());
    }

    @Test
    void buscarPorNombreInexistente() {
        assertTrue(service.buscarPorNombre("Física").isEmpty());
    }
}
