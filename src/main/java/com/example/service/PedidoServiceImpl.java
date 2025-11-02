package com.example.service;

import com.example.DTO.ItemPedidoDTO; // Importar o DTO de item
import com.example.DTO.PedidoDTO;
import com.example.DTO.PedidoResponseDTO;
import com.example.model.ItemPedido; // Importar a entidade ItemPedido
import com.example.model.Pedido;
import com.example.model.Pessoa; // Importar a entidade Pessoa
import com.example.model.Produto; // Importar a entidade Produto
import com.example.repository.PedidoRepository;
import com.example.repository.PessoaRepository; // Importar o repo de Pessoa
import com.example.repository.ProdutoRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException; // Para erros de negócio
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDate; // Para a data do pedido
import java.util.ArrayList; // Para a nova lista de itens
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    @Inject
    ProdutoRepository produtoRepository;

    @Inject
    PedidoRepository pedidoRepository;

    // --- MUDANÇA 1: Adicionar o PessoaRepository ---
    // Precisamos dele para encontrar o cliente (Pessoa)
    @Inject
    PessoaRepository pessoaRepository;


    @Override
    @Transactional
    public PedidoResponseDTO createPedido(PedidoDTO pedidoDTO) {

        Pedido pedido = new Pedido();

        // 1. Define os dados básicos do Pedido
        pedido.setDataPedido(LocalDate.now());
        pedido.setStatus("PROCESSANDO"); // Um status inicial padrão

        // 2. Busca o Cliente (Pessoa)
        Pessoa cliente = pessoaRepository.findById(pedidoDTO.pessoaId());
        if (cliente == null) {
            // Se o cliente não existe, não podemos criar o pedido
            throw new NotFoundException("Cliente (Pessoa) não encontrado com o ID: " + pedidoDTO.pessoaId());
        }
        pedido.setPessoa(cliente);

        // 3. Processa e converte os Itens do DTO para Entidades
        List<ItemPedido> itens = new ArrayList<>();
        for (ItemPedidoDTO itemDTO : pedidoDTO.itens()) {
            // Busca o produto no banco
            Produto produto = produtoRepository.findById(itemDTO.produtoId());
            if (produto == null) {
                throw new NotFoundException("Produto não encontrado com o ID: " + itemDTO.produtoId());
            }

            // (Lógica de Negócio Opcional: Validar estoque)
            if (produto.getEstoque() < itemDTO.quantidade()) {
                throw new BadRequestException("Estoque insuficiente para o produto: " + produto.getNome());
            }

            // Cria o novo ItemPedido
            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.quantidade());
            // PRÁTICA CRUCIAL: Salva o preço do produto NO MOMENTO da compra
            item.setPreco(produto.getPreco());

            // Link bi-direcional: O Item precisa saber a qual Pedido pertence
            item.setPedido(pedido);
            itens.add(item);

            // (Lógica de Negócio Opcional: Baixar estoque)
            // produto.setEstoque(produto.getEstoque() - itemDTO.quantidade());
            // produtoRepository.persist(produto); // (Panache gerencia isso)
        }

        // 4. Adiciona a lista final de itens ao pedido
        pedido.setItens(itens);

        // 5. Salva o Pedido (e os Itens, por causa do CascadeType.ALL)
        pedidoRepository.persist(pedido);

        // 6. Retorna o DTO de Resposta
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
        // --- MUDANÇA 2: Correção do nome do método ---
        // Trocado 'findPedido(id)' por 'findById(id)', que é o padrão do Panache
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado com o ID: " + id);
        }
        return PedidoResponseDTO.valueOf(pedido);
    }

    @Override
    @Transactional
    public PedidoResponseDTO updatePedido(Long id, PedidoDTO pedidoDTO) {
        // 1. Encontra o pedido existente
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado com o ID: " + id);
        }

        // (Lógica de Negócio Opcional: Não permitir alterar pedido já enviado)
        // if (!"PROCESSANDO".equals(pedido.getStatus())) {
        //     throw new BadRequestException("Não é possível alterar um pedido com status: " + pedido.getStatus());
        // }

        // 2. Atualiza o Cliente (se mudou)
        if (!pedido.getPessoa().getId().equals(pedidoDTO.pessoaId())) {
            Pessoa novoCliente = pessoaRepository.findById(pedidoDTO.pessoaId());
            if (novoCliente == null) {
                throw new NotFoundException("Novo cliente (Pessoa) não encontrado com o ID: " + pedidoDTO.pessoaId());
            }
            pedido.setPessoa(novoCliente);
        }

        // 3. Atualiza a lista de Itens (Abordagem: "Limpar e Adicionar")
        // Isso depende da sua entidade Pedido ter "orphanRemoval = true"

        // (Devolve o estoque dos itens antigos - Lógica complexa opcional)

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

            pedido.getItens().add(item); // Adiciona à lista

            // (Lógica de Negócio Opcional: Baixar estoque)
            // produto.setEstoque(produto.getEstoque() - itemDTO.quantidade());
        }

        // 4. Salva as alterações
        // O Panache gerencia a atualização da entidade 'pedido'
        // e o cascade fará a mágica nos itens (deletando os antigos e criando os novos)
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

        // (Lógica de Negócio Opcional: Devolver estoque ao deletar pedido)

        pedidoRepository.delete(pedido);
    }
}