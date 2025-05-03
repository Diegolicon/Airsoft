package com.example.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PessoaJuridicaDTO(

        @NotBlank(message = "O nome deve ser informado.")
        @Size(max = 60, message = "O nome deve ter no máximo 60 caracteres.")
        String nome,

        @NotBlank(message = "O e-mail deve ser informado.")
        @Email(message = "O e-mail deve ser válido.")
        @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres.")
        String email,

        @NotBlank(message = "O CNPJ deve ser informado.")
        @Size(min = 14, max = 14, message = "O CNPJ deve conter exatamente 14 dígitos.")
        String cnpj) {

}
