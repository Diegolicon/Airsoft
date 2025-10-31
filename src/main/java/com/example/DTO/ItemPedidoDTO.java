// src/main/java/com/example/DTO/ItemPedidoDTO.java

package com.example.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemPedidoDTO(
        Long produtoId,

        @NotNull(message = "A quantidade deve ser informada.")
        @Positive(message = "A quantidade deve ser positiva.")
        Integer quantidade,

        @NotNull(message = "A quantidade deve ser informada.")
        @Positive(message = "A quantidade deve ser positiva.")
        Double precoUnitario
) {}