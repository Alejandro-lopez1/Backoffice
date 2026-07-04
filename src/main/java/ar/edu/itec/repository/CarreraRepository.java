package ar.edu.itec.repository;

import ar.edu.itec.model.Carrera;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarreraRepository {

    private final List<Carrera> carreras;

    public CarreraRepository() {
        this.carreras = new ArrayList<>();
        cargarCarrerasIniciales();
    }

    private void cargarCarrerasIniciales() {
        carreras.add(new Carrera("TSP", "Tecnicatura Superior en Programacion")); //3
        carreras.add(new Carrera("TAS", "Tecnicatura en Analisis de Sistemas")); //5
        carreras.add(new Carrera("LDS", "Licenciatura en Desarrollo de Software")); //6
    }

    public List<Carrera> listarCarreras() {
        return new ArrayList<>(carreras);
    }

    public Optional<Carrera> buscarPorCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            return Optional.empty();
        }
        return carreras.stream()
                .filter(carrera -> carrera.getCodigo().equalsIgnoreCase(codigo.trim()))
                .findFirst();
    }
}
