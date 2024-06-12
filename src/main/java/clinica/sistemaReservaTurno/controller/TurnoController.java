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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.time.format.DateTimeFormatter;

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

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    @GetMapping
    public ResponseEntity<List<Turno>> buscarTodos(){
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @PostMapping //nos permite crear o registrar un turno
    public ResponseEntity<Turno> registrarUnTurno(@RequestBody Turno turno){
        // Validamos si el paciente y el odontologo existen
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorID(turno.getPaciente().getId());
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorID(turno.getOdontologo().getId());

        if(pacienteBuscado.isPresent() && odontologoBuscado.isPresent()){
            turno.setFechaHoraCita(LocalDateTime.parse(turno.getFechaHoraCita().toString(), formatter));
            return ResponseEntity.ok(turnoService.guardarTurno(turno));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarTurno(@RequestBody Turno turno){
        // Primero verificamos si el turno existe
        Optional<Turno> turnoBuscado = turnoService.buscarPorID(turno.getId());
        if(turnoBuscado.isPresent()){
            // Actualizamos los datos del turno
            //turno.setId(id);
            turno.setFechaHoraCita(LocalDateTime.parse(turno.getFechaHoraCita().toString(), formatter));
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
}
