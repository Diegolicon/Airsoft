package com.example.DTO;

import com.example.model.ItemPedido;
import java.math.BigDecimal;


public record ItemResponseDTO(
        Long id,
        Integer quantidade,
        BigDecimal preco,
        ProdutoResponseDTO produto
) {
    public static ItemResponseDTO valueOf(ItemPedido item) {
        if (item == null)
            return null;
        return new ItemResponseDTO(
                item.getId(),
                item.getQuantidade(),
                item.getPreco(),
                ProdutoResponseDTO.valueOf(item.getProduto())
        );
    }
}