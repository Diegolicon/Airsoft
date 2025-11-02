package com.example.DTO;

import com.example.model.Endereco;

public record EnderecoResponseDTO(
        Long id,
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado

) {
    public static EnderecoResponseDTO valueOf(Endereco endereco) {
        if (endereco == null)
            return null;
        return new EnderecoResponseDTO(
                endereco.getId(),
                endereco.getCep(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado()

        );
    }
}
