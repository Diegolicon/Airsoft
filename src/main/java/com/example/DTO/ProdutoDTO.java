package com.example.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProdutoDTO(

        @NotBlank(message = "O nome da produto deve ser informado.")
        @Size(max = 60, message = "O nome deve ter no máximo 60 caracteres.")
        String nome,

        @NotBlank(message = "O fabricante deve ser informado.")
        @Size(max = 60, message = "O fabricante deve ter no máximo 60 caracteres.")
        String fabricante,

        @NotBlank(message = "O tipo da produto deve ser informado.")
        @Pattern(
                regexp = "(?i)pistola|fuzil|fuzil de precisao|escopeta|submetralhadora|metralhadora|carabina",
                message = "Tipo deve ser: pistola, fuzil, fuzil de precisao, escopeta, submetralhadora, metralhadora ou carabina."
        )
        String tipo,

        @NotBlank(message = "O sistema da produto deve ser informado.")
        @Pattern(
                regexp = "(?i)spring|semi-automatico|automatico",
                message = "Sistema deve ser: spring, semi-automatico ou automatico."
        )
        String sistema,

        @NotBlank(message = "O material da produto deve ser informado.")
        @Size(max = 30, message = "O material deve ter no máximo 30 caracteres.")
        String material,

        @Positive(message = "O preço deve ser um valor positivo.")
        double preco) {

}