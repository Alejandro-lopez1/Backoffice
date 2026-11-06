package ar.edu.itec.repository;

import ar.edu.itec.model.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

class AlumnoRepositoryTest {

    private AlumnoRepository repository;
    private Alumno alumno;

    @BeforeEach
    void setUp() {
        repository = new AlumnoRepository();
        alumno = new Alumno("Zoe", "Prueba", "50000001", "zoe@mail.com", "3764000001");
    }

    @Test
    void guardarYBuscarPorDni() {
        repository.guardar(alumno);
        assertTrue(repository.buscarPorDni("50000001").isPresent());
        assertEquals(alumno, repository.buscarPorDni("50000001").get());
    }

    @Test
    void buscarPorDniInexistente() {
        assertTrue(repository.buscarPorDni("00000000").isEmpty());
    }

    @Test
    void buscarPorNombre() {
        repository.guardar(alumno);
        List<Alumno> r = repository.buscarPorNombre("Zoe");
        assertEquals(1, r.size());
    }

    @Test
    void buscarPorApellido() {
        repository.guardar(alumno);
        List<Alumno> r = repository.buscarPorNombre("Prueba");
        assertEquals(1, r.size());
    }

    @Test
    void buscarPorNombreSinResultados() {
        repository.guardar(alumno);
        assertTrue(repository.buscarPorNombre("Inexistente").isEmpty());
    }

    @Test
    void buscarTodos() {
        int cantidadInicial = repository.buscarTodos().size();
        repository.guardar(alumno);
        repository.guardar(new Alumno("Axel", "Almada", "50000002", null, "3764000002"));
        assertEquals(cantidadInicial + 2, repository.buscarTodos().size());
    }

    @Test
    void guardarInscripcionComision() {
        ComisionMateria com = new ComisionMateria("COM-001", new Materia("Programación I"), null, null);
        AlumnoInscripto ai = new AlumnoInscripto(alumno, com);
        repository.guardarInscripcionComision(ai);
        assertTrue(repository.existeInscripcionComision(alumno, "COM-001"));
        assertEquals(1, repository.buscarInscripcionesComisionPorAlumno(alumno).size());
    }

    @Test
    void actualizarAlumnoReemplazaLosDatos() {
        repository.guardar(alumno);
        alumno.setNombre("Zoe Editada");
        alumno.setEmail("nuevo@mail.com");

        Alumno actualizado = repository.actualizar(alumno);

        assertEquals("Zoe Editada", actualizado.getNombre());
        assertEquals("nuevo@mail.com", repository.buscarPorDni("50000001").orElseThrow().getEmail());
    }

    @Test
    void actualizarAlumnoConNuevoDniLoBuscaPorElDniOriginal() {
        repository.guardar(alumno);
        alumno.setDni("50000099");

        repository.actualizar("50000001", alumno);

        assertTrue(repository.buscarPorDni("50000001").isEmpty());
        assertTrue(repository.buscarPorDni("50000099").isPresent());
    }

    @Test
    void actualizarAlumnoInexistenteLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> repository.actualizar(alumno));
    }

    @Test
    void eliminarAlumnoLoQuitaDelPadron() {
        repository.guardar(alumno);

        assertTrue(repository.eliminar("50000001"));
        assertTrue(repository.buscarPorDni("50000001").isEmpty());
    }

    @Test
    void eliminarAlumnoTambienEliminaSusInscripciones() {
        repository.guardar(alumno);
        ComisionMateria com = new ComisionMateria("COM-001", new Materia("Programación I"), null, null);
        repository.guardarInscripcionComision(new AlumnoInscripto(alumno, com));

        repository.eliminar("50000001");

        assertTrue(repository.buscarInscripcionesComisionPorAlumno(alumno).isEmpty());
        assertTrue(repository.buscarTodasLasInscripcionesComision().isEmpty());
    }

    @Test
    void eliminarAlumnoInexistenteRetornaFalse() {
        assertFalse(repository.eliminar("00000000"));
    }
}
