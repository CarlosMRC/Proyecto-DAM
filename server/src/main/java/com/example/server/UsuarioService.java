package com.example.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void registrarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setCorreoElectronico(usuarioDTO.getCorreoElectronico());
        usuario.setContraseña(usuarioDTO.getContraseña());
        usuarioRepository.save(usuario);
    }

    public boolean autenticar(String nombreUsuario, String contraseña) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario);
        if (usuario != null) {
            return usuario.getContraseña().equals(contraseña);
        }
        return false;
    }
}