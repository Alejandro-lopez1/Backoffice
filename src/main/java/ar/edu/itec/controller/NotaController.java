package ar.edu.itec.controller;

import ar.edu.itec.entity.EstadoCursada;
import ar.edu.itec.entity.Nota;
import ar.edu.itec.service.NotaService;

import java.util.List;

public class NotaController {

    private final NotaService notaService;

    public NotaController(NotaService notaService) {
        this.notaService = notaService;
    }

    public void registrarNota(Nota nota) {
        notaService.registrarNota(nota);
    }

    public void registrarRecuperatorio(Nota recuperatorio) {
        notaService.registrarRecuperatorio(recuperatorio);
    }

    public List<Nota> obtenerNotasPorAlumno(Long alumnoId) {
        return notaService.obtenerNotasPorAlumno(alumnoId);
    }

    public Double calcularPromedio(Long alumnoId) {
        return notaService.calcularPromedio(alumnoId);
    }

    public EstadoCursada actualizarEstado(Long alumnoId) {
        return notaService.actualizarEstado(alumnoId);
    }
}
