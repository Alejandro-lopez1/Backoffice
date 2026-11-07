package ar.edu.itec.service;

import ar.edu.itec.enums.TipoEvaluacion;
import ar.edu.itec.model.Alumno;
import ar.edu.itec.model.AlumnoInscripto;
import ar.edu.itec.model.ComisionMateria;
import ar.edu.itec.model.Examen;
import ar.edu.itec.model.Materia;
import ar.edu.itec.model.Nota;
import ar.edu.itec.repository.NotaRepository;
import ar.edu.itec.service.impl.AlumnoNotaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotaServiceTest {

    private AlumnoNotaServiceImpl service;
    private AlumnoInscripto inscripto;
    private Examen examen;

    @BeforeEach
    void setUp() {
        service = new AlumnoNotaServiceImpl(new NotaRepository());
        Alumno alumno = new Alumno("Zoe", "Prueba", "50000001", "zoe@mail.com", "3764000001");
        ComisionMateria comision = new ComisionMateria(
                "COM-001", new Materia("Programación I"), null, null);
        inscripto = new AlumnoInscripto(alumno, comision);
        examen = new Examen(comision, "Parcial 1", "Unidades 1 a 3",
                LocalDate.of(2026, 11, 15), TipoEvaluacion.PARCIAL);
    }

    @Test
    void registrarNotaValida() {
        Nota nota = service.registrarNota(inscripto, examen, 8.0, "Aprobado");

        assertNotNull(nota.getId());
        assertEquals(8.0, nota.getValor());
        assertEquals("Aprobado", nota.getObservacion());
        assertEquals(LocalDate.now(), nota.getFechaRegistro());
        assertEquals(1, inscripto.getNotas().size());
        assertEquals(1, service.listarNotas().size());
    }

    @Test
    void registrarNotaFueraDeRangoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                service.registrarNota(inscripto, examen, 0.0, null));
        assertThrows(IllegalArgumentException.class, () ->
                service.registrarNota(inscripto, examen, 11.0, null));
        assertTrue(inscripto.getNotas().isEmpty());
    }

    @Test
    void modificarNotaConValorNuloConservaElValor() {
        Nota nota = service.registrarNota(inscripto, examen, 6.0, "Original");

        Nota modificada = service.modificarNota(nota.getId(), null, "Observación nueva");

        assertEquals(6.0, modificada.getValor());
        assertEquals("Observación nueva", modificada.getObservacion());
    }

    @Test
    void buscarNotaPorIdYPorNombreDeExamen() {
        Nota nota = service.registrarNota(inscripto, examen, 8.0, null);

        assertEquals(1, service.buscarNotas(String.valueOf(nota.getId())).size());
        assertEquals(1, service.buscarNotas("Parcial").size());
        assertTrue(service.buscarNotas("Final").isEmpty());
    }

    @Test
    void eliminarNota() {
        Nota nota = service.registrarNota(inscripto, examen, 8.0, null);

        service.eliminarNota(nota.getId());

        List<Nota> notas = service.listarNotas();
        assertTrue(notas.isEmpty());
        assertThrows(IllegalArgumentException.class, () -> service.eliminarNota(nota.getId()));
    }
}
