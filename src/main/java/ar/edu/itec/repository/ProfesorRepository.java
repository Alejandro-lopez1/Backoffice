package ar.edu.itec.repository;

import ar.edu.itec.model.Profesor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfesorRepository {

    private final List<Profesor> profesores;

    public ProfesorRepository() {
        this.profesores = new ArrayList<>();
        cargarProfesoresIniciales();
    }

    private void cargarProfesoresIniciales() {
        profesores.add(new Profesor("García", "Roberto", "roberto.garcia@itec.edu.ar", "1122334455"));
        profesores.add(new Profesor("Fernández", "Laura", "laura.fernandez@itec.edu.ar", "1133445566"));
        profesores.add(new Profesor("López", "Martín", "martin.lopez@itec.edu.ar", "1144556677"));
        profesores.add(new Profesor("Sánchez", "Patricia", "patricia.sanchez@itec.edu.ar", "1155667788"));
    }

    public List<Profesor> listarProfesores() {
        return new ArrayList<>(profesores);
    }

    public Optional<Profesor> buscarPorNombre(String apellido, String nombre) {
        return profesores.stream()
                .filter(p -> p.getApellido().equalsIgnoreCase(apellido) && 
                            p.getNombre().equalsIgnoreCase(nombre))
                .findFirst();
    }

    public void guardar(Profesor profesor) {
        profesores.add(profesor);
    }
}
