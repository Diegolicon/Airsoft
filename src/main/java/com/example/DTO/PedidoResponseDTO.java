package com.example.DTO;

import com.example.model.Pedido;

public record PedidoResponseDTO(
        Long id,
        double valorTotal,
        String status,
        Long clienteId) {

    public static PedidoResponseDTO valueOf(Pedido pedido) {
        if (pedido == null)
            return null;
        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getValorTotal(),
                pedido.getStatus(),
                pedido.getCliente().getId());
    }
}
