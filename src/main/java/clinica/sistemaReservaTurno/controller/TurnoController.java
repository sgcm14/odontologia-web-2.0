package clinica.sistemaReservaTurno.controller;

import clinica.sistemaReservaTurno.entity.Odontologo;
import clinica.sistemaReservaTurno.entity.Paciente;
import clinica.sistemaReservaTurno.entity.Turno;
import clinica.sistemaReservaTurno.service.OdontologoService;
import clinica.sistemaReservaTurno.service.PacienteService;
import clinica.sistemaReservaTurno.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@Controller
@RestController
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;


    @GetMapping
    public ResponseEntity<List<Turno>> buscarTodos(){
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @PostMapping //nos permite crear o registrar un turno
    public ResponseEntity<Turno> registrarUnTurno(@RequestBody Turno turno){
        /*PacienteService pacienteService= new PacienteService();
        OdontologoService odontologoService= new OdontologoService();
        if(pacienteService.buscarPorID(turno.getPaciente().getId())!=null&&odontologoService.buscarPorID(turno.getOdontologo().getId())!=null){
            return ResponseEntity.ok(turnoService.guardarTurno(turno));
        }else{
            //bad request or not found
            return ResponseEntity.badRequest().build();
        }*/
        // Validamos si el paciente y el odontologo existen
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorID(turno.getPaciente().getId());
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorID(turno.getOdontologo().getId());

        if(pacienteBuscado.isPresent() && odontologoBuscado.isPresent()){
            return ResponseEntity.ok(turnoService.guardarTurno(turno));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    /*public ResponseEntity<String> actualizarTurno(@RequestBody Turno turno){
        //necesitamos primeramente validar si existe o  no
        Optional<Turno> turnoBuscado= turnoService.buscarPorID(turno.getId());
        if(turnoBuscado.isPresent()){
            turnoService.actualizarTurno(turno);
            return ResponseEntity.ok("turno actualizado");
        }else{
            return  ResponseEntity.badRequest().body("no se encontro turno");
        }

    }*/
    public ResponseEntity<String> actualizarTurno(@RequestBody Turno turno){
        // Primero verificamos si el turno existe
        Optional<Turno> turnoBuscado = turnoService.buscarPorID(turno.getId());
        if(turnoBuscado.isPresent()){
            // Actualizamos los datos del turno
            //turno.setId(id);
            turnoService.actualizarTurno(turno);
            return ResponseEntity.ok("Turno actualizado");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turno no encontrado");
        }
    }

    //Buscar Turno por Id
    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarTurnoPorId(@PathVariable Long id){

        Optional<Turno> turnoBuscado= turnoService.buscarPorID(id);
        if(turnoBuscado.isPresent()){
            return ResponseEntity.ok(turnoBuscado.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id){
        Optional<Turno> turnoBuscado= turnoService.buscarPorID(id);
        if(turnoBuscado.isPresent()){
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok("turno eliminado con exito");
        }else{
            return ResponseEntity.badRequest().body("turno no encontrado");
        }
    }
    /*
    private TurnoService turnoService;

    @Autowired
    public TurnoController() {
        this.turnoService = new TurnoService();
    }

    private PacienteService pacienteService;
    private OdontologoService odontologoService;

    //Buscar Turno por Id
    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarTurnoPorId(@PathVariable Integer id){
        // Buscamos el turno por su ID utilizando el servicio
        Turno turno = turnoService.buscarPorID(id);

        // Verificamos si el turno fue encontrado
        if(turno != null){
            return ResponseEntity.ok(turno); // Devolvemos el turno con código 200 OK
        }else{
            return ResponseEntity.notFound().build(); // Devolvemos 404 Not Found si no se encuentra el turno
        }
    }

    //Crear o registrar un Turno
    @PostMapping
    public ResponseEntity<Turno> guardarTurno(@RequestBody Turno turno) throws Exception {

        PacienteService pacienteService = new PacienteService();
        OdontologoService odontologoService = new OdontologoService();
        if (pacienteService.buscarPaciente(turno.getPaciente().getId()) != null && odontologoService.buscarOdontologo(turno.getOdontologo().getId()) != null) {
            return ResponseEntity.ok(turnoService.guardarTurno(turno));
        } else {
            //bad request or not found
            return ResponseEntity.badRequest().build();
        }
    }

    //Actualizar un nuevo turno
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarTurno(@PathVariable Integer id, @RequestBody Turno turno){
        // Primero verificamos si el turno existe
        Turno turnoBuscado = turnoService.buscarPorID(id);
        if(turnoBuscado != null){
            // Actualizamos los datos del turno
            turno.setId(id);
            turnoService.actualizarTurno(turno);
            return ResponseEntity.ok("Turno actualizado");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turno no encontrado");
        }
    }

    //Eliminar turno por Id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Integer id){
        // Primero verificamos si el turno existe
        Turno turnoBuscado = turnoService.buscarPorID(id);
        if(turnoBuscado != null){
            // Eliminamos el turno
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok("Turno eliminado");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turno no encontrado");
        }
    }

    //Lista todos los turnos
    @GetMapping
    public ResponseEntity<List<Turno>> listarTodosLosOdontologos(){
        // Llamamos al servicio para obtener la lista de todos los turnos
        List<Turno> turnos = turnoService.listarTurnos();

        // Devolvemos la lista de turnos con código 200 OK
        return ResponseEntity.ok(turnos);
    }*/

}
