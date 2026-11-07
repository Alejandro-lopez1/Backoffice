package ar.edu.itec.controller;

import ar.edu.itec.model.Alumno;
import ar.edu.itec.model.AlumnoInscripto;
import ar.edu.itec.model.ComisionMateria;
import ar.edu.itec.service.AlumnoService;
import ar.edu.itec.service.ComisionService;

import java.util.List;

public class AlumnoController {

    private final AlumnoService alumnoService;
    private final ComisionService comisionService;

    public AlumnoController(AlumnoService alumnoService, ComisionService comisionService) {
        this.alumnoService = alumnoService;
        this.comisionService = comisionService;
    }

    public Alumno registrarAlumno(String nombre, String apellido, String dni, String email, String telefono) {
        return alumnoService.registrarAlumno(nombre, apellido, dni, email, telefono);
    }

    public List<Alumno> buscarAlumno(String criterio) {
        return alumnoService.buscarAlumno(criterio);
    }

    public List<Alumno> listarAlumnos() {
        return alumnoService.listarAlumnos();
    }

    public List<ComisionMateria> listarComisionesDisponibles() {
        return comisionService.listarComisiones();
    }

    public AlumnoInscripto inscribirAlumnoComision(String dni, String codigoComision) {
        return alumnoService.inscribirAlumnoComision(dni, codigoComision);
    }

    public List<AlumnoInscripto> obtenerComisionesDeAlumno(String dni) {
        return alumnoService.obtenerComisionesDeAlumno(obtenerAlumno(dni));
    }

    public Alumno modificarAlumno(String dniOriginal, String nombre, String apellido,
                                   String nuevoDni, String email, String telefono) {
        return alumnoService.modificarAlumno(dniOriginal, nombre, apellido, nuevoDni, email, telefono);
    }

    public void eliminarAlumno(String dni) {
        alumnoService.eliminarAlumno(dni);
    }

    public Alumno obtenerAlumno(String dni) {
        return alumnoService.obtenerAlumno(dni);
    }
}
