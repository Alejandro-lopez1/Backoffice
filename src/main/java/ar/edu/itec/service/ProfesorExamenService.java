package ar.edu.itec.service;

import ar.edu.itec.enums.TipoEvaluacion;
import ar.edu.itec.model.ComisionMateria;
import ar.edu.itec.model.Examen;

import java.time.LocalDate;
import java.util.List;

public interface ProfesorExamenService {

    Examen registrarExamen(ComisionMateria comision, String nombre, String descripcion,
                           LocalDate fecha, TipoEvaluacion tipoEvaluacion);

    List<Examen> listarExamenes();

    List<Examen> buscarExamenes(String criterio);

    Examen obtenerExamenPorId(Long id);

    Examen modificarExamen(Long id, ComisionMateria comision, String nombre, String descripcion,
                           LocalDate fecha, TipoEvaluacion tipoEvaluacion);

    void eliminarExamen(Long id);

    List<Examen> buscarPorComision(ComisionMateria comision);
}
