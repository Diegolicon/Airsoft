package com.example.service;

import com.example.DTO.EnderecoDTO;
import com.example.DTO.EnderecoResponseDTO;

import java.util.List;

public interface EnderecoService {

    EnderecoResponseDTO create(Long pessoaId, EnderecoDTO dto);;

    EnderecoResponseDTO update(Long enderecoId, EnderecoDTO dto);

    void delete(Long id);

    EnderecoResponseDTO findById(Long id);

    List<EnderecoResponseDTO> findAll();

    List<EnderecoResponseDTO> findByCidade(String cidade);

    List<EnderecoResponseDTO> findByCep(String cep);
}
