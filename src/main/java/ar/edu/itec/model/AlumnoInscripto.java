package ar.edu.itec.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AlumnoInscripto {

    private Alumno alumno;
    private ComisionMateria comision;
    private LocalDate fechaInscripcion;
    private List<Asistencia> asistencias;
    private List<Nota> notas;

    public AlumnoInscripto(Alumno alumno, ComisionMateria comision) {
        if (alumno == null) {
            throw new IllegalArgumentException("El alumno no puede ser null");
        }
        if (comision == null) {
            throw new IllegalArgumentException("La comisión no puede ser null");
        }
        this.alumno = alumno;
        this.comision = comision;
        this.fechaInscripcion = LocalDate.now();
        this.asistencias = new ArrayList<>();
        this.notas = new ArrayList<>();
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public ComisionMateria getComision() {
        return comision;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public List<Asistencia> getAsistencias() {
        return asistencias;
    }

    public void registrarAsistencia(Asistencia asistencia) {
        if (asistencia == null) {
            throw new IllegalArgumentException("La asistencia no puede ser null");
        }
        for (Asistencia existente : asistencias) {
            if (existente.esMismaFecha(asistencia)) {
                throw new IllegalArgumentException(
                        "Ya existe un registro de asistencia para la fecha " + asistencia.getFecha());
            }
        }
        asistencias.add(asistencia);
    }

    public double calcularPorcentajeAsistencia() {
        if (asistencias.isEmpty()) return 0.0;
        long presentes = asistencias.stream().filter(Asistencia::isPresente).count();
        return (double) presentes / asistencias.size() * 100;
    }

    public boolean esRegular(double porcentajeMinimo) {
        return calcularPorcentajeAsistencia() >= porcentajeMinimo;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void agregarNota(Nota nota) {
        if (nota == null) {
            throw new IllegalArgumentException("La nota no puede ser null");
        }
        notas.add(nota);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlumnoInscripto)) return false;
        AlumnoInscripto that = (AlumnoInscripto) o;
        return Objects.equals(alumno, that.alumno) && Objects.equals(comision, that.comision);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alumno, comision);
    }

    @Override
    public String toString() {
        return alumno.getNombreCompleto() + " inscripto en " + comision;
    }
}
