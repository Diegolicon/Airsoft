package com.example.service;

import com.example.DTO.PedidoDTO;
import com.example.DTO.PedidoResponseDTO;
import java.util.List;

public interface PedidoService {

    PedidoResponseDTO createPedido(PedidoDTO pedidoDTO);
    List<PedidoResponseDTO> getAllPedidos();
    PedidoResponseDTO getPedidoById(Long id);
    PedidoResponseDTO updatePedido(Long id, PedidoDTO pedidoDTO);
    void deletePedido(Long id);
}
