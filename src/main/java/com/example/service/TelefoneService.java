package com.example.service;

import com.example.DTO.TelefoneDTO;
import com.example.DTO.TelefoneResponseDTO;
import java.util.List;

public interface TelefoneService {
    TelefoneResponseDTO create(Long pessoaId, TelefoneDTO dto);
    TelefoneResponseDTO findById(Long id);
    List<TelefoneResponseDTO> findAll();
    TelefoneResponseDTO update(Long telefoneId, TelefoneDTO dto);
    void delete(Long id);

    List<TelefoneResponseDTO> findByDdd(String ddd);

    List<TelefoneResponseDTO> getTelefonesByPessoaId(Long pessoaId);
}