package ar.edu.itec.model;

import java.util.Objects;

public class PlanEstudio {

    private Carrera carrera;
    private int anio;

    public PlanEstudio(Carrera carrera, int anio) {
        if (carrera == null) {
            throw new IllegalArgumentException("La carrera no puede ser null");
        }
        if (anio <= 0) {
            throw new IllegalArgumentException("El año debe ser positivo");
        }
        this.carrera = carrera;
        this.anio = anio;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public int getAnio() {
        return anio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlanEstudio)) return false;
        PlanEstudio that = (PlanEstudio) o;
        return anio == that.anio && Objects.equals(carrera, that.carrera);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carrera, anio);
    }

    @Override
    public String toString() {
        return "Plan " + anio + " - " + carrera.getNombre();
    }
}
