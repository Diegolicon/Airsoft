// src/main/java/com/example/DTO/ItemPedidoDTO.java

package com.example.DTO;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record ItemPedidoDTO(
        @NotNull(message = "O ID do produto não pode ser nulo.")
        @Positive(message = "O ID do produto deve ser um número positivo.")
        Long produtoId,

        @NotNull(message = "A quantidade não pode ser nula.")
        @Positive(message = "A quantidade deve ser de no mínimo 1.")
        Integer quantidade
) {
}