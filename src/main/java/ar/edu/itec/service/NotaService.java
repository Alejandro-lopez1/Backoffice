package ar.edu.itec.service;

import ar.edu.itec.entity.EstadoCursada;
import ar.edu.itec.entity.Nota;
import ar.edu.itec.repository.NotaRepository;

import java.util.List;

public class NotaService {

    private final NotaRepository notaRepository;

    public NotaService(NotaRepository notaRepository) {
        this.notaRepository = notaRepository;
    }

    public void registrarNota(Nota nota) {
        notaRepository.guardar(nota);
    }

    public void registrarRecuperatorio(Nota recuperatorio) {
        notaRepository.guardar(recuperatorio);
    }

    public List<Nota> obtenerNotasPorAlumno(Long alumnoId) {
        return notaRepository.buscarPorAlumnoId(alumnoId);
    }

    public Double calcularPromedio(Long alumnoId) {
        List<Nota> notasDelAlumno = obtenerNotasPorAlumno(alumnoId);

        if (notasDelAlumno.isEmpty()) {
            return 0.0;
        }

        // Promedio simple: suma de valores dividida por la cantidad de notas registradas.
        double sumaNotas = 0.0;
        for (Nota nota : notasDelAlumno) {
            if (nota.getValor() != null) {
                sumaNotas += nota.getValor();
            }
        }

        return sumaNotas / notasDelAlumno.size();
    }

    public EstadoCursada actualizarEstado(Long alumnoId) {
        Double promedio = calcularPromedio(alumnoId);

        if (promedio >= 8) {
            return EstadoCursada.PROMOCIONADO;
        }

        if (promedio >= 6) {
            return EstadoCursada.REGULAR;
        }

        return EstadoCursada.DESAPROBADO;
    }
}
