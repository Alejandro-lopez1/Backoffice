package ar.edu.itec.service;

import ar.edu.itec.model.ComisionMateria;
import ar.edu.itec.repository.ComisionRepository;
import ar.edu.itec.repository.MateriaRepository;
import ar.edu.itec.repository.ProfesorRepository;
import ar.edu.itec.service.impl.ComisionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ComisionServiceTest {

    private ComisionServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new ComisionServiceImpl(
                new ComisionRepository(new MateriaRepository(), new ProfesorRepository()));
    }

    @Test
    void listarComisionesDevuelveLasIniciales() {
        assertEquals(4, service.listarComisiones().size());
    }

    @Test
    void buscarPorCodigoExistenteEsCaseInsensitive() {
        assertTrue(service.buscarPorCodigo("Comisión-1").isPresent());
        assertTrue(service.buscarPorCodigo("comisión-1").isPresent());
    }

    @Test
    void buscarPorCodigoInexistenteDevuelveVacio() {
        assertTrue(service.buscarPorCodigo("Comisión-99").isEmpty());
    }

    @Test
    void buscarPorMateriaProgramacionIDevuelveSusDosComisiones() {
        List<ComisionMateria> comisiones = service.buscarPorMateria("Programación I");

        assertEquals(2, comisiones.size());
        assertTrue(comisiones.stream().allMatch(c -> c.getMateria().getNombre().equals("Programación I")));
    }

    @Test
    void buscarPorMateriaVaciaONulaDevuelveListaVacia() {
        assertTrue(service.buscarPorMateria(" ").isEmpty());
        assertTrue(service.buscarPorMateria(null).isEmpty());
    }

    @Test
    void buscarPorProfesorFiltraPorApellido() {
        assertEquals(2, service.buscarPorProfesor("García").size());
        assertEquals(1, service.buscarPorProfesor("Fernández").size());
    }

    @Test
    void buscarPorProfesorInexistenteDevuelveListaVacia() {
        assertTrue(service.buscarPorProfesor("Pérez").isEmpty());
    }
}
