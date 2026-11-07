package ar.edu.itec.service;

import ar.edu.itec.model.AsistenciaAlumno;
import ar.edu.itec.repository.AsistenciaRepository;
import ar.edu.itec.service.impl.AlumnoAsistenciaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AlumnoAsistenciaServiceTest {

    private AlumnoAsistenciaServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new AlumnoAsistenciaServiceImpl(new AsistenciaRepository());
    }

    @Test
    void registrarAsistenciaValida() {
        AsistenciaAlumno asistencia = service.registrarAsistencia("40123456", true);

        assertNotNull(asistencia.getId());
        assertEquals("40123456", asistencia.getDocumentoAlumno());
        assertEquals(LocalDate.now(), asistencia.getFecha());
        assertTrue(asistencia.isPresente());
        assertEquals(1, service.listarAsistencias().size());
    }

    @Test
    void registrarAsistenciaDuplicadaLanzaExcepcion() {
        service.registrarAsistencia("40123456", true);

        assertThrows(IllegalArgumentException.class, () -> service.registrarAsistencia("40123456", false));
        assertEquals(1, service.listarAsistencias().size());
    }

    @Test
    void registrarAsistenciaConDocumentoVacioLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> service.registrarAsistencia(" ", true));
        assertThrows(IllegalArgumentException.class, () -> service.registrarAsistencia(null, true));
    }

    @Test
    void buscarAsistenciasPorDocumentoYPorId() {
        AsistenciaAlumno primera = service.registrarAsistencia("DOC-40123456", true);
        service.registrarAsistencia("DOC-41123456", false);

        assertEquals(1, service.buscarAsistencias("DOC-40123456").size());
        assertTrue(service.buscarAsistencias("DOC-99999999").isEmpty());
        assertEquals(primera, service.buscarAsistencias(String.valueOf(primera.getId())).get(0));
    }

    @Test
    void obtenerAsistenciaPorIdExistenteEInexistente() {
        AsistenciaAlumno asistencia = service.registrarAsistencia("40123456", true);

        assertEquals(asistencia, service.obtenerAsistenciaPorId(asistencia.getId()));
        assertThrows(IllegalArgumentException.class, () -> service.obtenerAsistenciaPorId(999L));
    }

    @Test
    void modificarAsistenciaActualizaLosDatos() {
        AsistenciaAlumno asistencia = service.registrarAsistencia("40123456", true);

        AsistenciaAlumno modificada = service.modificarAsistencia(
                asistencia.getId(), "41123456", LocalDate.now().minusDays(1), false);

        assertEquals("41123456", modificada.getDocumentoAlumno());
        assertEquals(LocalDate.now().minusDays(1), modificada.getFecha());
        assertFalse(modificada.isPresente());
    }

    @Test
    void modificarAsistenciaConDocumentoVacioOFechaNulaLanzaExcepcion() {
        AsistenciaAlumno asistencia = service.registrarAsistencia("40123456", true);

        assertThrows(IllegalArgumentException.class, () ->
                service.modificarAsistencia(asistencia.getId(), " ", LocalDate.now(), true));
        assertThrows(IllegalArgumentException.class, () ->
                service.modificarAsistencia(asistencia.getId(), "40123456", null, true));
    }

    @Test
    void modificarAsistenciaQueDuplicaOtraLanzaExcepcion() {
        service.registrarAsistencia("40123456", true);
        AsistenciaAlumno otra = service.registrarAsistencia("41123456", false);

        assertThrows(IllegalArgumentException.class, () ->
                service.modificarAsistencia(otra.getId(), "40123456", LocalDate.now(), false));
    }

    @Test
    void eliminarAsistenciaExistenteEInexistente() {
        AsistenciaAlumno asistencia = service.registrarAsistencia("40123456", true);

        service.eliminarAsistencia(asistencia.getId());

        assertTrue(service.listarAsistencias().isEmpty());
        assertThrows(IllegalArgumentException.class, () -> service.eliminarAsistencia(asistencia.getId()));
    }
}
