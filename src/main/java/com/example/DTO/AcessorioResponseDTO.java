package com.example.DTO;

import com.example.model.Acessorio;

public record AcessorioResponseDTO(
        Long id,
        String nome,
        String fabricante,
        String tipo,
        String material,
        Float preco,
        String compatibilidade
) {
    public static AcessorioResponseDTO valueOf(Acessorio acessorio) {
        if (acessorio == null)
            return null;
        return new AcessorioResponseDTO(
                acessorio.getId(),
                acessorio.getNome(),
                acessorio.getFabricante(),
                acessorio.getTipo(),
                acessorio.getMaterial(),
                acessorio.getPreco(),
                acessorio.getCompatibilidade()
        );
    }
}