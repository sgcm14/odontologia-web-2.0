package clinica.sistemaReservaTurno.service;

import clinica.sistemaReservaTurno.entity.Turno;
import clinica.sistemaReservaTurno.repository.TurnoRepository;
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
class TurnoServiceTest {

    @Mock
    private TurnoRepository turnoRepository;

    @InjectMocks
    private TurnoService turnoService;

    @Test
    void testBuscarTodos() {
        List<Turno> turnos = Arrays.asList(new Turno(), new Turno());
        when(turnoRepository.findAll()).thenReturn(turnos);

        List<Turno> result = turnoService.buscarTodos();

        assertEquals(2, result.size());
    }

    @Test
    void testGuardarTurno() {
        Turno turno = new Turno();
        when(turnoRepository.save(turno)).thenReturn(turno);

        Turno result = turnoService.guardarTurno(turno);

        assertEquals(turno, result);
    }

    @Test
    void testBuscarPorID() {
        Turno turno = new Turno();
        when(turnoRepository.findById(1L)).thenReturn(Optional.of(turno));

        Optional<Turno> result = turnoService.buscarPorID(1L);

        assertEquals(Optional.of(turno), result);
    }

    @Test
    void testEliminarTurno() {
        turnoService.eliminarTurno(1L);
        verify(turnoRepository, times(1)).deleteById(1L);
    }
}
