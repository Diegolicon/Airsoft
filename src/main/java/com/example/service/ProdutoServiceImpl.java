package com.example.service;

import com.example.DTO.ProdutoDTO;
import com.example.DTO.ProdutoResponseDTO;
import com.example.model.Produto;
import com.example.repository.ProdutoRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class ProdutoServiceImpl implements ProdutoService {

    @Inject
    ProdutoRepository produtoRepository;

    @Override
    @Transactional
    public ProdutoResponseDTO create(ProdutoDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setFabricante(dto.fabricante());
        produto.setTipo(dto.tipo());
        produto.setSistema(dto.sistema());
        produto.setMaterial(dto.material());
        produto.setPreco(dto.preco());
        produto.setEstoque(dto.estoque());

        produtoRepository.persist(produto);
        return ProdutoResponseDTO.valueOf(produto);
    }

    @Override
    @Transactional
    public void update(Long id, ProdutoDTO dto) {
        Produto produto = produtoRepository.findById(id);
        produto.setNome(dto.nome());
        produto.setFabricante(dto.fabricante());
        produto.setTipo(dto.tipo());
        produto.setSistema(dto.sistema());
        produto.setMaterial(dto.material());
        produto.setPreco(dto.preco());
        produto.setEstoque(dto.estoque());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        produtoRepository.deleteById(id);
    }

    @Override
    public ProdutoResponseDTO findById(Long id) {
        return ProdutoResponseDTO.valueOf(produtoRepository.findById(id));
    }

    @Override
    public ProdutoResponseDTO findByNome(String nome) {
        return produtoRepository.findByNome(nome)
                .map(ProdutoResponseDTO::valueOf)
                .orElse(null);
    }

    @Override
    public List<ProdutoResponseDTO> findAll() {
        return produtoRepository.findAll()
                .stream()
                .map(ProdutoResponseDTO::valueOf)
                .toList();
    }
}