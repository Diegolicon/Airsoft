package com.example.service;

import com.example.DTO.AcessorioDTO;
import com.example.DTO.AcessorioResponseDTO;

import java.util.List;

public interface AcessorioService {
    AcessorioResponseDTO create(AcessorioDTO dto);
    void update(Long id, AcessorioDTO dto);
    void delete(Long id);
    List<AcessorioResponseDTO> findByTipo(String nome);  
    List<AcessorioResponseDTO> findAll();
}