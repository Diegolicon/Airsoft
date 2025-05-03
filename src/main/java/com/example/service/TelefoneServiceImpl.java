package com.example.service;

import com.example.DTO.TelefoneDTO;
import com.example.DTO.TelefoneResponseDTO;
import com.example.model.Telefone;
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

    private void validarTelefone(TelefoneDTO telefoneDTO) {
        if (telefoneDTO.ddd() == null || telefoneDTO.ddd().trim().isEmpty() || !telefoneDTO.ddd().matches("[0-9]{2}") ) {
            throw new IllegalArgumentException("DDD deve ser informado e conter 2 números.");
        }
        if (telefoneDTO.numero() == null || telefoneDTO.numero().trim().isEmpty() || !telefoneDTO.numero().matches("[0-9]+") || telefoneDTO.numero().length() < 8 || telefoneDTO.numero().length() > 9) {
            throw new IllegalArgumentException("Número de telefone deve ser informado e conter apenas números, com 8 ou 9 dígitos.");
        }
    }

    @Override
    @Transactional
    public TelefoneResponseDTO create(TelefoneDTO telefoneDTO) {
        validarTelefone(telefoneDTO);
        Telefone telefone = new Telefone();
        telefone.setDdd(telefoneDTO.ddd());
        telefone.setNumero(telefoneDTO.numero());
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
        validarTelefone(telefoneDTO);
        Telefone telefone = telefoneRepository.findById(id);
        if (telefone == null) {
            throw new NotFoundException("Telefone não encontrado com o ID: " + id);
        }
        telefone.setDdd(telefoneDTO.ddd());
        telefone.setNumero(telefoneDTO.numero());
        telefoneRepository.persist(telefone); // Atualiza a entidade
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
}