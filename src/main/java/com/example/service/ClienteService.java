package com.example.service;

import com.example.DTO.ClienteDTO;
import com.example.DTO.ClienteResponseDTO;
import java.util.List;

public interface ClienteService {

    ClienteResponseDTO create(ClienteDTO clienteDTO);
    List<ClienteResponseDTO> findAll();
    ClienteResponseDTO findById(Long id);
    ClienteResponseDTO update(Long id, ClienteDTO clienteDTO);
    void delete(Long id);
}
