package ar.edu.itec.service;

import ar.edu.itec.enums.TipoEvaluacion;
import ar.edu.itec.model.ComisionMateria;
import ar.edu.itec.model.Examen;
import ar.edu.itec.model.Materia;
import ar.edu.itec.repository.ExamenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExamenServiceTest {

    private ExamenService service;
    private ComisionMateria comision;

    @BeforeEach
    void setUp() {
        service = new ExamenService(new ExamenRepository());
        comision = new ComisionMateria("COM-001", new Materia("Programación I"), null, null);
    }

    @Test
    void registrarExamenConDescripcion() {
        Examen examen = service.registrarExamen(
                comision, "Parcial 1", "Unidades 1 a 3",
                LocalDate.of(2026, 11, 15), TipoEvaluacion.PARCIAL);

        assertNotNull(examen.getId());
        assertEquals("Parcial 1", examen.getNombre());
        assertEquals("Unidades 1 a 3", examen.getDescripcion());
        assertEquals(comision, examen.getComision());
    }

    @Test
    void registrarExamenConDescripcionVaciaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                service.registrarExamen(comision, "Parcial 1", "",
                        LocalDate.of(2026, 11, 15), TipoEvaluacion.PARCIAL));
    }

    @Test
    void buscarExamenPorIdYPorNombre() {
        Examen examen = service.registrarExamen(
                comision, "Parcial 1", "Unidades 1 a 3",
                LocalDate.of(2026, 11, 15), TipoEvaluacion.PARCIAL);

        assertEquals(1, service.buscarExamenes(String.valueOf(examen.getId())).size());
        assertEquals(1, service.buscarExamenes("Parcial").size());
        assertTrue(service.buscarExamenes("Final").isEmpty());
    }

    @Test
    void modificarExamen() {
        Examen examen = service.registrarExamen(
                comision, "Parcial 1", "Unidades 1 a 3",
                LocalDate.of(2026, 11, 15), TipoEvaluacion.PARCIAL);

        Examen modificado = service.modificarExamen(
                examen.getId(), comision, "Recuperatorio 1", "Unidades 1 a 4",
                LocalDate.of(2026, 11, 22), TipoEvaluacion.RECUPERATORIO);

        assertEquals("Recuperatorio 1", modificado.getNombre());
        assertEquals(TipoEvaluacion.RECUPERATORIO, modificado.getTipoEvaluacion());
        assertEquals(LocalDate.of(2026, 11, 22), modificado.getFecha());
    }

    @Test
    void eliminarExamen() {
        Examen examen = service.registrarExamen(
                comision, "Parcial 1", "Unidades 1 a 3",
                LocalDate.of(2026, 11, 15), TipoEvaluacion.PARCIAL);

        service.eliminarExamen(examen.getId());

        List<Examen> examenes = service.listarExamenes();
        assertTrue(examenes.isEmpty());
        assertThrows(IllegalArgumentException.class, () -> service.eliminarExamen(examen.getId()));
    }
}
