package ar.edu.itec.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class AlumnoTest {

    private static final String TELEFONO = "3764123456";

    @Test
    void crearAlumnoValido() {
        Alumno a = new Alumno("Lucas", "Giorgi", "40123456", "lucas@mail.com", TELEFONO);
        assertEquals("Lucas", a.getNombre());
        assertEquals("Giorgi", a.getApellido());
        assertEquals("40123456", a.getDni());
        assertEquals("lucas@mail.com", a.getEmail());
        assertEquals(TELEFONO, a.getTelefono());
        assertEquals("Lucas Giorgi", a.getNombreCompleto());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void nombreNoPuedeSerNuloOVacio(String nombre) {
        assertThrows(IllegalArgumentException.class, () ->
                new Alumno(nombre, "Giorgi", "40123456", "lucas@mail.com", TELEFONO));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void apellidoNoPuedeSerNuloOVacio(String apellido) {
        assertThrows(IllegalArgumentException.class, () ->
                new Alumno("Lucas", apellido, "40123456", "lucas@mail.com", TELEFONO));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void dniNoPuedeSerNuloOVacio(String dni) {
        assertThrows(IllegalArgumentException.class, () ->
                new Alumno("Lucas", "Giorgi", dni, "lucas@mail.com", TELEFONO));
    }

    @Test
    void emailYTelefonoPuedenSerNulos() {
        Alumno a = new Alumno("Lucas", "Giorgi", "40123456", null, null);
        assertNull(a.getEmail());
        assertNull(a.getTelefono());
    }

    @Test
    void losSettersValidanLosCamposObligatorios() {
        Alumno a = new Alumno("Lucas", "Giorgi", "40123456", "lucas@mail.com", TELEFONO);
        assertThrows(IllegalArgumentException.class, () -> a.setNombre(" "));
        assertThrows(IllegalArgumentException.class, () -> a.setApellido(null));
        assertThrows(IllegalArgumentException.class, () -> a.setDni(""));
    }

    @Test
    void equalsPorDni() {
        Alumno a1 = new Alumno("Lucas", "Giorgi", "40123456", null, TELEFONO);
        Alumno a2 = new Alumno("Lucas", "Giorgi", "40123456", "otro@mail.com", "3764000000");
        assertEquals(a1, a2);
        assertEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    void distintosPorDni() {
        Alumno a1 = new Alumno("Lucas", "Giorgi", "40123456", null, TELEFONO);
        Alumno a2 = new Alumno("Axel", "Almada", "41234567", null, "3764000000");
        assertNotEquals(a1, a2);
    }
}
