package clinica.sistemaReservaTurno.controller;

import clinica.sistemaReservaTurno.entity.Usuario;
import clinica.sistemaReservaTurno.repository.UsuarioRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@Tag(name = "RegistroController", description = "Endpoint para registrar nuevos usuarios")
public class RegistroController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/user")
    @Operation(summary = "Registrar un nuevo usuario", description = "Registra un nuevo usuario y devuelve el objeto del usuario registrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario registrado con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content)
    })
    public Usuario createUser(
            @RequestBody @Schema(description = "Detalles del usuario a registrar", example = "{ \"username\": \"john_doe\", \"password\": \"password123\", \"role\": \"USER\" }") Usuario user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usuarioRepository.save(user);
    }

}
