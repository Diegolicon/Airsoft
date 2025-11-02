package com.example.DTO;

import com.example.model.Pessoa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EnderecoDTO(

        @NotBlank(message = "O CEP deve ser informado.")
        @Size(max = 9, message = "O CEP deve ter no máximo 9 caracteres.")
        String cep,

        @NotBlank(message = "O logradouro deve ser informado.")
        @Size(max = 100, message = "O logradouro deve ter no máximo 100 caracteres.")
        String logradouro,

        @NotBlank(message = "O número deve ser informado.")
        @Size(max = 10, message = "O número deve ter no máximo 10 caracteres.")
        String numero,

        @Size(max = 60, message = "O complemento deve ter no máximo 60 caracteres.")
        String complemento,

        @NotBlank(message = "O bairro deve ser informado.")
        @Size(max = 60, message = "O bairro deve ter no máximo 60 caracteres.")
        String bairro,

        @NotBlank(message = "A cidade deve ser informada.")
        @Size(max = 60, message = "A cidade deve ter no máximo 60 caracteres.")
        String cidade,

        @NotBlank(message = "O estado deve ser informado.")
        @Size(max = 2, min = 2, message = "O estado deve conter 2 caracteres (UF).")
        String estado


        ) {

}

