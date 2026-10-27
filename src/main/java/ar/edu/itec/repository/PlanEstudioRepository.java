package ar.edu.itec.repository;

import ar.edu.itec.model.Carrera;
import ar.edu.itec.model.Materia;
import ar.edu.itec.model.PlanEstudio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlanEstudioRepository {

    private final List<PlanEstudio> planes;

    public PlanEstudioRepository(CarreraRepository carreraRepository, MateriaRepository materiaRepository) {
        this.planes = new ArrayList<>();
        cargarPlanesIniciales(carreraRepository, materiaRepository);
    }

    private void cargarPlanesIniciales(CarreraRepository carreraRepository, MateriaRepository materiaRepository) {
        // Obtener carreras
        Carrera tsp = carreraRepository.buscarPorCodigo("TSP").orElseThrow();
        Carrera tas = carreraRepository.buscarPorCodigo("TAS").orElseThrow();
        Carrera lds = carreraRepository.buscarPorCodigo("LDS").orElseThrow();

        // Obtener materias
        Materia prog1 = materiaRepository.buscarPorNombre("Programación I").orElseThrow();
        Materia prog2 = materiaRepository.buscarPorNombre("Programación II").orElseThrow();
        Materia bd = materiaRepository.buscarPorNombre("Base de Datos").orElseThrow();

        // TSP - Tecnicatura Superior en Programación
        planes.add(new PlanEstudio(tsp, 1, prog1));  // 1er año
        planes.add(new PlanEstudio(tsp, 2, prog2));  // 2do año
        planes.add(new PlanEstudio(tsp, 2, bd));     // 2do año

        // TAS - Tecnicatura en Análisis de Sistemas
        planes.add(new PlanEstudio(tas, 1, prog1));  // 1er año
        planes.add(new PlanEstudio(tas, 1, bd));     // 1er año
        planes.add(new PlanEstudio(tas, 2, prog2));  // 2do año

        // LDS - Licenciatura en Desarrollo de Software
        planes.add(new PlanEstudio(lds, 1, prog1));  // 1er año
        planes.add(new PlanEstudio(lds, 2, bd));     // 2do año
        planes.add(new PlanEstudio(lds, 3, prog2));  // 3er año
    }

    public List<PlanEstudio> listarPlanes() {
        return new ArrayList<>(planes);
    }

    public List<PlanEstudio> buscarPorCarrera(String codigoCarrera) {
        return planes.stream()
                .filter(p -> p.getCarrera().getCodigo().equalsIgnoreCase(codigoCarrera))
                .collect(Collectors.toList());
    }

    public List<PlanEstudio> buscarPorCarreraYAnio(String codigoCarrera, int anio) {
        return planes.stream()
                .filter(p -> p.getCarrera().getCodigo().equalsIgnoreCase(codigoCarrera) && p.getAnio() == anio)
                .collect(Collectors.toList());
    }

    public void guardar(PlanEstudio plan) {
        planes.add(plan);
    }
}
