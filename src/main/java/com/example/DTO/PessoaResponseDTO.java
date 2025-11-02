package com.example.DTO;

import com.example.model.Pessoa;
import com.example.model.PessoaFisica;
import com.example.model.PessoaJuridica;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record PessoaResponseDTO(
        Long id,
        String email,
        String tipoPessoa, // "PF" ou "PJ"
        List<TelefoneResponseDTO> telefones,
        List<EnderecoResponseDTO> enderecos,

        // Campos de PessoaFisica
        String nome,
        String cpf,
        LocalDate dataNascimento,

        // Campos de PessoaJuridica
        String razaoSocial,
        String cnpj
) {
    public static PessoaResponseDTO valueOf(Pessoa pessoa) {
        if (pessoa == null)
            return null;

        // Converte as listas de entidades em listas de DTOs
        List<TelefoneResponseDTO> telefonesDTO = pessoa.getTelefones().stream()
                .map(TelefoneResponseDTO::valueOf)
                .collect(Collectors.toList());

        List<EnderecoResponseDTO> enderecosDTO = pessoa.getEnderecos().stream()
                .map(EnderecoResponseDTO::valueOf)
                .collect(Collectors.toList());

        if (pessoa instanceof PessoaFisica pf) {
            // Se for PessoaFisica, preenche os campos de PF
            return new PessoaResponseDTO(
                    pf.getId(),
                    pf.getEmail(),
                    "PF",
                    telefonesDTO,
                    enderecosDTO,
                    pf.getNome(), // Vem do método polimórfico
                    pf.getCpf(),
                    pf.getDataNascimento(),
                    null, // razaoSocial// nomeFantasia
                    null  // cnpj
            );
        } else if (pessoa instanceof PessoaJuridica pj) {
            // Se for PessoaJuridica, preenche os campos de PJ
            return new PessoaResponseDTO(
                    pj.getId(),
                    pj.getEmail(),
                    "PJ",
                    telefonesDTO,
                    enderecosDTO,
                    null, // nome
                    null, // cpf
                    null, // dataNascimento
                    pj.getRazaoSocial(),
                    pj.getCnpj()
            );
        }

        
        return null;
    }
}