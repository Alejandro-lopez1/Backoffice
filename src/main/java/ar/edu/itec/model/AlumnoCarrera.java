package ar.edu.itec.model;

import java.time.LocalDate;
import java.util.Objects;

public class AlumnoCarrera {

    public enum Estado {
        ACTIVO,
        EGRESADO,
        BAJA
    }

    private Alumno alumno;
    private Carrera carrera;
    private PlanEstudio planEstudio;
    private LocalDate fechaInscripcion;
    private Estado estado;

    public AlumnoCarrera(Alumno alumno, Carrera carrera, PlanEstudio planEstudio) {
        if (alumno == null) {
            throw new IllegalArgumentException("El alumno no puede ser null");
        }
        if (carrera == null) {
            throw new IllegalArgumentException("La carrera no puede ser null");
        }
        if (planEstudio == null) {
            throw new IllegalArgumentException("El plan de estudio no puede ser null");
        }
        this.alumno = alumno;
        this.carrera = carrera;
        this.planEstudio = planEstudio;
        this.fechaInscripcion = LocalDate.now();
        this.estado = Estado.ACTIVO;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public PlanEstudio getPlanEstudio() {
        return planEstudio;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser null");
        }
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlumnoCarrera)) return false;
        AlumnoCarrera that = (AlumnoCarrera) o;
        return Objects.equals(alumno, that.alumno) && Objects.equals(carrera, that.carrera);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alumno, carrera);
    }

    @Override
    public String toString() {
        return alumno.getNombreCompleto() + " -> " + carrera.getNombre() + " [" + estado + "]";
    }
}
