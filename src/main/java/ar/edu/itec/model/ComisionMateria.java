package ar.edu.itec.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ComisionMateria {

    private String codigo;
    private String materia;
    private List<HorarioClase> horarios;

    public ComisionMateria(String codigo, String materia) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código de comisión no puede ser nulo o vacío");
        }
        if (materia == null || materia.isBlank()) {
            throw new IllegalArgumentException("La materia no puede ser nula o vacía");
        }
        this.codigo = codigo;
        this.materia = materia;
        this.horarios = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getMateria() {
        return materia;
    }

    public List<HorarioClase> getHorarios() {
        return horarios;
    }

    public void agregarHorario(HorarioClase horario) {
        if (horario == null) {
            throw new IllegalArgumentException("El horario no puede ser null");
        }
        for (HorarioClase existente : horarios) {
            if (existente.chocaCon(horario)) {
                throw new IllegalArgumentException(
                        "El horario " + horario + " se superpone con " + existente);
            }
        }
        horarios.add(horario);
    }

    public boolean chocaCon(ComisionMateria otra) {
        if (otra == null) return false;
        for (HorarioClase propio : this.horarios) {
            for (HorarioClase ajeno : otra.horarios) {
                if (propio.chocaCon(ajeno)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComisionMateria)) return false;
        ComisionMateria that = (ComisionMateria) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return materia + " - Comisión " + codigo;
    }
}
