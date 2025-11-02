package com.example.service;

import com.example.DTO.TelefoneDTO;
import com.example.DTO.TelefoneResponseDTO;
import com.example.model.Pessoa;
import com.example.model.Telefone;
import com.example.repository.PessoaRepository;
import com.example.repository.TelefoneRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TelefoneServiceImpl implements TelefoneService {

    @Inject
    TelefoneRepository telefoneRepository;

    @Inject
    PessoaRepository pessoaRepository;


    @Override
    @Transactional
    public TelefoneResponseDTO create(Long pessoaId, TelefoneDTO dto) {

        Pessoa pessoa = pessoaRepository.findById(pessoaId);
        if (pessoa == null) {
            throw new NotFoundException("Pessoa não encontrada com o ID: " + pessoaId);
        }

        Telefone telefone = new Telefone();
        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());
        telefone.setTipo(dto.tipo());
        telefone.setPessoa(pessoa);

        telefoneRepository.persist(telefone);

        return TelefoneResponseDTO.valueOf(telefone);
    }

    @Override
    public TelefoneResponseDTO findById(Long id) {
        Telefone telefone = telefoneRepository.findById(id);
        if (telefone == null) {
            return null; // Retorna null se não encontrar, o Resource trata o 404
        }
        return TelefoneResponseDTO.valueOf(telefone);
    }

    @Override
    public List<TelefoneResponseDTO> findAll() {
        List<Telefone> telefones = telefoneRepository.listAll();
        return telefones.stream()
                .map(TelefoneResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TelefoneResponseDTO update(Long id, TelefoneDTO telefoneDTO) {
        Telefone telefone = telefoneRepository.findById(id);
        if (telefone == null) {
            throw new NotFoundException("Telefone não encontrado com o ID: " + id);
        }
        telefone.setDdd(telefoneDTO.ddd());
        telefone.setNumero(telefoneDTO.numero());
        telefone.setTipo(telefoneDTO.tipo());

        telefoneRepository.persist(telefone);

            return TelefoneResponseDTO.valueOf(telefone);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        boolean deleted = telefoneRepository.deleteById(id);
        if (!deleted) {
            throw new NotFoundException("Telefone não encontrado com o ID: " + id);
        }
    }

    @Override
    public List<TelefoneResponseDTO> findByDdd(String ddd) {
        List<Telefone> telefones = telefoneRepository.findByDdd(ddd);
        return telefones.stream()
                .map(TelefoneResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    public List<TelefoneResponseDTO> getTelefonesByPessoaId(Long pessoaId) {
        return telefoneRepository.findByPessoaId(pessoaId).stream()
                .map(TelefoneResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

}