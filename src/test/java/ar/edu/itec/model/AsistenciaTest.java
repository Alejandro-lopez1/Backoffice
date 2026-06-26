package ar.edu.itec.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

class AsistenciaTest {

    @Test
    void crearAsistenciaPresente() {
        Asistencia a = new Asistencia(LocalDate.of(2026, 6, 15), true);
        assertEquals(LocalDate.of(2026, 6, 15), a.getFecha());
        assertTrue(a.isPresente());
    }

    @Test
    void crearAsistenciaAusente() {
        Asistencia a = new Asistencia(LocalDate.of(2026, 6, 15), false);
        assertFalse(a.isPresente());
    }

    @Test
    void fechaNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Asistencia(null, true));
    }

    @Test
    void fechaFuturaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                new Asistencia(LocalDate.now().plusDays(1), true));
    }

    @Test
    void esMismaFecha() {
        Asistencia a1 = new Asistencia(LocalDate.of(2026, 6, 15), true);
        Asistencia a2 = new Asistencia(LocalDate.of(2026, 6, 15), false);
        assertTrue(a1.esMismaFecha(a2));
    }

    @Test
    void noEsMismaFecha() {
        Asistencia a1 = new Asistencia(LocalDate.of(2026, 6, 15), true);
        Asistencia a2 = new Asistencia(LocalDate.of(2026, 6, 16), true);
        assertFalse(a1.esMismaFecha(a2));
    }

    @Test
    void noEsMismaFechaConNull() {
        Asistencia a = new Asistencia(LocalDate.of(2026, 6, 15), true);
        assertFalse(a.esMismaFecha(null));
    }

    @Test
    void setFechaNullLanzaExcepcion() {
        Asistencia a = new Asistencia(LocalDate.of(2026, 6, 15), true);
        assertThrows(IllegalArgumentException.class, () -> a.setFecha(null));
    }

    @Test
    void setFechaFuturaLanzaExcepcion() {
        Asistencia a = new Asistencia(LocalDate.of(2026, 6, 15), true);
        assertThrows(IllegalArgumentException.class, () ->
                a.setFecha(LocalDate.now().plusDays(1)));
    }
}
