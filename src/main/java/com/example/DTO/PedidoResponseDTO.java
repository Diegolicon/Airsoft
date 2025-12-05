package com.example.DTO;

import com.example.model.Pedido;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


public record PedidoResponseDTO(
        Long id,
        LocalDate dataPedido,
        String status,
        PessoaResponseDTO cliente,
        List<ItemPedidoResponseDTO> itens
) {
    public static PedidoResponseDTO valueOf(Pedido pedido) {
        if (pedido == null)
            return null;

        // Converte a lista de entidades ItemPedido em uma lista de DTOs
        List<ItemPedidoResponseDTO> listaItensDTO = pedido.getItens()
                .stream()
                .map(ItemPedidoResponseDTO::valueOf) // (item) -> ItemPedidoResponseDTO.valueOf(item)
                .collect(Collectors.toList());

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getDataPedido(),
                pedido.getStatus(),
                PessoaResponseDTO.valueOf(pedido.getPessoa()),
                listaItensDTO
        );
    }
}