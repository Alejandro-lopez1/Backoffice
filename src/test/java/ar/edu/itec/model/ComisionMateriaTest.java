package ar.edu.itec.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.time.LocalTime;

class ComisionMateriaTest {

    @Test
    void crearComisionValida() {
        ComisionMateria c = new ComisionMateria("COM-001", "Programación I");
        assertEquals("COM-001", c.getCodigo());
        assertEquals("Programación I", c.getMateria());
    }

    @Test
    void codigoNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new ComisionMateria(null, "Programación I"));
    }

    @Test
    void codigoVacioLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new ComisionMateria("", "Programación I"));
    }

    @Test
    void materiaNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new ComisionMateria("COM-001", null));
    }

    @Test
    void materiaVaciaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new ComisionMateria("COM-001", ""));
    }

    @Test
    void agregarHorario() {
        ComisionMateria c = new ComisionMateria("COM-001", "Programación I");
        HorarioClase h = new HorarioClase(DayOfWeek.MONDAY);
        h.agregarModulo(new ModuloHorario(1, LocalTime.of(18, 0), LocalTime.of(20, 0)));
        c.agregarHorario(h);
        assertEquals(1, c.getHorarios().size());
    }

    @Test
    void agregarHorarioNullLanzaExcepcion() {
        ComisionMateria c = new ComisionMateria("COM-001", "Programación I");
        assertThrows(IllegalArgumentException.class, () -> c.agregarHorario(null));
    }

    @Test
    void chocaConComisionMismoHorario() {
        HorarioClase h = new HorarioClase(DayOfWeek.MONDAY);
        h.agregarModulo(new ModuloHorario(1, LocalTime.of(18, 0), LocalTime.of(20, 0)));

        ComisionMateria c1 = new ComisionMateria("COM-001", "Programación I");
        c1.agregarHorario(h);

        ComisionMateria c2 = new ComisionMateria("COM-002", "Programación I");
        HorarioClase h2 = new HorarioClase(DayOfWeek.MONDAY);
        h2.agregarModulo(new ModuloHorario(1, LocalTime.of(19, 0), LocalTime.of(21, 0)));
        c2.agregarHorario(h2);

        assertTrue(c1.chocaCon(c2));
    }

    @Test
    void noChocaConComisionDistintoHorario() {
        HorarioClase h = new HorarioClase(DayOfWeek.MONDAY);
        h.agregarModulo(new ModuloHorario(1, LocalTime.of(18, 0), LocalTime.of(20, 0)));

        ComisionMateria c1 = new ComisionMateria("COM-001", "Programación I");
        c1.agregarHorario(h);

        ComisionMateria c2 = new ComisionMateria("COM-002", "Matemática");
        HorarioClase h2 = new HorarioClase(DayOfWeek.TUESDAY);
        h2.agregarModulo(new ModuloHorario(1, LocalTime.of(18, 0), LocalTime.of(20, 0)));
        c2.agregarHorario(h2);

        assertFalse(c1.chocaCon(c2));
    }

    @Test
    void noChocaConNull() {
        ComisionMateria c = new ComisionMateria("COM-001", "Programación I");
        assertFalse(c.chocaCon(null));
    }
}
