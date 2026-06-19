package ar.edu.itec.model;



import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ModuloHorario {

    private int numero;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("HH:mm");

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

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        if (horaFin != null && !horaInicio.isBefore(horaFin)) {
            throw new IllegalArgumentException("horaInicio debe ser anterior a horaFin");
        }
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

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

    @Override
    public String toString() {
        return "Módulo " + numero + " (" + horaInicio.format(FORMATO) + " - " + horaFin.format(FORMATO) + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModuloHorario)) return false;
        ModuloHorario m = (ModuloHorario) o;
        return numero == m.numero && horaInicio.equals(m.horaInicio) && horaFin.equals(m.horaFin);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(numero, horaInicio, horaFin);
    }
}
