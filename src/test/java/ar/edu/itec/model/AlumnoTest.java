package ar.edu.itec.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class AlumnoTest {

    @Test
    void crearAlumnoValido() {
        Alumno a = new Alumno("Lucas", "Giorgi", "40123456", "lucas@mail.com", "LEG-001");
        assertEquals("Lucas", a.getNombre());
        assertEquals("Giorgi", a.getApellido());
        assertEquals("40123456", a.getDocumento());
        assertEquals("lucas@mail.com", a.getEmail());
        assertEquals("LEG-001", a.getLegajo());
        assertEquals("Lucas Giorgi", a.getNombreCompleto());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void nombreNoPuedeSerNuloOVacio(String nombre) {
        assertThrows(IllegalArgumentException.class, () ->
                new Alumno(nombre, "Giorgi", "40123456", "lucas@mail.com", "LEG-001"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void apellidoNoPuedeSerNuloOVacio(String apellido) {
        assertThrows(IllegalArgumentException.class, () ->
                new Alumno("Lucas", apellido, "40123456", "lucas@mail.com", "LEG-001"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void documentoNoPuedeSerNuloOVacio(String documento) {
        assertThrows(IllegalArgumentException.class, () ->
                new Alumno("Lucas", "Giorgi", documento, "lucas@mail.com", "LEG-001"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void legajoNoPuedeSerNuloOVacio(String legajo) {
        assertThrows(IllegalArgumentException.class, () ->
                new Alumno("Lucas", "Giorgi", "40123456", "lucas@mail.com", legajo));
    }

    @Test
    void emailPuedeSerNulo() {
        Alumno a = new Alumno("Lucas", "Giorgi", "40123456", null, "LEG-001");
        assertNull(a.getEmail());
    }

    @Test
    void equalsPorDocumento() {
        Alumno a1 = new Alumno("Lucas", "Giorgi", "40123456", null, "LEG-001");
        Alumno a2 = new Alumno("Lucas", "Giorgi", "40123456", null, "LEG-002");
        assertEquals(a1, a2);
        assertEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    void distintosPorDocumento() {
        Alumno a1 = new Alumno("Lucas", "Giorgi", "40123456", null, "LEG-001");
        Alumno a2 = new Alumno("Axel", "Almada", "41234567", null, "LEG-002");
        assertNotEquals(a1, a2);
    }
}
