package ar.edu.itec.entity;

import java.time.LocalDate;

public class Nota {

    private Long id;
    private Long examenId;
    private Long alumnoId;
    private Double valor;
    private String observacion;
    private LocalDate fechaRegistro;

    public Nota() {
    }

    public Nota(Long id, Long examenId, Long alumnoId, Double valor, String observacion, LocalDate fechaRegistro) {
        this.id = id;
        this.examenId = examenId;
        this.alumnoId = alumnoId;
        this.valor = valor;
        this.observacion = observacion;
        this.fechaRegistro = fechaRegistro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExamenId() {
        return examenId;
    }

    public void setExamenId(Long examenId) {
        this.examenId = examenId;
    }

    public Long getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Long alumnoId) {
        this.alumnoId = alumnoId;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return "Nota{" +
                "id=" + id +
                ", examenId=" + examenId +
                ", alumnoId=" + alumnoId +
                ", valor=" + valor +
                ", observacion='" + observacion + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}
