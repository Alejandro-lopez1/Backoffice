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

    @BeforeEach
    void setUp() {
        service = new AlumnoService(new AlumnoRepository());
        alumno = service.registrarAlumno("Zoe", "Prueba", "50000001", "zoe@mail.com", "3764000001");
    }

    @Test
    void registrarAlumno() {
        assertEquals("Zoe", alumno.getNombre());
    }

    @Test
    void registrarAlumnoDniDuplicadoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                service.registrarAlumno("Otro", "Nombre", "50000001", "otro@mail.com", "3764000009"));
    }

    @Test
    void registrarAlumnoConDniDeDatosInicialesLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                service.registrarAlumno("Otro", "Nombre", "40123456", "otro@mail.com", "3764000009"));
    }

    @Test
    void buscarAlumnoPorDni() {
        List<Alumno> r = service.buscarAlumno("50000001");
        assertEquals(1, r.size());
        assertEquals(alumno, r.get(0));
    }

    @Test
    void buscarAlumnoPorNombre() {
        List<Alumno> r = service.buscarAlumno("Zoe");
        assertEquals(1, r.size());
    }

    @Test
    void buscarAlumnoCriterioVacioRetornaTodos() {
        int cantidadInicial = service.buscarAlumno("").size();
        service.registrarAlumno("Axel", "Almada", "50000002", null, "3764000002");
        assertEquals(cantidadInicial + 1, service.buscarAlumno("").size());
    }

    @Test
    void buscarAlumnoNullRetornaTodos() {
        int cantidadInicial = service.buscarAlumno(null).size();
        service.registrarAlumno("Axel", "Almada", "50000002", null, "3764000002");
        assertEquals(cantidadInicial + 1, service.buscarAlumno(null).size());
    }

    @Test
    void inscribirAlumnoComision() {
        ComisionMateria com = new ComisionMateria("COM-001", new Materia("Programación I"), null, null);
        AlumnoInscripto ai = service.inscribirAlumnoComision(alumno, com);
        assertNotNull(ai);
        assertEquals(com, ai.getComision());
    }

    @Test
    void inscribirAlumnoComisionDuplicadoLanzaExcepcion() {
        ComisionMateria com = new ComisionMateria("COM-001", new Materia("Programación I"), null, null);
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

        ComisionMateria com1 = new ComisionMateria("COM-001", new Materia("Programación I"), null, null);
        com1.agregarHorario(h1);
        service.inscribirAlumnoComision(alumno, com1);

        ComisionMateria com2 = new ComisionMateria("COM-002", new Materia("Programación II"), null, null);
        com2.agregarHorario(h2);
        assertThrows(IllegalArgumentException.class, () ->
                service.inscribirAlumnoComision(alumno, com2));
    }

    @Test
    void obtenerComisionesDeAlumno() {
        ComisionMateria com = new ComisionMateria("COM-001", new Materia("Programación I"), null, null);
        service.inscribirAlumnoComision(alumno, com);
        List<AlumnoInscripto> comisiones = service.obtenerComisionesDeAlumno(alumno);
        assertEquals(1, comisiones.size());
    }

    @Test
    void modificarAlumnoActualizaSusDatos() {
        Alumno actualizado = service.modificarAlumno(
                "50000001", "Zoe Editada", "Prueba Editada", "50000099", "editado@mail.com", "3764111111");

        assertEquals("Zoe Editada", actualizado.getNombre());
        assertEquals("Prueba Editada", actualizado.getApellido());
        assertEquals("50000099", actualizado.getDni());
        assertEquals("editado@mail.com", actualizado.getEmail());
        assertEquals("3764111111", actualizado.getTelefono());
        assertTrue(service.buscarAlumno("50000001").isEmpty());
        assertEquals(actualizado, service.obtenerAlumno("50000099"));
    }

    @Test
    void modificarAlumnoConDniDuplicadoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                service.modificarAlumno("50000001", "Zoe", "Prueba", "40123456", "zoe@mail.com", "3764000001"));
    }

    @Test
    void modificarAlumnoInexistenteLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                service.modificarAlumno("99999999", "Zoe", "Prueba", "99999999", "zoe@mail.com", "3764000001"));
    }

    @Test
    void modificarAlumnoConCamposObligatoriosVaciosLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                service.modificarAlumno("50000001", " ", "Prueba", "50000001", "zoe@mail.com", "3764000001"));
    }

    @Test
    void modificarAlumnoConservaSusInscripciones() {
        ComisionMateria com = new ComisionMateria("COM-001", new Materia("Programación I"), null, null);
        service.inscribirAlumnoComision(alumno, com);

        service.modificarAlumno("50000001", "Zoe", "Prueba", "50000099", "zoe@mail.com", "3764000001");

        assertEquals(1, service.obtenerComisionesDeAlumno(alumno).size());
    }

    @Test
    void eliminarAlumnoLoQuitaDelPadron() {
        service.eliminarAlumno("50000001");

        assertTrue(service.buscarAlumno("50000001").isEmpty());
    }

    @Test
    void eliminarAlumnoInexistenteLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> service.eliminarAlumno("99999999"));
    }
}
