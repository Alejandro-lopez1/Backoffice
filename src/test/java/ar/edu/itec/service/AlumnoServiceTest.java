package ar.edu.itec.service;

import ar.edu.itec.model.*;
import ar.edu.itec.repository.AlumnoRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

class AlumnoServiceTest {

    private AlumnoService service;
    private Alumno alumno;
    private Carrera carrera;
    private PlanEstudio plan;

    @BeforeEach
    void setUp() {
        service = new AlumnoService(new AlumnoRepository());
        alumno = service.registrarAlumno("Lucas", "Giorgi", "40123456", "lucas@mail.com", "LEG-001");
        carrera = new Carrera("TSD", "Tecnicatura en Desarrollo de Software");
        plan = new PlanEstudio(carrera, 2024);
    }

    @Test
    void registrarAlumno() {
        assertEquals("Lucas", alumno.getNombre());
    }

    @Test
    void registrarAlumnoDocumentoDuplicadoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                service.registrarAlumno("Otro", "Nombre", "40123456", "otro@mail.com", "LEG-999"));
    }

    @Test
    void registrarAlumnoLegajoDuplicadoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                service.registrarAlumno("Otro", "Nombre", "99999999", "otro@mail.com", "LEG-001"));
    }

    @Test
    void buscarAlumnoPorDocumento() {
        List<Alumno> r = service.buscarAlumno("40123456");
        assertEquals(1, r.size());
        assertEquals(alumno, r.get(0));
    }

    @Test
    void buscarAlumnoPorLegajo() {
        List<Alumno> r = service.buscarAlumno("LEG-001");
        assertEquals(1, r.size());
    }

    @Test
    void buscarAlumnoPorNombre() {
        List<Alumno> r = service.buscarAlumno("Lucas");
        assertEquals(1, r.size());
    }

    @Test
    void buscarAlumnoCriterioVacioRetornaTodos() {
        service.registrarAlumno("Axel", "Almada", "41234567", null, "LEG-002");
        assertEquals(2, service.buscarAlumno("").size());
    }

    @Test
    void buscarAlumnoNullRetornaTodos() {
        service.registrarAlumno("Axel", "Almada", "41234567", null, "LEG-002");
        assertEquals(2, service.buscarAlumno(null).size());
    }

    @Test
    void inscribirAlumnoCarrera() {
        AlumnoCarrera ac = service.inscribirAlumnoCarrera(alumno, carrera, plan);
        assertNotNull(ac);
        assertEquals(carrera, ac.getCarrera());
    }

    @Test
    void inscribirAlumnoCarreraDuplicadoLanzaExcepcion() {
        service.inscribirAlumnoCarrera(alumno, carrera, plan);
        assertThrows(IllegalArgumentException.class, () ->
                service.inscribirAlumnoCarrera(alumno, carrera, plan));
    }

    @Test
    void inscribirAlumnoComision() {
        ComisionMateria com = new ComisionMateria("COM-001", "Programación I");
        AlumnoInscripto ai = service.inscribirAlumnoComision(alumno, com);
        assertNotNull(ai);
        assertEquals(com, ai.getComision());
    }

    @Test
    void inscribirAlumnoComisionDuplicadoLanzaExcepcion() {
        ComisionMateria com = new ComisionMateria("COM-001", "Programación I");
        service.inscribirAlumnoComision(alumno, com);
        assertThrows(IllegalArgumentException.class, () ->
                service.inscribirAlumnoComision(alumno, com));
    }

    @Test
    void inscribirAlumnoComisionSuperpuestaLanzaExcepcion() {
        HorarioClase h1 = new HorarioClase(DayOfWeek.MONDAY);
        h1.agregarModulo(new ModuloHorario(1, LocalTime.of(18, 0), LocalTime.of(20, 0)));

        HorarioClase h2 = new HorarioClase(DayOfWeek.MONDAY);
        h2.agregarModulo(new ModuloHorario(1, LocalTime.of(19, 0), LocalTime.of(21, 0)));

        ComisionMateria com1 = new ComisionMateria("COM-001", "Programación I");
        com1.agregarHorario(h1);
        service.inscribirAlumnoComision(alumno, com1);

        ComisionMateria com2 = new ComisionMateria("COM-002", "Programación I");
        com2.agregarHorario(h2);
        assertThrows(IllegalArgumentException.class, () ->
                service.inscribirAlumnoComision(alumno, com2));
    }

    @Test
    void obtenerCarrerasDeAlumno() {
        service.inscribirAlumnoCarrera(alumno, carrera, plan);
        List<AlumnoCarrera> carreras = service.obtenerCarrerasDeAlumno(alumno);
        assertEquals(1, carreras.size());
    }

    @Test
    void obtenerComisionesDeAlumno() {
        ComisionMateria com = new ComisionMateria("COM-001", "Programación I");
        service.inscribirAlumnoComision(alumno, com);
        List<AlumnoInscripto> comisiones = service.obtenerComisionesDeAlumno(alumno);
        assertEquals(1, comisiones.size());
    }
}
