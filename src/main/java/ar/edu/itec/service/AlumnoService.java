package ar.edu.itec.service;

import ar.edu.itec.model.Alumno;
import ar.edu.itec.model.AlumnoInscripto;
import ar.edu.itec.model.ComisionMateria;

import java.util.List;

public interface AlumnoService {

    Alumno registrarAlumno(String nombre, String apellido, String dni, String email, String telefono);

    List<Alumno> buscarAlumno(String criterio);

    List<Alumno> listarAlumnos();

    Alumno obtenerAlumno(String dni);

    Alumno modificarAlumno(String dniOriginal, String nombre, String apellido,
                           String nuevoDni, String email, String telefono);

    void eliminarAlumno(String dni);

    AlumnoInscripto inscribirAlumnoComision(Alumno alumno, ComisionMateria comision);

    AlumnoInscripto inscribirAlumnoComision(String dni, String codigoComision);

    List<AlumnoInscripto> obtenerComisionesDeAlumno(Alumno alumno);

    List<AlumnoInscripto> buscarInscripcionesPorComision(ComisionMateria comision);
}
