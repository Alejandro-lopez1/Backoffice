package ar.edu.itec.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CarreraTest {

    @Test
    void crearCarreraValida() {
        Carrera c = new Carrera("TSD", "Tecnicatura en Desarrollo de Software");
        assertEquals("TSD", c.getCodigo());
        assertEquals("Tecnicatura en Desarrollo de Software", c.getNombre());
    }

    @Test
    void codigoNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Carrera(null, "Nombre"));
    }

    @Test
    void codigoVacioLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Carrera("", "Nombre"));
    }

    @Test
    void nombreNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Carrera("COD", null));
    }

    @Test
    void nombreVacioLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Carrera("COD", ""));
    }

    @Test
    void equalsPorCodigo() {
        Carrera c1 = new Carrera("TSD", "Tecnicatura en Desarrollo de Software");
        Carrera c2 = new Carrera("TSD", "Otro nombre");
        assertEquals(c1, c2);
    }
}
