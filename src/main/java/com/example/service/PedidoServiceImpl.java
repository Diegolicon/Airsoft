package com.example.service;

import com.example.DTO.PedidoDTO;
import com.example.DTO.PedidoResponseDTO;
import com.example.model.Cliente;
import com.example.model.Pedido;
import com.example.repository.ClienteRepository;
import com.example.repository.PedidoRepository;
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

    @Override
    @Transactional
    public PedidoResponseDTO createPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        pedido.setValorTotal(pedidoDTO.valorTotal());
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

        pedido.setValorTotal(pedidoDTO.valorTotal());
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
}
