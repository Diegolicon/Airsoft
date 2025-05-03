package com.example.DTO;

import com.example.model.Pessoa;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;

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

        @NotBlank(message = "A quantidade deve ser informada.")
        String quantidade,

        @NotNull(message = "O ID do cliente deve ser informado.")
        Long clienteId

) {}
