package ar.edu.itec.model;

import java.time.DayOfWeek;

public class HorarioClase {

    private DayOfWeek dia;
    private ModuloHorario moduloHorario;

    public HorarioClase() {
    }

    public HorarioClase(DayOfWeek dia, ModuloHorario moduloHorario) {
        this.dia = dia;
        this.moduloHorario = moduloHorario;
    }

    public DayOfWeek getDia() {
        return dia;
    }

    public void setDia(DayOfWeek dia) {
        this.dia = dia;
    }

    public ModuloHorario getModuloHorario() {
        return moduloHorario;
    }

    public void setModuloHorario(ModuloHorario moduloHorario) {
        this.moduloHorario = moduloHorario;
    }
}
