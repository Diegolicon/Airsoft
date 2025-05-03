package com.example.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public record TelefoneDTO(
        @NotBlank(message = "O DDD deve ser informado.")
        @Size(min = 2, max = 2, message = "O DDD deve ter 2 caracteres.")
        @Pattern(regexp = "[0-9]{2}", message = "O DDD deve conter apenas números.")
        String ddd,

        @NotBlank(message = "O número de telefone deve ser informado.")
        @Size(min = 8, max = 9, message = "O número de telefone deve ter entre 8 e 9 dígitos.")
        @Pattern(regexp = "[0-9]+", message = "O número de telefone deve conter apenas números.")
        String numero
) {
}