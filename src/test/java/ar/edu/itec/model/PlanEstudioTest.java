package ar.edu.itec.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PlanEstudioTest {

    @Test
    void crearPlanValido() {
        Carrera c = new Carrera("TSD", "Tecnicatura en Desarrollo de Software");
        PlanEstudio p = new PlanEstudio(c, 2024);
        assertEquals(c, p.getCarrera());
        assertEquals(2024, p.getAnio());
    }

    @Test
    void carreraNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new PlanEstudio(null, 2024));
    }

    @Test
    void anioNegativoLanzaExcepcion() {
        Carrera c = new Carrera("TSD", "Tecnicatura en Desarrollo de Software");
        assertThrows(IllegalArgumentException.class, () -> new PlanEstudio(c, -1));
    }

    @Test
    void anioCeroLanzaExcepcion() {
        Carrera c = new Carrera("TSD", "Tecnicatura en Desarrollo de Software");
        assertThrows(IllegalArgumentException.class, () -> new PlanEstudio(c, 0));
    }
}
