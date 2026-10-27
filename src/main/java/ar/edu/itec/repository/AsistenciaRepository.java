package ar.edu.itec.repository;

import ar.edu.itec.model.AsistenciaAlumno;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AsistenciaRepository {

    private final List<AsistenciaAlumno> asistencias;
    private long siguienteId;

    public AsistenciaRepository() {
        this.asistencias = new ArrayList<>();
        this.siguienteId = 1L;
    }

    public AsistenciaAlumno guardar(AsistenciaAlumno asistencia) {
        if (asistencia == null) {
            throw new IllegalArgumentException("La asistencia no puede ser null");
        }
        if (asistencia.getId() == null) {
            asistencia.setId(siguienteId++);
        } else {
            siguienteId = Math.max(siguienteId, asistencia.getId() + 1);
        }
        asistencias.add(asistencia);
        return asistencia;
    }

    public List<AsistenciaAlumno> buscarTodos() {
        return new ArrayList<>(asistencias);
    }

    public Optional<AsistenciaAlumno> buscarPorId(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return asistencias.stream()
                .filter(asistencia -> id.equals(asistencia.getId()))
                .findFirst();
    }

    public List<AsistenciaAlumno> buscarPorDocumento(String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            return buscarTodos();
        }
        String normalizado = documento.trim();
        return asistencias.stream()
                .filter(asistencia -> asistencia.getDocumentoAlumno().equalsIgnoreCase(normalizado))
                .toList();
    }

    public List<AsistenciaAlumno> buscarPorCriterio(String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            return buscarTodos();
        }
        String normalizado = criterio.trim();
        try {
            Long id = Long.parseLong(normalizado);
            return buscarPorId(id).map(List::of).orElseGet(List::of);
        } catch (NumberFormatException e) {
            return buscarPorDocumento(normalizado);
        }
    }

    public AsistenciaAlumno actualizar(AsistenciaAlumno asistencia) {
        if (asistencia == null || asistencia.getId() == null) {
            throw new IllegalArgumentException("La asistencia a actualizar debe tener un id");
        }
        for (int i = 0; i < asistencias.size(); i++) {
            if (asistencias.get(i).getId().equals(asistencia.getId())) {
                asistencias.set(i, asistencia);
                return asistencia;
            }
        }
        throw new IllegalArgumentException("No existe una asistencia con id " + asistencia.getId());
    }

    public boolean eliminar(Long id) {
        return asistencias.removeIf(asistencia -> asistencia.getId().equals(id));
    }
}
