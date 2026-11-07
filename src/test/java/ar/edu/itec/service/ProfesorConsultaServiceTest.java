package ar.edu.itec.service;

import ar.edu.itec.model.Profesor;
import ar.edu.itec.repository.ProfesorRepository;
import ar.edu.itec.service.impl.ProfesorConsultaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProfesorConsultaServiceTest {

    private ProfesorConsultaServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new ProfesorConsultaServiceImpl(new ProfesorRepository());
    }

    @Test
    void listarProfesoresDevuelveLosIniciales() {
        List<Profesor> profesores = service.listarProfesores();

        assertEquals(4, profesores.size());
        assertTrue(profesores.stream().anyMatch(p -> p.getApellido().equals("García")));
        assertTrue(profesores.stream().anyMatch(p -> p.getApellido().equals("Sánchez")));
    }

    @Test
    void listarProfesoresDevuelveCopiaIndependiente() {
        List<Profesor> primera = service.listarProfesores();
        primera.clear();

        assertEquals(4, service.listarProfesores().size());
    }
}
