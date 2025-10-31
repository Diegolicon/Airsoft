package com.example.DTO;

import jakarta.validation.constraints.*;

public record ClienteDTO(

        @NotNull(message = "O saldo deve ser informado.")
        @PositiveOrZero(message = "O saldo deve ser positivo ou zero.")
        double saldo,

        boolean vip,

        String observacoes,

        @NotNull(message = "O ID da pessoa deve ser informado.")
        Long pessoaId

) {}
