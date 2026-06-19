package ar.edu.itec.model;

import java.time.LocalDate;

public class Examen {

    private Long id;
    private String nombre;
    private LocalDate fecha;
    private TipoEvaluacion tipoEvaluacion;
    private Long materiaId;

    public Examen() {
    }

    public Examen(Long id, String nombre, LocalDate fecha, TipoEvaluacion tipoEvaluacion, Long materiaId) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.tipoEvaluacion = tipoEvaluacion;
        this.materiaId = materiaId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public TipoEvaluacion getTipoEvaluacion() {
        return tipoEvaluacion;
    }

    public void setTipoEvaluacion(TipoEvaluacion tipoEvaluacion) {
        this.tipoEvaluacion = tipoEvaluacion;
    }

    public Long getMateriaId() {
        return materiaId;
    }

    public void setMateriaId(Long materiaId) {
        this.materiaId = materiaId;
    }

    @Override
    public String toString() {
        return "Examen{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fecha=" + fecha +
                ", tipoEvaluacion=" + tipoEvaluacion +
                ", materiaId=" + materiaId +
                '}';
    }
}
