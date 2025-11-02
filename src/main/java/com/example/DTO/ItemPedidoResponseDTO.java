// src/main/java/com/example/DTO/ItemPedidoResponseDTO.java

package com.example.DTO;

import java.io.Serializable;

public record ItemPedidoResponseDTO(
        Long id,
        Long produtoId,
        Integer quantidade,
        Double precoUnitario,
        Double subtotal
) implements Serializable {
    public static ItemPedidoResponseDTO fromEntity(com.example.model.ItemPedido itemPedido) {
        return new ItemPedidoResponseDTO(
            itemPedido.getId(),
            itemPedido.getProduto() != null ? itemPedido.getProduto().getId() : null,
            itemPedido.getQuantidade(),
            itemPedido.getPrecoUnitario(),
            itemPedido.getSubtotal()
        );
    }
}