package ar.edu.itec.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

class AlumnoCarreraTest {

    @Test
    void crearInscripcionValida() {
        Alumno a = new Alumno("Lucas", "Giorgi", "40123456", null, "LEG-001");
        Carrera c = new Carrera("TSD", "Tecnicatura en Desarrollo de Software");
        PlanEstudio p = new PlanEstudio(c, 2024);
        AlumnoCarrera ac = new AlumnoCarrera(a, c, p);

        assertEquals(a, ac.getAlumno());
        assertEquals(c, ac.getCarrera());
        assertEquals(p, ac.getPlanEstudio());
        assertEquals(LocalDate.now(), ac.getFechaInscripcion());
        assertEquals(AlumnoCarrera.Estado.ACTIVO, ac.getEstado());
    }

    @Test
    void alumnoNullLanzaExcepcion() {
        Carrera c = new Carrera("TSD", "Tecnicatura en Desarrollo de Software");
        PlanEstudio p = new PlanEstudio(c, 2024);
        assertThrows(IllegalArgumentException.class, () -> new AlumnoCarrera(null, c, p));
    }

    @Test
    void carreraNullLanzaExcepcion() {
        Alumno a = new Alumno("Lucas", "Giorgi", "40123456", null, "LEG-001");
        PlanEstudio p = new PlanEstudio(new Carrera("TSD", "Tecnicatura"), 2024);
        assertThrows(IllegalArgumentException.class, () -> new AlumnoCarrera(a, null, p));
    }

    @Test
    void planEstudioNullLanzaExcepcion() {
        Alumno a = new Alumno("Lucas", "Giorgi", "40123456", null, "LEG-001");
        Carrera c = new Carrera("TSD", "Tecnicatura en Desarrollo de Software");
        assertThrows(IllegalArgumentException.class, () -> new AlumnoCarrera(a, c, null));
    }

    @Test
    void setEstadoNullLanzaExcepcion() {
        Alumno a = new Alumno("Lucas", "Giorgi", "40123456", null, "LEG-001");
        Carrera c = new Carrera("TSD", "Tecnicatura en Desarrollo de Software");
        PlanEstudio p = new PlanEstudio(c, 2024);
        AlumnoCarrera ac = new AlumnoCarrera(a, c, p);
        assertThrows(IllegalArgumentException.class, () -> ac.setEstado(null));
    }

    @Test
    void cambiarEstado() {
        Alumno a = new Alumno("Lucas", "Giorgi", "40123456", null, "LEG-001");
        Carrera c = new Carrera("TSD", "Tecnicatura en Desarrollo de Software");
        PlanEstudio p = new PlanEstudio(c, 2024);
        AlumnoCarrera ac = new AlumnoCarrera(a, c, p);
        ac.setEstado(AlumnoCarrera.Estado.EGRESADO);
        assertEquals(AlumnoCarrera.Estado.EGRESADO, ac.getEstado());
    }
}
