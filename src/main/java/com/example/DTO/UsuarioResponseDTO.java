package com.example.DTO;

import com.example.model.Perfil;
import com.example.model.Usuario;

public record UsuarioResponseDTO(Long id, String login, String senha, Perfil perfil) {

    public static UsuarioResponseDTO valueOf(Usuario usuario) {
        if (usuario == null)
            return null;
        return new UsuarioResponseDTO (
            usuario.getId(), 
            usuario.getLogin(), 
            usuario.getSenha(),
            usuario.getPerfil());
    }
    
}