package com.example.service;

import java.util.List;
import com.example.DTO.ProdutoDTO;
import com.example.DTO.ProdutoResponseDTO;

public interface ProdutoService {
    ProdutoResponseDTO create(ProdutoDTO produto);
    void update(Long id, ProdutoDTO produto);
    void delete(Long id);
    ProdutoResponseDTO findById(Long id);
    ProdutoResponseDTO findByNome(String nome);
    List<ProdutoResponseDTO> findAll();
}
