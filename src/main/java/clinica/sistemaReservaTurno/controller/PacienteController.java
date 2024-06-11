package clinica.sistemaReservaTurno.controller;

import clinica.sistemaReservaTurno.entity.Paciente;
import clinica.sistemaReservaTurno.service.PacienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@Controller
@RestController //cambiamos pq no necesitamos tecnologia de vista.
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<Paciente>> buscarTodos(){
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    @PostMapping //nos permite crear o registrar un paciente
    public ResponseEntity<Paciente> registrarUnPaciente(@RequestBody Paciente paciente){
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }

    @PutMapping
    public ResponseEntity<String> actualizarPaciente(@RequestBody Paciente paciente){
        //necesitamos primeramente validar si existe o  no
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPorID(paciente.getId());
        if(pacienteBuscado.isPresent()){
            pacienteService.actualizarPaciente(paciente);
            return ResponseEntity.ok("paciente actualizado");
        }else{
            return  ResponseEntity.badRequest().body("no se encontro paciente");
        }

    }

    //Buscar Paciente por Id
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPacientePorId(@PathVariable Long id){

        Optional<Paciente> pacienteBuscado= pacienteService.buscarPorID(id);
        if(pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Paciente> buscarPorEmail(@PathVariable String email){
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPorEmail(email);
        if(pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id){
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPorID(id);
        if(pacienteBuscado.isPresent()){
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok("paciente eliminado con exito");
        }else{
            return ResponseEntity.badRequest().body("paciente no encontrado");
        }
    }
}
