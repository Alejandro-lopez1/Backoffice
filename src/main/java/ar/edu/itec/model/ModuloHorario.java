package ar.edu.itec.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ModuloHorario {

    private int numero;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("HH:mm");

    // Constructor: valida que las horas no sean nulas y que inicio < fin, luego asigna los valores.
    public ModuloHorario(int numero, LocalTime horaInicio, LocalTime horaFin) {
        if (horaInicio == null || horaFin == null) {
            throw new IllegalArgumentException("horaInicio y horaFin no pueden ser null");
        }
        if (!horaInicio.isBefore(horaFin)) {
            throw new IllegalArgumentException(
                    "horaInicio (" + horaInicio + ") debe ser anterior a horaFin (" + horaFin + ")"
            );
        }
        this.numero = numero;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    // --- getters y setters ---

    // Retorna el número del módulo (ej: 1, 2, 3...).
    public int getNumero() {
        return numero;
    }

    // Asigna el número del módulo.
    public void setNumero(int numero) {
        this.numero = numero;
    }

    // Retorna la hora de inicio del módulo.
    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    // Asigna la hora de inicio validando que sea anterior a horaFin (si ya está definida).
    public void setHoraInicio(LocalTime horaInicio) {
        if (horaFin != null && !horaInicio.isBefore(horaFin)) {
            throw new IllegalArgumentException("horaInicio debe ser anterior a horaFin");
        }
        this.horaInicio = horaInicio;
    }

    // Retorna la hora de fin del módulo.
    public LocalTime getHoraFin() {
        return horaFin;
    }

    // Asigna la hora de fin validando que sea posterior a horaInicio (si ya está definida).
    public void setHoraFin(LocalTime horaFin) {
        if (horaInicio != null && !horaInicio.isBefore(horaFin)) {
            throw new IllegalArgumentException("horaFin debe ser posterior a horaInicio");
        }
        this.horaFin = horaFin;
    }

    // --- lógica de negocio ---

    /**
     * Determina si este módulo se superpone en el tiempo con otro.
     * Dos intervalos [a,b) y [c,d) se solapan si a < d && c < b.
     */
    public boolean seSuperponeCon(ModuloHorario otro) {
        if (otro == null) return false;
        return this.horaInicio.isBefore(otro.horaFin) && otro.horaInicio.isBefore(this.horaFin);
    }

    /**
     * Duración del módulo en minutos. Útil para sumar horas de cursada.
     */
    public long duracionEnMinutos() {
        return java.time.Duration.between(horaInicio, horaFin).toMinutes();
    }

    // Devuelve una representación textual del módulo, ej: "Módulo 1 (08:00 - 09:30)".
    @Override
    public String toString() {
        return "Módulo " + numero + " (" + horaInicio.format(FORMATO) + " - " + horaFin.format(FORMATO) + ")";
    }

    // Dos módulos son iguales si tienen el mismo número y las mismas horas de inicio y fin.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModuloHorario)) return false;
        ModuloHorario m = (ModuloHorario) o;
        return numero == m.numero && horaInicio.equals(m.horaInicio) && horaFin.equals(m.horaFin);
    }

    // Genera el hash code en base a numero, horaInicio y horaFin.
    @Override
    public int hashCode() {
        return java.util.Objects.hash(numero, horaInicio, horaFin);
    }
}
