
package com.example.DTO;


import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;


public record PedidoDTO(
        @NotNull(message = "O ID da pessoa (cliente) não pode ser nulo.")
        @Positive(message = "O ID da pessoa (cliente) deve ser um número positivo.")
        Long pessoaId,

        @NotEmpty(message = "O pedido deve conter pelo menos um item.")
        @NotNull(message = "A lista de itens não pode ser nula.")
        @Valid // Valida cada ItemPedidoDTO dentro da lista
        List<ItemPedidoDTO> itens,

        LocalDate dataPedido,

        @NotBlank(message = "O status do produto deve ser informado.")
        @Pattern(
                regexp = "(?i)Processando|Enviado|Cancelado",
                message = "Tipo deve ser: Processando, Enviado ou Cancelado."
        )
        String status
) {
}
