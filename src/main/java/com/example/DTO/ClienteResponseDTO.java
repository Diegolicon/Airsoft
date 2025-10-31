package com.example.DTO;

public record ClienteResponseDTO(

        Long id,
        double saldo,
        boolean vip,
        String observacoes,
        Long pessoaId

) {
    public static ClienteResponseDTO valueOf(com.example.model.Cliente cliente) {
        if (cliente == null)
            return null;
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getSaldo(),
                cliente.isVip(),
                cliente.getObservacoes(),
                cliente.getPessoa().getId()
        );
    }
}
