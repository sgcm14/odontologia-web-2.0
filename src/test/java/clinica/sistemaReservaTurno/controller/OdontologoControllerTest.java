package clinica.sistemaReservaTurno.controller;
import clinica.sistemaReservaTurno.entity.Odontologo;
import clinica.sistemaReservaTurno.service.OdontologoService;

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

@WebMvcTest(OdontologoController.class)
class OdontologoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OdontologoService odontologoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testBuscarTodos() throws Exception {
        when(odontologoService.buscarTodos()).thenReturn(Arrays.asList(new Odontologo(), new Odontologo()));

        mockMvc.perform(get("/odontologos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(odontologoService, times(1)).buscarTodos();
    }

    @Test
    void testRegistrarUnOdontologo() throws Exception {
        Odontologo odontologo = new Odontologo("123", "John", "Doe");
        when(odontologoService.guardarOdontologo(any(Odontologo.class))).thenReturn(odontologo);

        mockMvc.perform(post("/odontologos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(odontologo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("John"));

        verify(odontologoService, times(1)).guardarOdontologo(any(Odontologo.class));
    }

    @Test
    void testBuscarOdontologoPorId() throws Exception {
        Odontologo odontologo = new Odontologo("123", "John", "Doe");
        when(odontologoService.buscarPorID(anyLong())).thenReturn(Optional.of(odontologo));

        mockMvc.perform(get("/odontologos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("John"));

        verify(odontologoService, times(1)).buscarPorID(anyLong());
    }

    @Test
    void testEliminarOdontologo() throws Exception {
        when(odontologoService.buscarPorID(anyLong())).thenReturn(Optional.of(new Odontologo()));

        mockMvc.perform(delete("/odontologos/eliminar/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("odontologo eliminado con exito"));

        verify(odontologoService, times(1)).eliminarOdontologo(anyLong());
    }
}
