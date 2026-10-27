package ar.edu.itec.controller;

import ar.edu.itec.model.AlumnoInscripto;
import ar.edu.itec.model.Examen;
import ar.edu.itec.model.Nota;
import ar.edu.itec.service.NotaService;

import java.util.List;

public class NotaController {

    private final NotaService notaService;

    public NotaController(NotaService notaService) {
        this.notaService = notaService;
    }

    public Nota registrarNota(AlumnoInscripto alumnoInscripto, Examen examen, Double valor, String observacion) {
        return notaService.registrarNota(alumnoInscripto, examen, valor, observacion);
    }

    public List<Nota> listarNotas() {
        return notaService.listarNotas();
    }

    public List<Nota> buscarNotas(String criterio) {
        return notaService.buscarNotas(criterio);
    }

    public Nota modificarNota(Long id, Double valor, String observacion) {
        return notaService.modificarNota(id, valor, observacion);
    }

    public void eliminarNota(Long id) {
        notaService.eliminarNota(id);
    }
}
