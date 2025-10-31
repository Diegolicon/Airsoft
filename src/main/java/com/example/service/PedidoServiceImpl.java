package com.example.service;

import com.example.DTO.PedidoDTO;
import com.example.DTO.PedidoResponseDTO;
import com.example.model.Cliente;
import com.example.model.Pedido;
import com.example.repository.ClienteRepository;
import com.example.repository.PedidoRepository;
import com.example.repository.ProdutoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    ProdutoRepository produtoRepository; 

    

    @Override
    public List<PedidoResponseDTO> getAllPedidos() {
        return pedidoRepository.listAll().stream()
                .map(PedidoResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public PedidoResponseDTO getPedidoById(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado com o ID: " + id);
        }
        return PedidoResponseDTO.valueOf(pedido);
    }

    @Override
    @Transactional
    public PedidoResponseDTO updatePedido(Long id, PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado com o ID: " + id);
        }

        pedido.setStatus(pedidoDTO.status());

        Cliente cliente = clienteRepository.findById(pedidoDTO.clienteId());
        if (cliente == null) {
            throw new NotFoundException("Cliente não encontrado com o ID: " + pedidoDTO.clienteId());
        }
        pedido.setCliente(cliente);

        pedidoRepository.persist(pedido);
        return PedidoResponseDTO.valueOf(pedido);
    }

    @Override
    @Transactional
    public void deletePedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado com o ID: " + id);
        }
        pedidoRepository.delete(pedido);
    }

    @Override
    public PedidoResponseDTO createPedido(PedidoDTO pedidoDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createPedido'");
    }
}