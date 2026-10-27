package ar.edu.itec.model;

import java.time.LocalDate;
import java.util.Objects;

public class AsistenciaAlumno {

    private Long id;
    private String documentoAlumno;
    private LocalDate fecha;
    private boolean presente;

    public AsistenciaAlumno() {
    }

    public AsistenciaAlumno(Long id, String documentoAlumno, LocalDate fecha, boolean presente) {
        setId(id);
        setDocumentoAlumno(documentoAlumno);
        setFecha(fecha);
        setPresente(presente);
    }

    public AsistenciaAlumno(String documentoAlumno, LocalDate fecha, boolean presente) {
        this(null, documentoAlumno, fecha, presente);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentoAlumno() {
        return documentoAlumno;
    }

    public void setDocumentoAlumno(String documentoAlumno) {
        if (documentoAlumno == null || documentoAlumno.trim().isEmpty()) {
            throw new IllegalArgumentException("El documento del alumno no puede ser nulo o vacío");
        }
        this.documentoAlumno = documentoAlumno.trim();
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser null");
        }
        if (fecha.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("No se puede registrar una asistencia con fecha futura: " + fecha);
        }
        this.fecha = fecha;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }

    @Override
    public String toString() {
        return "AsistenciaAlumno{" +
                "id=" + id +
                ", documentoAlumno='" + documentoAlumno + '\'' +
                ", fecha=" + fecha +
                ", presente=" + presente +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AsistenciaAlumno)) return false;
        AsistenciaAlumno that = (AsistenciaAlumno) o;
        return presente == that.presente
                && Objects.equals(id, that.id)
                && Objects.equals(documentoAlumno, that.documentoAlumno)
                && Objects.equals(fecha, that.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, documentoAlumno, fecha, presente);
    }
}
