package clinica.sistemaReservaTurno.service;

import clinica.sistemaReservaTurno.entity.Domicilio;
import clinica.sistemaReservaTurno.entity.Paciente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PacienteServiceTest {

    @Autowired
    private PacienteService pacienteService;

    @Test
    public void testCrearPaciente(){
        Domicilio domicilio = new Domicilio("Las Acasias",17,"sjm","lima");
        // Asegúrate de inicializar correctamente el objeto Domicilio según tu implementación
        Paciente paciente = new Paciente("Juan", "Perez", "12345678", LocalDate.now(), domicilio, "juan.perez@example.com");
        pacienteService.guardarPaciente(paciente);

        Optional<Paciente> pacienteGuardado =  pacienteService.buscarPorID(paciente.getId());

        assertTrue(pacienteGuardado != null);
    }

}