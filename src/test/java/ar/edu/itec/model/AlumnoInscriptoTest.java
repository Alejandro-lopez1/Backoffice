package ar.edu.itec.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

class AlumnoInscriptoTest {

    private Alumno alumno;
    private ComisionMateria comision;

    @BeforeEach
    void setUp() {
        alumno = new Alumno("Lucas", "Giorgi", "40123456", "lucas@mail.com", "LEG-001");
        comision = new ComisionMateria("COM-001", "Programación I");
    }

    @Test
    void crearInscripcionValida() {
        AlumnoInscripto ai = new AlumnoInscripto(alumno, comision);
        assertEquals(alumno, ai.getAlumno());
        assertEquals(comision, ai.getComision());
        assertEquals(LocalDate.now(), ai.getFechaInscripcion());
        assertTrue(ai.getAsistencias().isEmpty());
    }

    @Test
    void alumnoNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new AlumnoInscripto(null, comision));
    }

    @Test
    void comisionNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new AlumnoInscripto(alumno, null));
    }

    @Test
    void registrarAsistencia() {
        AlumnoInscripto ai = new AlumnoInscripto(alumno, comision);
        ai.registrarAsistencia(new Asistencia(LocalDate.of(2026, 6, 15), true));
        assertEquals(1, ai.getAsistencias().size());
    }

    @Test
    void registrarAsistenciaNullLanzaExcepcion() {
        AlumnoInscripto ai = new AlumnoInscripto(alumno, comision);
        assertThrows(IllegalArgumentException.class, () -> ai.registrarAsistencia(null));
    }

    @Test
    void registrarAsistenciaDuplicadaLanzaExcepcion() {
        AlumnoInscripto ai = new AlumnoInscripto(alumno, comision);
        ai.registrarAsistencia(new Asistencia(LocalDate.of(2026, 6, 15), true));
        assertThrows(IllegalArgumentException.class, () ->
                ai.registrarAsistencia(new Asistencia(LocalDate.of(2026, 6, 15), false)));
    }

    @Test
    void calcularPorcentajeAsistenciaSinAsistencias() {
        AlumnoInscripto ai = new AlumnoInscripto(alumno, comision);
        assertEquals(0.0, ai.calcularPorcentajeAsistencia(), 0.001);
    }

    @Test
    void calcularPorcentajeAsistencia() {
        AlumnoInscripto ai = new AlumnoInscripto(alumno, comision);
        ai.registrarAsistencia(new Asistencia(LocalDate.of(2026, 6, 15), true));
        ai.registrarAsistencia(new Asistencia(LocalDate.of(2026, 6, 16), false));
        ai.registrarAsistencia(new Asistencia(LocalDate.of(2026, 6, 17), true));
        assertEquals(200.0 / 3.0, ai.calcularPorcentajeAsistencia(), 0.001);
    }

    @Test
    void esRegular() {
        AlumnoInscripto ai = new AlumnoInscripto(alumno, comision);
        ai.registrarAsistencia(new Asistencia(LocalDate.of(2026, 6, 15), true));
        ai.registrarAsistencia(new Asistencia(LocalDate.of(2026, 6, 16), true));
        ai.registrarAsistencia(new Asistencia(LocalDate.of(2026, 6, 17), false));
        assertTrue(ai.esRegular(66.0));
        assertFalse(ai.esRegular(67.0));
    }
}
