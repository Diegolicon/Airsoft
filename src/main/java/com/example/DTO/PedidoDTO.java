package com.example.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record PedidoDTO(

        @NotNull(message = "O valor total deve ser informado.")
        @Positive(message = "O valor total deve ser positivo.")
        double valorTotal,

        @NotBlank(message = "O status do pedido deve ser informado")
        @Pattern(
                regexp = "(?i)a caminho|entregue|separando",
                message = "O status deve ser 'A Caminho', 'Entregue' ou 'Separando'."
        )
        String status,

        @NotNull(message = "O ID do cliente deve ser informado.")
        Long clienteId,

        @Valid
        @NotEmpty(message = "O pedido deve conter pelo menos um item.")
        List<ItemPedidoDTO> itens
) {}
