package ar.edu.itec.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ComisionMateria {

    private String codigo;
    private Materia materia;
    private Profesor profesor;
    private Cuatrimestre cuatrimestre;
    private List<HorarioClase> horarios;

    public ComisionMateria(String codigo, Materia materia, Profesor profesor, Cuatrimestre cuatrimestre) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código de comisión no puede ser nulo o vacío");
        }
        if (materia == null) {
            throw new IllegalArgumentException("La materia no puede ser null");
        }
        this.codigo = codigo;
        this.materia = materia;
        this.profesor = profesor;
        this.cuatrimestre = cuatrimestre;
        this.horarios = new ArrayList<>();
    }

    // Constructor compatible con código anterior
    @Deprecated
    public ComisionMateria(String codigo, String materiaStr) {
        this(codigo, new Materia(materiaStr), null, null);
    }

    public String getCodigo() {
        return codigo;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        if (materia == null) {
            throw new IllegalArgumentException("La materia no puede ser null");
        }
        this.materia = materia;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Cuatrimestre getCuatrimestre() {
        return cuatrimestre;
    }

    public void setCuatrimestre(Cuatrimestre cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
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
