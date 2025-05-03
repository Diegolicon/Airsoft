package com.example.service;

import com.example.DTO.PessoaFisicaDTO;
import com.example.DTO.PessoaFisicaResponseDTO;
import com.example.model.PessoaFisica;
import com.example.repository.PessoaFisicaRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class PessoaFisicaServiceImpl implements PessoaFisicaService {

    @Inject
    PessoaFisicaRepository pessoaFisicaRepository;

    @Override
    @Transactional
    public PessoaFisicaResponseDTO create(PessoaFisicaDTO dto) {
        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoaFisica.setNome(dto.nome());
        pessoaFisica.setEmail(dto.email());
        pessoaFisica.setCpf(dto.cpf());
        pessoaFisica.setIdade(dto.nascimento());

        pessoaFisicaRepository.persist(pessoaFisica);
        return PessoaFisicaResponseDTO.valueOf(pessoaFisica);
    }

    @Override
    @Transactional
    public void update(Long id, PessoaFisicaDTO dto) {
        PessoaFisica pessoaFisica = pessoaFisicaRepository.findById(id);
        if (pessoaFisica != null) {
            pessoaFisica.setNome(dto.nome());
            pessoaFisica.setEmail(dto.email());
            pessoaFisica.setCpf(dto.cpf());
            pessoaFisica.setIdade(dto.nascimento());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        pessoaFisicaRepository.deleteById(id);
    }

    @Override
    public PessoaFisicaResponseDTO findById(Long id) {
        PessoaFisica pessoaFisica = pessoaFisicaRepository.findById(id);
        return PessoaFisicaResponseDTO.valueOf(pessoaFisica);
    }

    @Override
    public List<PessoaFisicaResponseDTO> findAll() {
        return pessoaFisicaRepository.findAll()
                .stream()
                .map(PessoaFisicaResponseDTO::valueOf)
                .toList();
    }

    @Override
    public PessoaFisicaResponseDTO findByCpf(String cpf) {
        PessoaFisica pessoaFisica = pessoaFisicaRepository.findByCpf(cpf);
        return PessoaFisicaResponseDTO.valueOf(pessoaFisica);
    }
}
