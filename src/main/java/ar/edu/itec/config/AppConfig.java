package ar.edu.itec.config;

import ar.edu.itec.controller.AlumnoController;
import ar.edu.itec.controller.AsistenciaController;
import ar.edu.itec.controller.ConsultasController;
import ar.edu.itec.controller.ExamenController;
import ar.edu.itec.controller.NotaController;
import ar.edu.itec.repository.AlumnoRepository;
import ar.edu.itec.repository.AsistenciaRepository;
import ar.edu.itec.repository.CarreraRepository;
import ar.edu.itec.repository.ComisionRepository;
import ar.edu.itec.repository.ExamenRepository;
import ar.edu.itec.repository.MateriaRepository;
import ar.edu.itec.repository.NotaRepository;
import ar.edu.itec.repository.PlanEstudioRepository;
import ar.edu.itec.repository.ProfesorRepository;
import ar.edu.itec.service.impl.AlumnoAsistenciaServiceImpl;
import ar.edu.itec.service.impl.AlumnoNotaServiceImpl;
import ar.edu.itec.service.impl.AlumnoServiceImpl;
import ar.edu.itec.service.impl.CarreraServiceImpl;
import ar.edu.itec.service.impl.ComisionServiceImpl;
import ar.edu.itec.service.impl.MateriaServiceImpl;
import ar.edu.itec.service.impl.PlanEstudioServiceImpl;
import ar.edu.itec.service.impl.ProfesorConsultaServiceImpl;
import ar.edu.itec.service.impl.ProfesorExamenServiceImpl;
import ar.edu.itec.view.AlumnoView;
import ar.edu.itec.view.AsistenciaView;
import ar.edu.itec.view.ConsultasView;
import ar.edu.itec.view.ExamenView;
import ar.edu.itec.view.MenuView;
import ar.edu.itec.view.NotaView;

import java.util.Scanner;

/**
 * Única raíz de composición: conoce repositorios, services, controllers y vistas.
 * El resto de las capas solo depende de la capa inmediatamente inferior.
 */
public class AppConfig {

    public MenuView construirMenuView() {
        Scanner scanner = new Scanner(System.in);

        // Repositorios: una sola instancia de cada uno
        CarreraRepository carreraRepository = new CarreraRepository();
        MateriaRepository materiaRepository = new MateriaRepository();
        ProfesorRepository profesorRepository = new ProfesorRepository();
        PlanEstudioRepository planEstudioRepository = new PlanEstudioRepository(carreraRepository, materiaRepository);
        ComisionRepository comisionRepository = new ComisionRepository(materiaRepository, profesorRepository);
        AlumnoRepository alumnoRepository = new AlumnoRepository();
        ExamenRepository examenRepository = new ExamenRepository();
        AsistenciaRepository asistenciaRepository = new AsistenciaRepository();
        NotaRepository notaRepository = new NotaRepository();

        // Servicios
        CarreraServiceImpl carreraService = new CarreraServiceImpl(carreraRepository);
        MateriaServiceImpl materiaService = new MateriaServiceImpl(materiaRepository);
        ProfesorConsultaServiceImpl profesorConsultaService = new ProfesorConsultaServiceImpl(profesorRepository);
        PlanEstudioServiceImpl planEstudioService = new PlanEstudioServiceImpl(planEstudioRepository);
        ComisionServiceImpl comisionService = new ComisionServiceImpl(comisionRepository);
        AlumnoServiceImpl alumnoService = new AlumnoServiceImpl(alumnoRepository, comisionRepository);
        ProfesorExamenServiceImpl examenService = new ProfesorExamenServiceImpl(examenRepository);
        AlumnoAsistenciaServiceImpl asistenciaService = new AlumnoAsistenciaServiceImpl(asistenciaRepository);
        AlumnoNotaServiceImpl notaService = new AlumnoNotaServiceImpl(notaRepository);

        // Controladores
        AlumnoController alumnoController = new AlumnoController(alumnoService, comisionService);
        ExamenController examenController = new ExamenController(examenService);
        AsistenciaController asistenciaController = new AsistenciaController(asistenciaService);
        NotaController notaController = new NotaController(notaService);
        ConsultasController consultasController = new ConsultasController(
                carreraService, materiaService, profesorConsultaService, planEstudioService,
                comisionService, alumnoService, examenService);

        // Vistas
        AlumnoView alumnoView = new AlumnoView(alumnoController, scanner);
        ExamenView examenView = new ExamenView(examenController, consultasController, scanner);
        AsistenciaView asistenciaView = new AsistenciaView(asistenciaController, scanner);
        ConsultasView consultasView = new ConsultasView(consultasController, scanner);
        NotaView notaView = new NotaView(notaController, consultasController, scanner);

        return new MenuView(alumnoView, examenView, asistenciaView, consultasView, notaView, scanner);
    }
}
