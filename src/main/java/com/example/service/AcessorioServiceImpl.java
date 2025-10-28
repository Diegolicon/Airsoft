package com.example.service;

import com.example.DTO.AcessorioDTO;
import com.example.DTO.AcessorioResponseDTO;
import com.example.model.Acessorio;
import com.example.repository.AcessorioRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AcessorioServiceImpl implements AcessorioService {

    @Inject
    AcessorioRepository repository;

    @Override
    @Transactional
    public AcessorioResponseDTO create(AcessorioDTO dto) {
        Acessorio acessorio = new Acessorio();

        acessorio.setNome(dto.nome());
        acessorio.setFabricante(dto.fabricante());
        acessorio.setTipo(dto.tipo());
        acessorio.setMaterial(dto.material());
        acessorio.setPreco(dto.preco());
        acessorio.setCompatibilidade(dto.compatibilidade());

        repository.persist(acessorio);

        return AcessorioResponseDTO.valueOf(acessorio);
    }

    @Override
    public List<AcessorioResponseDTO> findAll() {
        return repository.findAll().list()
                .stream()
                .map(AcessorioResponseDTO::valueOf)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public void update(Long id, AcessorioDTO dto) {
        Acessorio acessorio = repository.findById(id);

        if (acessorio == null) {
            throw new NotFoundException("Acessório com ID " + id + " não encontrado para atualização."); 
        }

        acessorio.setNome(dto.nome());
        acessorio.setFabricante(dto.fabricante());
        acessorio.setTipo(dto.tipo()); 
        acessorio.setMaterial(dto.material());
        acessorio.setPreco(dto.preco());
        acessorio.setCompatibilidade(dto.compatibilidade());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        boolean deleted = repository.deleteById(id);
        
        if (!deleted) {
            throw new NotFoundException("Acessório com ID " + id + " não encontrado para exclusão.");
        }
    }

    @Override
    public List<AcessorioResponseDTO> findByTipo(String tipo) {
        return repository.findByTipo(tipo)
                .stream()
                .map(AcessorioResponseDTO::valueOf)
                .collect(Collectors.toList());
    }
}