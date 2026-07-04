package ar.edu.itec.repository;

import ar.edu.itec.model.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComisionRepository {

    private final List<ComisionMateria> comisiones;

    public ComisionRepository(MateriaRepository materiaRepository, ProfesorRepository profesorRepository) {
        this.comisiones = new ArrayList<>();
        cargarComisionesIniciales(materiaRepository, profesorRepository);
    }

    private void cargarComisionesIniciales(MateriaRepository materiaRepository, ProfesorRepository profesorRepository) {
        // Obtener materias y profesores
        Materia prog1 = materiaRepository.buscarPorNombre("Programación I").orElseThrow();
        Materia prog2 = materiaRepository.buscarPorNombre("Programación II").orElseThrow();
        Materia bd = materiaRepository.buscarPorNombre("Base de Datos").orElseThrow();

        Profesor garcia = profesorRepository.buscarPorNombre("García", "Roberto").orElseThrow();
        Profesor fernandez = profesorRepository.buscarPorNombre("Fernández", "Laura").orElseThrow();
        Profesor lopez = profesorRepository.buscarPorNombre("López", "Martín").orElseThrow();

        // Crear cuatrimestres
        Cuatrimestre c2024_1 = new Cuatrimestre(2024, 1, LocalDate.of(2024, 3, 1), LocalDate.of(2024, 7, 15));
        Cuatrimestre c2024_2 = new Cuatrimestre(2024, 2, LocalDate.of(2024, 8, 1), LocalDate.of(2024, 12, 15));

        // Comisión 1: Programación I - Turno Mañana
        ComisionMateria com1A = new ComisionMateria("Comisión-1", prog1, garcia, c2024_1);
        HorarioClase horario1A_lunes = new HorarioClase(DayOfWeek.MONDAY);
        horario1A_lunes.agregarModulo(new ModuloHorario(1, LocalTime.of(8, 0), LocalTime.of(10, 0)));
        HorarioClase horario1A_miercoles = new HorarioClase(DayOfWeek.WEDNESDAY);
        horario1A_miercoles.agregarModulo(new ModuloHorario(1, LocalTime.of(8, 0), LocalTime.of(10, 0)));
        com1A.agregarHorario(horario1A_lunes);
        com1A.agregarHorario(horario1A_miercoles);
        comisiones.add(com1A);

        // Comisión 2: Programación I - Turno Noche
        ComisionMateria com1B = new ComisionMateria("Comisión-2", prog1, fernandez, c2024_1);
        HorarioClase horario1B_martes = new HorarioClase(DayOfWeek.TUESDAY);
        horario1B_martes.agregarModulo(new ModuloHorario(4, LocalTime.of(18, 0), LocalTime.of(20, 0)));
        HorarioClase horario1B_jueves = new HorarioClase(DayOfWeek.THURSDAY);
        horario1B_jueves.agregarModulo(new ModuloHorario(4, LocalTime.of(18, 0), LocalTime.of(20, 0)));
        com1B.agregarHorario(horario1B_martes);
        com1B.agregarHorario(horario1B_jueves);
        comisiones.add(com1B);

        // Comisión 3: Programación II - Turno Mañana
        ComisionMateria com2A = new ComisionMateria("Comisión-3", prog2, lopez, c2024_2);
        HorarioClase horario2A_lunes = new HorarioClase(DayOfWeek.MONDAY);
        horario2A_lunes.agregarModulo(new ModuloHorario(2, LocalTime.of(10, 0), LocalTime.of(12, 0)));
        HorarioClase horario2A_miercoles = new HorarioClase(DayOfWeek.WEDNESDAY);
        horario2A_miercoles.agregarModulo(new ModuloHorario(2, LocalTime.of(10, 0), LocalTime.of(12, 0)));
        com2A.agregarHorario(horario2A_lunes);
        com2A.agregarHorario(horario2A_miercoles);
        comisiones.add(com2A);

        // Comisión 4: Base de Datos - Turno Tarde
        ComisionMateria com3A = new ComisionMateria("Comisión-4", bd, garcia, c2024_2);
        HorarioClase horario3A_martes = new HorarioClase(DayOfWeek.TUESDAY);
        horario3A_martes.agregarModulo(new ModuloHorario(3, LocalTime.of(14, 0), LocalTime.of(16, 0)));
        HorarioClase horario3A_viernes = new HorarioClase(DayOfWeek.FRIDAY);
        horario3A_viernes.agregarModulo(new ModuloHorario(3, LocalTime.of(14, 0), LocalTime.of(16, 0)));
        com3A.agregarHorario(horario3A_martes);
        com3A.agregarHorario(horario3A_viernes);
        comisiones.add(com3A);
    }

    public List<ComisionMateria> listarComisiones() {
        return new ArrayList<>(comisiones);
    }

    public Optional<ComisionMateria> buscarPorCodigo(String codigo) {
        return comisiones.stream()
                .filter(c -> c.getCodigo().equalsIgnoreCase(codigo))
                .findFirst();
    }

    public List<ComisionMateria> buscarPorMateria(String nombreMateria) {
        return comisiones.stream()
                .filter(c -> c.getMateria().getNombre().toLowerCase().contains(nombreMateria.toLowerCase()))
                .toList();
    }

    public List<ComisionMateria> buscarPorProfesor(String apellidoProfesor) {
        return comisiones.stream()
                .filter(c -> c.getProfesor() != null && 
                            c.getProfesor().getApellido().toLowerCase().contains(apellidoProfesor.toLowerCase()))
                .toList();
    }

    public void guardar(ComisionMateria comision) {
        comisiones.add(comision);
    }
}
