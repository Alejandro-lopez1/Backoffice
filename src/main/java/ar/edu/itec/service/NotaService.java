package ar.edu.itec.service;

import ar.edu.itec.model.AlumnoInscripto;
import ar.edu.itec.model.Examen;
import ar.edu.itec.model.Nota;
import ar.edu.itec.repository.NotaRepository;

import java.time.LocalDate;
import java.util.List;

public class NotaService {

    private final NotaRepository notaRepository;

    public NotaService(NotaRepository notaRepository) {
        this.notaRepository = notaRepository;
    }

    public Nota registrarNota(AlumnoInscripto alumnoInscripto, Examen examen, Double valor, String observacion) {
        if (valor == null || valor < 1 || valor > 10) {
            throw new IllegalArgumentException("La nota debe ser un valor entre 1 y 10");
        }
        Nota nota = new Nota(examen, valor);
        nota.setObservacion(observacion);
        nota.setFechaRegistro(LocalDate.now());
        nota = notaRepository.guardar(nota);
        alumnoInscripto.agregarNota(nota);
        return nota;
    }

    public List<Nota> listarNotas() {
        return notaRepository.buscarTodos();
    }

    public List<Nota> buscarNotas(String criterio) {
        if (criterio == null || criterio.isBlank()) {
            return listarNotas();
        }
        try {
            Long id = Long.parseLong(criterio.trim());
            return notaRepository.buscarPorId(id).stream().toList();
        } catch (NumberFormatException e) {
            return listarNotas().stream()
                    .filter(n -> n.getExamen() != null
                            && n.getExamen().getNombre().toLowerCase().contains(criterio.toLowerCase()))
                    .toList();
        }
    }

    public Nota obtenerNotaPorId(Long id) {
        return notaRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe una nota con id " + id));
    }

    public Nota modificarNota(Long id, Double valor, String observacion) {
        Nota nota = obtenerNotaPorId(id);
        if (valor != null) {
            if (valor < 1 || valor > 10) {
                throw new IllegalArgumentException("La nota debe ser un valor entre 1 y 10");
            }
            nota.setValor(valor);
        }
        nota.setObservacion(observacion);
        return notaRepository.actualizar(nota);
    }

    public void eliminarNota(Long id) {
        if (!notaRepository.eliminar(id)) {
            throw new IllegalArgumentException("No existe una nota con id " + id);
        }
    }
}
