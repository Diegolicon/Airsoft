package com.example.DTO;

import com.example.model.ItemPedido;
import java.math.BigDecimal;

public record ItemPedidoResponseDTO(
        Long id,
        Integer quantidade,
        BigDecimal preco, // O preço que foi salvo no pedido
        ProdutoResponseDTO produto
) {
    public static ItemPedidoResponseDTO valueOf(ItemPedido item) {
        if (item == null)
            return null;
        return new ItemPedidoResponseDTO(
                item.getId(),
                item.getQuantidade(),
                item.getPreco(),
                ProdutoResponseDTO.valueOf(item.getProduto())
        );
    }
}