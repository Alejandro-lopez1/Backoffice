package ar.edu.itec.model;

import java.time.LocalDate;
import java.util.Objects;

public class Cuatrimestre {

    private int anio;
    private int numero;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public Cuatrimestre(int anio, int numero, LocalDate fechaInicio, LocalDate fechaFin) {
        if (anio <= 0) {
            throw new IllegalArgumentException("El año debe ser positivo");
        }
        if (numero < 1 || numero > 2) {
            throw new IllegalArgumentException("El número de cuatrimestre debe ser 1 o 2");
        }
        if (fechaInicio == null) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser null");
        }
        if (fechaFin == null) {
            throw new IllegalArgumentException("La fecha de fin no puede ser null");
        }
        if (!fechaInicio.isBefore(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin");
        }
        this.anio = anio;
        this.numero = numero;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        if (anio <= 0) {
            throw new IllegalArgumentException("El año debe ser positivo");
        }
        this.anio = anio;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        if (numero < 1 || numero > 2) {
            throw new IllegalArgumentException("El número de cuatrimestre debe ser 1 o 2");
        }
        this.numero = numero;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        if (fechaInicio == null) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser null");
        }
        if (fechaFin != null && !fechaInicio.isBefore(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin");
        }
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        if (fechaFin == null) {
            throw new IllegalArgumentException("La fecha de fin no puede ser null");
        }
        if (fechaInicio != null && !fechaInicio.isBefore(fechaFin)) {
            throw new IllegalArgumentException("La fecha de fin debe ser posterior a la fecha de inicio");
        }
        this.fechaFin = fechaFin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cuatrimestre)) return false;
        Cuatrimestre that = (Cuatrimestre) o;
        return anio == that.anio && numero == that.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(anio, numero);
    }

    @Override
    public String toString() {
        return anio + "C" + numero;
    }
}
