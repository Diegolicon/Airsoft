package com.example.DTO;

import com.example.model.Telefone;

public record TelefoneResponseDTO(
        Long id,
        String ddd,
        String numero,
        String tipo
) {
    public static TelefoneResponseDTO valueOf(Telefone telefone) {
        if (telefone == null)
            return null;
        return new TelefoneResponseDTO(
                telefone.getId(),
                telefone.getDdd(),
                telefone.getNumero(),
                telefone.getTipo()
                
        );
    }
}