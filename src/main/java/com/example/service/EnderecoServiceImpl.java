package com.example.service;

import com.example.DTO.EnderecoDTO;
import com.example.DTO.EnderecoResponseDTO;
import com.example.model.Endereco;
import com.example.model.Pessoa;
import com.example.repository.EnderecoRepository;

import com.example.repository.PessoaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService {

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    PessoaRepository pessoaRepository;

    @Override
    @Transactional
    public EnderecoResponseDTO create(Long pessoaId,EnderecoDTO dto) {

        Pessoa pessoa = pessoaRepository.findById(pessoaId);
        if (pessoa == null) {
            throw new NotFoundException("Pessoa não encontrada com o ID: " + pessoaId);
        }

        Endereco endereco = new Endereco();
        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setComplemento(dto.complemento());
        endereco.setBairro(dto.bairro());
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());
        endereco.setCep(dto.cep());
        endereco.setPessoa(pessoa);

        enderecoRepository.persist(endereco);
        return EnderecoResponseDTO.valueOf(endereco);
    }

    @Override
    @Transactional
    public EnderecoResponseDTO update(Long enderecoId, EnderecoDTO dto) {

        Endereco endereco = enderecoRepository.findById(enderecoId);
        if (endereco == null) {
            throw new NotFoundException("Endereço não encontrado com o ID: " + enderecoId);
        }
            endereco.setLogradouro(dto.logradouro());
            endereco.setNumero(dto.numero());
            endereco.setComplemento(dto.complemento());
            endereco.setBairro(dto.bairro());
            endereco.setCidade(dto.cidade());
            endereco.setEstado(dto.estado());
            endereco.setCep(dto.cep());

        enderecoRepository.persist(endereco); // O Panache gerencia o 'merge'

        return EnderecoResponseDTO.valueOf(endereco);

    }

    @Override
    @Transactional
    public void delete(Long id) {
        enderecoRepository.deleteById(id);
    }

    @Override
    public EnderecoResponseDTO findById(Long id) {
        Endereco endereco = enderecoRepository.findById(id);
        return EnderecoResponseDTO.valueOf(endereco);
    }

    @Override
    public List<EnderecoResponseDTO> findByCidade(String cidade) {
        return enderecoRepository.find("cidade", cidade)
                .list()
                .stream()
                .map(EnderecoResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<EnderecoResponseDTO> findAll() {
        return enderecoRepository.findAll()
                .stream()
                .map(EnderecoResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<EnderecoResponseDTO> findByCep(String cep) {
        List<Endereco> listaEntidades = enderecoRepository.findByCep(cep);

        // 2. Converte a lista de Entidades para uma lista de DTOs
        return listaEntidades.stream()
                .map(EnderecoResponseDTO::valueOf) // (e) -> EnderecoResponseDTO.valueOf(e)
                .collect(Collectors.toList());
    }
}
