package com.example.service;

import com.example.DTO.EnderecoDTO;
import com.example.DTO.EnderecoResponseDTO;
import com.example.model.Endereco;
import com.example.repository.EnderecoRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService {

    @Inject
    EnderecoRepository enderecoRepository;

    @Override
    @Transactional
    public EnderecoResponseDTO create(EnderecoDTO dto) {
        Endereco endereco = new Endereco();
        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setComplemento(dto.complemento());
        endereco.setBairro(dto.bairro());
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());
        endereco.setCep(dto.cep());

        enderecoRepository.persist(endereco);
        return EnderecoResponseDTO.valueOf(endereco);
    }

    @Override
    @Transactional
    public void update(Long id, EnderecoDTO dto) {
        Endereco endereco = enderecoRepository.findById(id);
        if (endereco != null) {
            endereco.setLogradouro(dto.logradouro());
            endereco.setNumero(dto.numero());
            endereco.setComplemento(dto.complemento());
            endereco.setBairro(dto.bairro());
            endereco.setCidade(dto.cidade());
            endereco.setEstado(dto.estado());
            endereco.setCep(dto.cep());
        }
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
    public EnderecoResponseDTO findByCep(String cep) {
        Endereco endereco = enderecoRepository.findBySigla(cep);
        return EnderecoResponseDTO.valueOf(endereco);
    }
}
