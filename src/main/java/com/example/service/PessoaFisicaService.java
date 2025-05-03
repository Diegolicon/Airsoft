package com.example.service;

import com.example.DTO.PessoaFisicaDTO;
import com.example.DTO.PessoaFisicaResponseDTO;

import java.util.List;

public interface PessoaFisicaService {

    PessoaFisicaResponseDTO create(PessoaFisicaDTO dto);

    void update(Long id, PessoaFisicaDTO dto);

    void delete(Long id);

    PessoaFisicaResponseDTO findById(Long id);

    List<PessoaFisicaResponseDTO> findAll();

    PessoaFisicaResponseDTO findByCpf(String cpf);
}
