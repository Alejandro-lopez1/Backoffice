package ar.edu.itec.service;

import ar.edu.itec.model.AlumnoInscripto;
import ar.edu.itec.model.Examen;
import ar.edu.itec.model.Nota;

import java.util.List;

public interface AlumnoNotaService {

    Nota registrarNota(AlumnoInscripto alumnoInscripto, Examen examen, Double valor, String observacion);

    List<Nota> listarNotas();

    List<Nota> buscarNotas(String criterio);

    Nota obtenerNotaPorId(Long id);

    Nota modificarNota(Long id, Double valor, String observacion);

    void eliminarNota(Long id);
}
