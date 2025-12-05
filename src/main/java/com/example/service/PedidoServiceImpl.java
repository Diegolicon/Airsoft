package com.example.service;

import com.example.DTO.ItemPedidoDTO;
import com.example.DTO.PedidoDTO;
import com.example.DTO.PedidoResponseDTO;
import com.example.model.ItemPedido;
import com.example.model.Pedido;
import com.example.model.Pessoa;
import com.example.model.Produto;
import com.example.model.Usuario;
import com.example.repository.PedidoRepository;
import com.example.repository.PessoaRepository;
import com.example.repository.ProdutoRepository;
import com.example.repository.UsuarioRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    @Inject
    ProdutoRepository produtoRepository;

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    PessoaRepository pessoaRepository;

    @Inject
    UsuarioRepository usuarioRepository;


    @Override
    public List<PedidoResponseDTO> getAllPedidos() {
        return pedidoRepository.listAll().stream()
                .map(PedidoResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public PedidoResponseDTO getPedidoById(Long id) {
        Pedido pedido = pedidoRepository.findByIdCompleto(id);

        if (pedido == null)
            throw new NotFoundException("Pedido não encontrada.");
        return PedidoResponseDTO.valueOf(pedido);
    }

    @Override
    @Transactional
    public PedidoResponseDTO updatePedido(Long id, PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado com o ID: " + id);
        }

        if (!pedido.getPessoa().getId().equals(pedidoDTO.pessoaId())) {
            Pessoa novoCliente = pessoaRepository.findById(pedidoDTO.pessoaId());
            if (novoCliente == null) {
                throw new NotFoundException("Novo cliente (Pessoa) não encontrado com o ID: " + pedidoDTO.pessoaId());
            }
            pedido.setPessoa(novoCliente);
        }

        pedido.getItens().clear(); // Remove todos os itens antigos

        // Adiciona os novos itens (mesma lógica do createPedido)
        for (ItemPedidoDTO itemDTO : pedidoDTO.itens()) {
            Produto produto = produtoRepository.findById(itemDTO.produtoId());
            if (produto == null) {
                throw new NotFoundException("Produto não encontrado com o ID: " + itemDTO.produtoId());
            }
            if (produto.getEstoque() < itemDTO.quantidade()) {
                throw new BadRequestException("Estoque insuficiente para o produto: " + produto.getNome());
            }

            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.quantidade());
            item.setPreco(produto.getPreco()); // Preço no momento da ATUALIZAÇÃO
            item.setPedido(pedido);

            pedido.getItens().add(item);

        }

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
    @Transactional
    public PedidoResponseDTO createPedido(PedidoDTO pedidoDTO, String login) {
        Pedido entity = new Pedido();
        entity.setDataPedido(LocalDate.now());
        entity.setStatus("PROCESSANDO"); // Definindo um status padrão ou pegando do DTO
        
        // 1. Busca e define o usuário
        entity.setUsuario(usuarioRepository.findByLogin(login));

        // 2. Busca e define a Pessoa (Cliente) - ISSO ESTAVA FALTANDO
        Pessoa cliente = pessoaRepository.findById(pedidoDTO.pessoaId());
        if (cliente == null) {
            throw new NotFoundException("Cliente não encontrado");
        }
        entity.setPessoa(cliente);

        // 3. Processa os itens corretamente
        List<ItemPedido> itensPedido = new ArrayList<>();
        
        for (ItemPedidoDTO itemDTO : pedidoDTO.itens()) {
            Produto produto = produtoRepository.findById(itemDTO.produtoId());
            
            // Validação básica
            if (produto == null) {
                throw new NotFoundException("Produto não encontrado: " + itemDTO.produtoId());
            }
            // Opcional: Validar estoque aqui se necessário

            ItemPedido ip = new ItemPedido();
            ip.setPedido(entity);
            ip.setProduto(produto); // <--- O FIX PRINCIPAL ESTÁ AQUI
            ip.setQuantidade(itemDTO.quantidade());
            ip.setPreco(produto.getPreco()); // Salva o preço histórico
            
            itensPedido.add(ip);
        }

        entity.setItens(itensPedido);

        pedidoRepository.persist(entity);

        return PedidoResponseDTO.valueOf(entity);
    }
    @Override
    public List<PedidoResponseDTO> findByUsuario(String login) {
        Usuario usuario = usuarioRepository.findByLogin(login);
        return pedidoRepository.findByUsuario(usuario).list().stream().map(e -> PedidoResponseDTO.valueOf(e)).toList();
    }
}