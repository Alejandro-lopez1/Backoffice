package ar.edu.itec.model;

import java.time.LocalDate;

public class Asistencia {

    private LocalDate fecha;
    private boolean presente;

    public Asistencia(LocalDate fecha, boolean presente) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser null.");
        }
        if (fecha.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("No se puede registrar una asistencia con fecha futura: " + fecha);
        }
        this.fecha = fecha;
        this.presente = presente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser null.");
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

    //Dos asistencias se consideran "la misma" si caen en la misma fecha, independientemente del valor de presente. Esto es lo que AlumnoInscripto debería usar antes de agregar una Asistencia a su lista, para no permitir duplicados en el registro en el mismo día.
    public boolean esMismaFecha(Asistencia otra) {
        if (otra == null) return false;
        return this.fecha.equals(otra.fecha);
    }

    @Override
    public String toString(){
        return fecha + " - " + (presente ? "PRESENTE" : "AUSENTE");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Asistencia)) return false;
        Asistencia a = (Asistencia) o;
        return fecha.equals(a.fecha)&& presente == a.presente;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(fecha, presente);
    }

    }
