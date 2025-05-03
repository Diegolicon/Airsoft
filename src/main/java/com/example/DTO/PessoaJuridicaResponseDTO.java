package com.example.DTO;

import com.example.model.PessoaJuridica;

public record PessoaJuridicaResponseDTO(
        Long id,
        String nome,
        String email,
        String cnpj
) {
    public static PessoaJuridicaResponseDTO valueOf(PessoaJuridica pessoa) {
        if (pessoa == null)
            return null;
        return new PessoaJuridicaResponseDTO(
                pessoa.getId(),
                pessoa.getNome(),
                pessoa.getEmail(),
                pessoa.getCnpj()
        );
    }
}
