package ar.edu.itec.controller;

import ar.edu.itec.model.Alumno;
import ar.edu.itec.model.AlumnoInscripto;
import ar.edu.itec.repository.AlumnoRepository;
import ar.edu.itec.repository.CarreraRepository;
import ar.edu.itec.repository.ComisionRepository;
import ar.edu.itec.repository.MateriaRepository;
import ar.edu.itec.repository.ProfesorRepository;
import ar.edu.itec.service.AlumnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlumnoControllerTest {

    private AlumnoController controller;
    private AlumnoRepository alumnoRepository;
    private ComisionRepository comisionRepository;

    @BeforeEach
    void setUp() {
        CarreraRepository carreraRepository = new CarreraRepository();
        MateriaRepository materiaRepository = new MateriaRepository();
        ProfesorRepository profesorRepository = new ProfesorRepository();
        comisionRepository = new ComisionRepository(materiaRepository, profesorRepository);
        alumnoRepository = new AlumnoRepository();
        AlumnoService alumnoService = new AlumnoService(alumnoRepository);
        controller = new AlumnoController(
                alumnoService, alumnoRepository, carreraRepository, comisionRepository);
        controller.registrarAlumno("Zoe", "Prueba", "50000001", "zoe@mail.com", "3764000001");
    }

    @Test
    void inscribirConComisionRealConservaProfesorYHorarios() {
        AlumnoInscripto inscripcion = controller.inscribirAlumnoComision("50000001", "Comisión-1");

        assertNotNull(inscripcion.getComision().getProfesor());
        assertEquals(2, inscripcion.getComision().getHorarios().size());
        assertEquals(
                comisionRepository.buscarPorCodigo("Comisión-1").orElseThrow(),
                inscripcion.getComision());
    }

    @Test
    void laInscripcionQuedaDisponibleParaElModuloDeNotas() {
        AlumnoInscripto inscripcion = controller.inscribirAlumnoComision("50000001", "Comisión-1");

        assertEquals(1, alumnoRepository.buscarInscripcionesPorComision(inscripcion.getComision()).size());
    }

    @Test
    void inscribirConCodigoInexistenteLanzaExcepcion() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () ->
                controller.inscribirAlumnoComision("50000001", "COM-999"));

        assertTrue(error.getMessage().contains("No existe la comisión"));
    }

    @Test
    void modificarAlumnoActualizaElPadron() {
        Alumno actualizado = controller.modificarAlumno(
                "50000001", "Zoe", "Prueba", "50000001", "nuevo@mail.com", "3764000099");

        assertEquals("nuevo@mail.com", actualizado.getEmail());
        assertEquals("nuevo@mail.com", controller.obtenerAlumno("50000001").getEmail());
    }

    @Test
    void eliminarAlumnoLoQuitaDelPadron() {
        controller.eliminarAlumno("50000001");

        assertTrue(controller.buscarAlumno("50000001").isEmpty());
    }
}
