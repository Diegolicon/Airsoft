package com.example.service;

import com.example.DTO.PessoaJuridicaDTO;
import com.example.DTO.PessoaJuridicaResponseDTO;

import java.util.List;

public interface PessoaJuridicaService {

    PessoaJuridicaResponseDTO create(PessoaJuridicaDTO dto);

    void update(Long id, PessoaJuridicaDTO dto);

    void delete(Long id);

    PessoaJuridicaResponseDTO findById(Long id);

    List<PessoaJuridicaResponseDTO> findAll();

    PessoaJuridicaResponseDTO findByCnpj(String cnpj);
}
