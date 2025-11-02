package com.example.DTO;

import com.example.model.ItemPedido;
import java.math.BigDecimal;

/*
 * Documentação:
 * DTO de resposta para um item, usado dentro da lista do PedidoResponseDTO.
 * Ele mostra a quantidade, o preço *salvo* no pedido (e não o preço
 * atual do produto) e os dados simples do produto.
 */
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