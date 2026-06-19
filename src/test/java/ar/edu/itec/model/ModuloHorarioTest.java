package ar.edu.itec.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;

class ModuloHorarioTest {

    @Test
    void crearModuloValido() {
        ModuloHorario m = new ModuloHorario(1, LocalTime.of(18, 0), LocalTime.of(20, 0));
        assertEquals(1, m.getNumero());
        assertEquals(LocalTime.of(18, 0), m.getHoraInicio());
        assertEquals(LocalTime.of(20, 0), m.getHoraFin());
    }

    @Test
    void horaInicioNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                new ModuloHorario(1, null, LocalTime.of(20, 0)));
    }

    @Test
    void horaFinNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                new ModuloHorario(1, LocalTime.of(18, 0), null));
    }

    @Test
    void inicioDebeSerAnteriorAFin() {
        assertThrows(IllegalArgumentException.class, () ->
                new ModuloHorario(1, LocalTime.of(20, 0), LocalTime.of(18, 0)));
    }

    @Test
    void inicioIgualAFinLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                new ModuloHorario(1, LocalTime.of(18, 0), LocalTime.of(18, 0)));
    }

    @Test
    void seSuperponeCon() {
        ModuloHorario m1 = new ModuloHorario(1, LocalTime.of(18, 0), LocalTime.of(20, 0));
        ModuloHorario m2 = new ModuloHorario(2, LocalTime.of(19, 0), LocalTime.of(21, 0));
        assertTrue(m1.seSuperponeCon(m2));
        assertTrue(m2.seSuperponeCon(m1));
    }

    @Test
    void noSeSuperponeCon() {
        ModuloHorario m1 = new ModuloHorario(1, LocalTime.of(18, 0), LocalTime.of(19, 0));
        ModuloHorario m2 = new ModuloHorario(2, LocalTime.of(20, 0), LocalTime.of(21, 0));
        assertFalse(m1.seSuperponeCon(m2));
        assertFalse(m2.seSuperponeCon(m1));
    }

    @Test
    void noSeSuperponeConNull() {
        ModuloHorario m = new ModuloHorario(1, LocalTime.of(18, 0), LocalTime.of(20, 0));
        assertFalse(m.seSuperponeCon(null));
    }

    @Test
    void duracionEnMinutos() {
        ModuloHorario m = new ModuloHorario(1, LocalTime.of(18, 0), LocalTime.of(20, 30));
        assertEquals(150, m.duracionEnMinutos());
    }

    @Test
    void setHoraInicioValidaOrden() {
        ModuloHorario m = new ModuloHorario(1, LocalTime.of(18, 0), LocalTime.of(20, 0));
        assertThrows(IllegalArgumentException.class, () ->
                m.setHoraInicio(LocalTime.of(21, 0)));
    }

    @Test
    void setHoraFinValidaOrden() {
        ModuloHorario m = new ModuloHorario(1, LocalTime.of(18, 0), LocalTime.of(20, 0));
        assertThrows(IllegalArgumentException.class, () ->
                m.setHoraFin(LocalTime.of(17, 0)));
    }
}
