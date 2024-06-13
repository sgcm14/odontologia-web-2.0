package clinica.sistemaReservaTurno.service;
import clinica.sistemaReservaTurno.entity.Paciente;
import clinica.sistemaReservaTurno.repository.PacienteRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteService pacienteService;

    @Test
    void testBuscarTodos() {
        List<Paciente> pacientes = Arrays.asList(new Paciente(), new Paciente());
        when(pacienteRepository.findAll()).thenReturn(pacientes);

        List<Paciente> result = pacienteService.buscarTodos();

        assertEquals(2, result.size());
    }

    @Test
    void testGuardarPaciente() {
        Paciente paciente = new Paciente();
        when(pacienteRepository.save(paciente)).thenReturn(paciente);

        Paciente result = pacienteService.guardarPaciente(paciente);

        assertEquals(paciente, result);
    }

    @Test
    void testBuscarPorID() {
        Paciente paciente = new Paciente();
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));

        Optional<Paciente> result = pacienteService.buscarPorID(1L);

        assertEquals(Optional.of(paciente), result);
    }

    @Test
    void testEliminarPaciente() {
        pacienteService.eliminarPaciente(1L);
        verify(pacienteRepository, times(1)).deleteById(1L);
    }
}