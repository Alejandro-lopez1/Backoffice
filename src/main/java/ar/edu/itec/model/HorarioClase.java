package ar.edu.itec.model;
import java.util.ArrayList;
import java.time.DayOfWeek;
import java.util.List;

public class HorarioClase {

    private DayOfWeek diaSemana;
    private List<ModuloHorario> modulos;

    public HorarioClase(DayOfWeek diaSemana) {
        if (diaSemana == null) {
            throw new IllegalArgumentException("diaSemana no puede ser null");
        }
        this.diaSemana = diaSemana;
        this.modulos = new ArrayList<>();
    }

    public DayOfWeek getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DayOfWeek diaSemana) {
        if (diaSemana == null) {
            throw new IllegalArgumentException("diaSemana no puede ser null");
        }
        this.diaSemana = diaSemana;
    }

    public List<ModuloHorario> getModulos() {
        return modulos;
    }

    public void setModulos(List<ModuloHorario> modulos) {
        this.modulos = (modulos != null) ? modulos : new ArrayList<>();
    }

    //Agrega un nuevo módulo horario, validando que no se sobreescriba con los módulos ya cargados en este mismo HorarioClase (mismo dia).

    public void agregarModulo(ModuloHorario nuevo) {
        if (nuevo == null) {
            throw new IllegalArgumentException("El módulo no puede ser null");
        }
        for (ModuloHorario existente : modulos) {
            if (existente.seSuperponeCon(nuevo)) {
                throw new IllegalArgumentException(
                        "El módulo " + nuevo + " se superpone con " + existente + " el día " + diaSemana);
            }
        }
        modulos.add(nuevo);
    }

    public boolean quitarModulo(ModuloHorario modulo) {
        return modulos.remove(modulo);
    }

    //Determina si este HorarioClase choca con otro: mismo día y al menos un módulo superpuesto. Esto es lo que ComisionMateria necesita para detectar superposición de horarios entre distintas comisiones a las que un mismo alumno pueda estar inscripto.

    public boolean chocaCon(HorarioClase otro) {
        if (otro == null || this.diaSemana != otro.diaSemana) {
            return false;
        }
        for (ModuloHorario propio : this.modulos) {
            for (ModuloHorario ajeno : otro.modulos) {
                if (propio.seSuperponeCon(ajeno)) {
                    return true;
                }
            }
        }
        return false;
    }

    //Minutos totales de clase que representa este HorarioClase (suma de la duración de todos sus módulos).

    public long duracionTotalEnMinutos() {
        long total = 0;
        for (ModuloHorario m : modulos) {
            total += m.duracionEnMinutos();
        }
        return total;
    }

    @Override
    public String toString() {
        return diaSemana + " " + modulos;
    }
}

