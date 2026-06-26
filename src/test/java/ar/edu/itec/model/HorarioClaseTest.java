package ar.edu.itec.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.time.LocalTime;

class HorarioClaseTest {

    @Test
    void crearHorarioValido() {
        HorarioClase h = new HorarioClase(DayOfWeek.MONDAY);
        assertEquals(DayOfWeek.MONDAY, h.getDiaSemana());
    }

    @Test
    void diaSemanaNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new HorarioClase(null));
    }

    @Test
    void agregarModulo() {
        HorarioClase h = new HorarioClase(DayOfWeek.MONDAY);
        h.agregarModulo(new ModuloHorario(1, LocalTime.of(18, 0), LocalTime.of(20, 0)));
        assertEquals(1, h.getModulos().size());
    }

    @Test
    void agregarModuloNullLanzaExcepcion() {
        HorarioClase h = new HorarioClase(DayOfWeek.MONDAY);
        assertThrows(IllegalArgumentException.class, () -> h.agregarModulo(null));
    }

    @Test
    void agregarModuloSuperpuestoLanzaExcepcion() {
        HorarioClase h = new HorarioClase(DayOfWeek.MONDAY);
        h.agregarModulo(new ModuloHorario(1, LocalTime.of(18, 0), LocalTime.of(20, 0)));
        assertThrows(IllegalArgumentException.class, () ->
                h.agregarModulo(new ModuloHorario(2, LocalTime.of(19, 0), LocalTime.of(21, 0))));
    }

    @Test
    void chocaConMismoDiaYSuperpuesto() {
        HorarioClase h1 = new HorarioClase(DayOfWeek.MONDAY);
        h1.agregarModulo(new ModuloHorario(1, LocalTime.of(18, 0), LocalTime.of(20, 0)));

        HorarioClase h2 = new HorarioClase(DayOfWeek.MONDAY);
        h2.agregarModulo(new ModuloHorario(1, LocalTime.of(19, 0), LocalTime.of(21, 0)));

        assertTrue(h1.chocaCon(h2));
    }

    @Test
    void noChocaConDistintoDia() {
        HorarioClase h1 = new HorarioClase(DayOfWeek.MONDAY);
        h1.agregarModulo(new ModuloHorario(1, LocalTime.of(18, 0), LocalTime.of(20, 0)));

        HorarioClase h2 = new HorarioClase(DayOfWeek.TUESDAY);
        h2.agregarModulo(new ModuloHorario(1, LocalTime.of(18, 0), LocalTime.of(20, 0)));

        assertFalse(h1.chocaCon(h2));
    }

    @Test
    void noChocaConNull() {
        HorarioClase h = new HorarioClase(DayOfWeek.MONDAY);
        assertFalse(h.chocaCon(null));
    }

    @Test
    void duracionTotalEnMinutos() {
        HorarioClase h = new HorarioClase(DayOfWeek.MONDAY);
        h.agregarModulo(new ModuloHorario(1, LocalTime.of(18, 0), LocalTime.of(20, 0)));
        h.agregarModulo(new ModuloHorario(2, LocalTime.of(20, 15), LocalTime.of(22, 15)));
        assertEquals(240, h.duracionTotalEnMinutos());
    }

    @Test
    void quitarModulo() {
        HorarioClase h = new HorarioClase(DayOfWeek.MONDAY);
        ModuloHorario m = new ModuloHorario(1, LocalTime.of(18, 0), LocalTime.of(20, 0));
        h.agregarModulo(m);
        assertTrue(h.quitarModulo(m));
        assertTrue(h.getModulos().isEmpty());
    }
}
