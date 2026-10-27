package ar.edu.itec.model;

import java.time.LocalDate;

public class Nota {

    private Long id;
    private Examen examen;
    private Double valor;
    private String observacion;
    private LocalDate fechaRegistro;

    public Nota() {
    }

    public Nota(Long id, Examen examen, Double valor, String observacion, LocalDate fechaRegistro) {
        this.id = id;
        this.examen = examen;
        this.valor = valor;
        this.observacion = observacion;
        this.fechaRegistro = fechaRegistro;
    }

    public Nota(Examen examen, Double valor) {
        this(null, examen, valor, null, LocalDate.now());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Examen getExamen() {
        return examen;
    }

    public void setExamen(Examen examen) {
        this.examen = examen;
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
                ", examen=" + (examen != null ? examen.getNombre() : "null") +
                ", valor=" + valor +
                ", observacion='" + observacion + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}
