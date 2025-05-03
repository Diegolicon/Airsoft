package com.example.service;

import com.example.DTO.PessoaJuridicaDTO;
import com.example.DTO.PessoaJuridicaResponseDTO;
import com.example.model.PessoaJuridica;
import com.example.repository.PessoaJuridicaRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class PessoaJuridicaServiceImpl implements PessoaJuridicaService {

    @Inject
    PessoaJuridicaRepository pessoaJuridicaRepository;

    @Override
    @Transactional
    public PessoaJuridicaResponseDTO create(PessoaJuridicaDTO dto) {
        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setNome(dto.nome());
        pessoaJuridica.setEmail(dto.email());
        pessoaJuridica.setCnpj(dto.cnpj());

        pessoaJuridicaRepository.persist(pessoaJuridica);
        return PessoaJuridicaResponseDTO.valueOf(pessoaJuridica);
    }

    @Override
    @Transactional
    public void update(Long id, PessoaJuridicaDTO dto) {
        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findById(id);
        if (pessoaJuridica != null) {
            pessoaJuridica.setNome(dto.nome());
            pessoaJuridica.setEmail(dto.email());
            pessoaJuridica.setCnpj(dto.cnpj());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        pessoaJuridicaRepository.deleteById(id);
    }

    @Override
    public PessoaJuridicaResponseDTO findById(Long id) {
        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findById(id);
        return PessoaJuridicaResponseDTO.valueOf(pessoaJuridica);
    }

    @Override
    public List<PessoaJuridicaResponseDTO> findAll() {
        return pessoaJuridicaRepository.findAll()
                .stream()
                .map(PessoaJuridicaResponseDTO::valueOf)
                .toList();
    }

    @Override
    public PessoaJuridicaResponseDTO findByCnpj(String cnpj) {
        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findByCnpj(cnpj);
        return PessoaJuridicaResponseDTO.valueOf(pessoaJuridica);
    }
}
