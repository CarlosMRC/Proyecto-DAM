package com.example.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Async
    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTO usuarioDTO) {
        
        if (usuarioService.autenticar(usuarioDTO.getNombreUsuario(), usuarioDTO.getContraseña())) {
            return "Inicio de sesión exitoso";
        } else {
            return "Credenciales incorrectas";
        }
    }

    @Async
    @PostMapping("/registro")
    public String registrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        
        usuarioService.registrarUsuario(usuarioDTO);
        return "Usuario registrado exitosamente";
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "¡El servidor está funcionando correctamente!";
    }
}