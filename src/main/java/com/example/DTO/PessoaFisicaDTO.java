package com.example.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PessoaFisicaDTO(

        @NotBlank(message = "O nome deve ser informado.")
        @Size(max = 60, message = "O nome deve ter no máximo 60 caracteres.")
        String nome,

        @NotBlank(message = "O e-mail deve ser informado.")
        @Email(message = "O e-mail deve ser válido.")
        @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres.")
        String email,

        @NotBlank(message = "O CPF deve ser informado.")
        @Size(min = 11, max = 11, message = "O CPF deve conter exatamente 11 dígitos.")
        String cpf,

        @NotNull(message = "A data de nascimento deve ser informada.")
        LocalDate nascimento

        ) {

}
