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
        alumno = new Alumno("Lucas", "Giorgi", "40123456", "lucas@mail.com", "LEG-001");
    }

    @Test
    void guardarYBuscarPorDocumento() {
        repository.guardar(alumno);
        assertTrue(repository.buscarPorDocumento("40123456").isPresent());
        assertEquals(alumno, repository.buscarPorDocumento("40123456").get());
    }

    @Test
    void buscarPorDocumentoInexistente() {
        assertTrue(repository.buscarPorDocumento("99999999").isEmpty());
    }

    @Test
    void buscarPorLegajo() {
        repository.guardar(alumno);
        assertTrue(repository.buscarPorLegajo("LEG-001").isPresent());
    }

    @Test
    void buscarPorLegajoInexistente() {
        assertTrue(repository.buscarPorLegajo("LEG-999").isEmpty());
    }

    @Test
    void buscarPorNombre() {
        repository.guardar(alumno);
        List<Alumno> r = repository.buscarPorNombre("Lucas");
        assertEquals(1, r.size());
    }

    @Test
    void buscarPorApellido() {
        repository.guardar(alumno);
        List<Alumno> r = repository.buscarPorNombre("Giorgi");
        assertEquals(1, r.size());
    }

    @Test
    void buscarPorNombreSinResultados() {
        repository.guardar(alumno);
        assertTrue(repository.buscarPorNombre("Inexistente").isEmpty());
    }

    @Test
    void buscarTodos() {
        repository.guardar(alumno);
        repository.guardar(new Alumno("Axel", "Almada", "41234567", null, "LEG-002"));
        assertEquals(2, repository.buscarTodos().size());
    }

    @Test
    void guardarInscripcionCarrera() {
        Carrera c = new Carrera("TSD", "Tecnicatura");
        PlanEstudio p = new PlanEstudio(c, 2024);
        AlumnoCarrera ac = new AlumnoCarrera(alumno, c, p);
        repository.guardarInscripcionCarrera(ac);
        assertTrue(repository.existeInscripcionCarrera(alumno, "TSD"));
        assertEquals(1, repository.buscarInscripcionesCarreraPorAlumno(alumno).size());
    }

    @Test
    void guardarInscripcionComision() {
        ComisionMateria com = new ComisionMateria("COM-001", "Programación I");
        AlumnoInscripto ai = new AlumnoInscripto(alumno, com);
        repository.guardarInscripcionComision(ai);
        assertTrue(repository.existeInscripcionComision(alumno, "COM-001"));
        assertEquals(1, repository.buscarInscripcionesComisionPorAlumno(alumno).size());
    }
}
