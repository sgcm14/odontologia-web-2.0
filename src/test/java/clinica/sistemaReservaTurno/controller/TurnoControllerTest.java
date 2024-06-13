package clinica.sistemaReservaTurno.controller;
import clinica.sistemaReservaTurno.entity.Turno;
import clinica.sistemaReservaTurno.service.TurnoService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TurnoController.class)
class TurnoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TurnoService turnoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testBuscarTodos() throws Exception {
        when(turnoService.buscarTodos()).thenReturn(Arrays.asList(new Turno(), new Turno()));

        mockMvc.perform(get("/turnos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(turnoService, times(1)).buscarTodos();
    }

    @Test
    void testRegistrarUnTurno() throws Exception {
        Turno turno = new Turno();
        when(turnoService.guardarTurno(any(Turno.class))).thenReturn(turno);

        mockMvc.perform(post("/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(turno)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(turno.getId()));

        verify(turnoService, times(1)).guardarTurno(any(Turno.class));
    }

    @Test
    void testBuscarTurnoPorId() throws Exception {
        Turno turno = new Turno();
        when(turnoService.buscarPorID(anyLong())).thenReturn(Optional.of(turno));

        mockMvc.perform(get("/turnos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(turno.getId()));

        verify(turnoService, times(1)).buscarPorID(anyLong());
    }

    @Test
    void testEliminarTurno() throws Exception {
        when(turnoService.buscarPorID(anyLong())).thenReturn(Optional.of(new Turno()));

        mockMvc.perform(delete("/turnos/eliminar/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Turno eliminado con éxito"));

        verify(turnoService, times(1)).eliminarTurno(anyLong());
    }
}
