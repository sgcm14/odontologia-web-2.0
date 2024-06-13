package clinica.sistemaReservaTurno.service;

import clinica.sistemaReservaTurno.entity.Odontologo;
import clinica.sistemaReservaTurno.repository.OdontologoRepository;
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
class OdontologoServiceTest {

    @Mock
    private OdontologoRepository odontologoRepository;

    @InjectMocks
    private OdontologoService odontologoService;

    @Test
    void testBuscarTodos() {
        List<Odontologo> odontologos = Arrays.asList(new Odontologo(), new Odontologo());
        when(odontologoRepository.findAll()).thenReturn(odontologos);

        List<Odontologo> result = odontologoService.buscarTodos();

        assertEquals(2, result.size());
    }

    @Test
    void testGuardarOdontologo() {
        Odontologo odontologo = new Odontologo();
        when(odontologoRepository.save(odontologo)).thenReturn(odontologo);

        Odontologo result = odontologoService.guardarOdontologo(odontologo);

        assertEquals(odontologo, result);
    }

    @Test
    void testBuscarPorID() {
        Odontologo odontologo = new Odontologo();
        when(odontologoRepository.findById(1L)).thenReturn(Optional.of(odontologo));

        Optional<Odontologo> result = odontologoService.buscarPorID(1L);

        assertEquals(Optional.of(odontologo), result);
    }

    @Test
    void testEliminarOdontologo() {
        odontologoService.eliminarOdontologo(1L);
        verify(odontologoRepository, times(1)).deleteById(1L);
    }
}
