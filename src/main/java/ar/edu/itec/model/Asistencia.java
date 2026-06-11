package ar.edu.itec.model;

import ar.edu.itec.enums.EstadoAsistencia;

import java.time.LocalDate;

public class Asistencia {

    private Long id;
    private Alumno alumno;
    private LocalDate fecha;
    private EstadoAsistencia estado;

    public Asistencia (Long id, Alumno alumno, LocalDate fecha, EstadoAsistencia estado
    ) { this.id = id;
        this.alumno = alumno;
        this.fecha = fecha;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public EstadoAsistencia getEstado() {
        return estado;
    }

    public void setEstado(EstadoAsistencia estado) {
        this.estado = estado;
    }
}
