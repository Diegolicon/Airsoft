package com.example.DTO;

import com.example.model.PessoaFisica;

import java.time.LocalDate;

public record PessoaFisicaResponseDTO(
        Long id,
        String nome,
        String email,
        String cpf,
        LocalDate nascimento
) {
    public static PessoaFisicaResponseDTO valueOf(PessoaFisica pessoa) {
        if (pessoa == null)
            return null;
        return new PessoaFisicaResponseDTO(
                pessoa.getId(),
                pessoa.getNome(),
                pessoa.getEmail(),
                pessoa.getCpf(),
                pessoa.getIdade()
        );
    }
}
