package com.example.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;



public record PessoaJuridicaDTO(
        @NotBlank(message = "A Razão Social é obrigatória")
        String razaoSocial,


        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Formato de email inválido")
        String email,

        @NotBlank(message = "O CNPJ é obrigatório")
        @Pattern(regexp = "\\d{14}", message = "O CNPJ deve conter 14 números, sem pontos ou traços")
        String cnpj
) {
}