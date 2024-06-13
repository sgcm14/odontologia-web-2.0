package clinica.sistemaReservaTurno.controller;

import clinica.sistemaReservaTurno.entity.Domicilio;
import clinica.sistemaReservaTurno.entity.Paciente;
import clinica.sistemaReservaTurno.service.PacienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PacienteController.class)
class PacienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PacienteService pacienteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testBuscarTodos() throws Exception {
        when(pacienteService.buscarTodos()).thenReturn(Arrays.asList(new Paciente(), new Paciente()));

        mockMvc.perform(get("/pacientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(pacienteService, times(1)).buscarTodos();
    }

    @Test
    void testRegistrarUnPaciente() throws Exception {
        Domicilio domicilio = new Domicilio("Las Acasias", 17, "sjm", "lima");
        Paciente paciente = new Paciente("Juan", "Perez", "12345678", LocalDate.now(), domicilio, "juan.perez@example.com");
        when(pacienteService.guardarPaciente(any(Paciente.class))).thenReturn(paciente);

        mockMvc.perform(post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paciente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));

        verify(pacienteService, times(1)).guardarPaciente(any(Paciente.class));
    }

    @Test
    void testBuscarPacientePorId() throws Exception {
        Domicilio domicilio = new Domicilio("Las Acasias", 17, "sjm", "lima");
        Paciente paciente = new Paciente("Juan", "Perez", "12345678", LocalDate.now(), domicilio, "juan.perez@example.com");
        when(pacienteService.buscarPorID(anyLong())).thenReturn(Optional.of(paciente));

        mockMvc.perform(get("/pacientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));

        verify(pacienteService, times(1)).buscarPorID(anyLong());
    }

    @Test
    void testEliminarPaciente() throws Exception {
        Domicilio domicilio = new Domicilio("Las Acasias", 17, "sjm", "lima");
        Paciente paciente = new Paciente("Juan", "Perez", "12345678", LocalDate.now(), domicilio, "juan.perez@example.com");
        when(pacienteService.buscarPorID(anyLong())).thenReturn(Optional.of(paciente));

        mockMvc.perform(delete("/pacientes/eliminar/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("paciente eliminado con exito"));

        verify(pacienteService, times(1)).eliminarPaciente(anyLong());
    }
}
