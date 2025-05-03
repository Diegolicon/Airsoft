package com.example.service;

import com.example.DTO.TelefoneDTO;
import com.example.DTO.TelefoneResponseDTO;
import java.util.List;

public interface TelefoneService {
    TelefoneResponseDTO create(TelefoneDTO telefoneDTO);
    TelefoneResponseDTO findById(Long id);
    List<TelefoneResponseDTO> findAll();
    TelefoneResponseDTO update(Long id, TelefoneDTO telefoneDTO);
    void delete(Long id);

    List<TelefoneResponseDTO> findByDdd(String ddd);
}