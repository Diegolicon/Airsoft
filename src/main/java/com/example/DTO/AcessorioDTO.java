package com.example.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AcessorioDTO(

        @NotBlank(message = "O nome do acessório deve ser informado.")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
        String nome,

        @NotBlank(message = "O fabricante deve ser informado.")
        @Size(max = 60, message = "O fabricante deve ter no máximo 60 caracteres.")
        String fabricante,

        @NotBlank(message = "O tipo do acessório deve ser informado.")
        @Pattern(
                regexp = "(?i)luneta|mira|silenciador|supressor|bipé|bandoleira|pente|carregador|kit conversao",
                message = "Tipo deve ser: Luneta, Mira, Silenciador, Supressor, Bipé, Bandoleira, Pente, Carregador ou Kit Conversao."
        )
        String tipo,

        @NotBlank(message = "O material deve ser informado.")
        @Size(max = 60, message = "O material deve ter no máximo 60 caracteres.")
        String material,

        @NotNull(message = "O preço deve ser informado.")
        @Positive(message = "O preço deve ser um valor positivo.")
        Float preco,

        @Size(max = 255, message = "A compatibilidade deve ter no máximo 255 caracteres.")
        String compatibilidade
) {}