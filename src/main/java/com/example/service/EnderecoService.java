package com.example.service;

import com.example.DTO.EnderecoDTO;
import com.example.DTO.EnderecoResponseDTO;

import java.util.List;

public interface EnderecoService {

    EnderecoResponseDTO create(EnderecoDTO enderecoDTO);

    void update(Long id, EnderecoDTO enderecoDTO);

    void delete(Long id);

    EnderecoResponseDTO findById(Long id);

    List<EnderecoResponseDTO> findAll();

    List<EnderecoResponseDTO> findByCidade(String cidade);

    EnderecoResponseDTO findByCep(String cep);
}
