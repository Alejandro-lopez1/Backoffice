package ar.edu.itec.repository;

import ar.edu.itec.model.ComisionMateria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ComisionRepositoryTest {

    private ComisionRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ComisionRepository(new MateriaRepository(), new ProfesorRepository());
    }

    @Test
    void buscarPorMateriaProgramacionIDevuelveSoloSusComisiones() {
        List<ComisionMateria> comisiones = repository.buscarPorMateria("Programación I");

        Set<String> codigos = comisiones.stream()
                .map(ComisionMateria::getCodigo)
                .collect(Collectors.toSet());
        assertEquals(Set.of("Comisión-1", "Comisión-2"), codigos);
    }

    @Test
    void buscarPorMateriaProgramacionIINoIncluyeProgramacionI() {
        List<ComisionMateria> comisiones = repository.buscarPorMateria("Programación II");

        assertEquals(1, comisiones.size());
        assertEquals("Comisión-3", comisiones.get(0).getCodigo());
    }

    @Test
    void buscarPorMateriaParcialNoDevuelveResultados() {
        assertTrue(repository.buscarPorMateria("Program").isEmpty());
    }

    @Test
    void buscarPorCodigoIgnoraMayusculas() {
        assertTrue(repository.buscarPorCodigo("comisión-1").isPresent());
        assertTrue(repository.buscarPorCodigo("COMISIÓN-1").isPresent());
    }
}
