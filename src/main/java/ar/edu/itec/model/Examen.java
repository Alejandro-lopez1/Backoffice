package ar.edu.itec.model;

import ar.edu.itec.enums.TipoEvaluacion;

import java.time.LocalDate;

public class Examen {

    private Long id;
    private Carrera carrera;
    private String nombre;
    private String descripcion;
    private LocalDate fecha;
    private TipoEvaluacion tipoEvaluacion;

    public Examen() {
    }

    public Examen(Long id, Carrera carrera, String nombre, String descripcion, LocalDate fecha, TipoEvaluacion tipoEvaluacion) {
        setId(id);
        setCarrera(carrera);
        setNombre(nombre);
        setDescripcion(descripcion);
        setFecha(fecha);
        setTipoEvaluacion(tipoEvaluacion);
    }

    public Examen(Carrera carrera, String nombre, String descripcion, LocalDate fecha, TipoEvaluacion tipoEvaluacion) {
        this(null, carrera, nombre, descripcion, fecha, tipoEvaluacion);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        if (carrera == null) {
            throw new IllegalArgumentException("La carrera no puede ser null");
        }
        this.carrera = carrera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede ser nula o vacía");
        }
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser null");
        }
        this.fecha = fecha;
    }

    public TipoEvaluacion getTipoEvaluacion() {
        return tipoEvaluacion;
    }

    public void setTipoEvaluacion(TipoEvaluacion tipoEvaluacion) {
        if (tipoEvaluacion == null) {
            throw new IllegalArgumentException("El tipo de examen no puede ser null");
        }
        this.tipoEvaluacion = tipoEvaluacion;
    }

    @Override
    public String toString() {
        return "Examen{" +
                "id=" + id +
                ", carrera=" + carrera +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fecha=" + fecha +
                ", tipoEvaluacion=" + tipoEvaluacion +
                '}';
    }
}
