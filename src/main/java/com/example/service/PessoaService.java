package com.example.service;

import com.example.DTO.PessoaFisicaDTO;
import com.example.DTO.PessoaJuridicaDTO;
import com.example.DTO.PessoaResponseDTO;

import java.util.List;


public interface PessoaService {

    PessoaResponseDTO createPessoaFisica(PessoaFisicaDTO dto);
    PessoaResponseDTO createPessoaJuridica(PessoaJuridicaDTO dto);
    List<PessoaResponseDTO> getAllPessoas();
    PessoaResponseDTO getPessoaById(Long id);
    PessoaResponseDTO getPessoaByCpf(String cpf);
    PessoaResponseDTO getPessoaByCnpj(String cnpj);
    PessoaResponseDTO updatePessoaFisica(Long id, PessoaFisicaDTO dto);
    PessoaResponseDTO updatePessoaJuridica(Long id, PessoaJuridicaDTO dto);
    void deletePessoa(Long id);
}