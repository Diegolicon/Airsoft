package com.example.DTO;

import com.example.model.Produto;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        String fabricante,
        String tipo,
        String sistema,
        String material,
        double preco
) {
    public static ProdutoResponseDTO valueOf(Produto produto) {
        if (produto == null)
            return null;
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getFabricante(),
                produto.getTipo(),
                produto.getSistema(),
                produto.getMaterial(),
                produto.getPreco()
        );
    }
}