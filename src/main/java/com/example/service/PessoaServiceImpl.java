package com.example.service;

import com.example.DTO.PessoaFisicaDTO;
import com.example.DTO.PessoaJuridicaDTO;
import com.example.DTO.PessoaResponseDTO;
import com.example.model.Pessoa;
import com.example.model.PessoaFisica;
import com.example.model.PessoaJuridica;
import com.example.repository.PessoaRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class PessoaServiceImpl implements PessoaService {

    @Inject
    PessoaRepository pessoaRepository;


    @Override
    @Transactional
    public PessoaResponseDTO createPessoaFisica(PessoaFisicaDTO dto) {

        if (pessoaRepository.findByCpf(dto.cpf()).isPresent()) {
            throw new BadRequestException("Já existe uma Pessoa Física com este CPF.");
        }

        PessoaFisica pf = new PessoaFisica();
        pf.setNome(dto.nome());
        pf.setEmail(dto.email());
        pf.setCpf(dto.cpf());
        pf.setDataNascimento(dto.dataNascimento());

        pessoaRepository.persist(pf);

        return PessoaResponseDTO.valueOf(pf);
    }

    @Override
    @Transactional
    public PessoaResponseDTO createPessoaJuridica(PessoaJuridicaDTO dto) {
        // Validação de Negócio: CNPJ já existe?
        if (pessoaRepository.findByCnpj(dto.cnpj()).isPresent()) {
            throw new BadRequestException("Já existe uma Pessoa Jurídica com este CNPJ.");
        }

        // Mapeamento: DTO -> Entidade
        PessoaJuridica pj = new PessoaJuridica();
        pj.setRazaoSocial(dto.razaoSocial());
        pj.setEmail(dto.email());
        pj.setCnpj(dto.cnpj());

        // Persistência
        pessoaRepository.persist(pj);

        return PessoaResponseDTO.valueOf(pj);
    }


    // Em: service/PessoaServiceImpl.java

    // --- Métodos de Leitura (READ) - CORRIGIDOS ---

    @Override
    public List<PessoaResponseDTO> getAllPessoas() {
        return pessoaRepository.listAll().stream()
                .map(PessoaResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public PessoaResponseDTO getPessoaById(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id);

        // MUDANÇA: Retorna null se não encontrar (para o teste 'assertNull' funcionar)
        return (pessoa == null) ? null : PessoaResponseDTO.valueOf(pessoa);
    }

    @Override
    public PessoaResponseDTO getPessoaByCpf(String cpf) {
        // MUDANÇA: Retorna null se o Optional estiver vazio
        return pessoaRepository.findByCpf(cpf)
                .map(PessoaResponseDTO::valueOf)
                .orElse(null);
    }

    @Override
    public PessoaResponseDTO getPessoaByCnpj(String cnpj) {
        // MUDANÇA: Retorna null se o Optional estiver vazio
        return pessoaRepository.findByCnpj(cnpj)
                .map(PessoaResponseDTO::valueOf)
                .orElse(null);
    }

    // ... (Os métodos create, update e delete continuam iguais) ...


    @Override
    @Transactional
    public PessoaResponseDTO updatePessoaFisica(Long id, PessoaFisicaDTO dto) {
        // 1. Encontra a entidade no banco
        Pessoa pessoa = pessoaRepository.findById(id);
        if (pessoa == null) {
            throw new NotFoundException("Pessoa não encontrada com o ID: " + id);
        }

        // 2. Valida se a Pessoa encontrada é do tipo correto
        if (!(pessoa instanceof PessoaFisica pf)) {
            throw new BadRequestException("A Pessoa com ID " + id + " não é uma Pessoa Física.");
        }

        // 3. Validação de Negócio: O novo CPF já pertence a OUTRA pessoa?
        Optional<PessoaFisica> pfExistente = pessoaRepository.findByCpf(dto.cpf());
        if (pfExistente.isPresent() && !pfExistente.get().getId().equals(id)) {
            throw new BadRequestException("O CPF " + dto.cpf() + " já está em uso por outra Pessoa.");
        }


        pf.setNome(dto.nome());
        pf.setEmail(dto.email());
        pf.setCpf(dto.cpf());
        pf.setDataNascimento(dto.dataNascimento());


        pessoaRepository.persist(pf);

        return PessoaResponseDTO.valueOf(pf);
    }

    @Override
    @Transactional
    public PessoaResponseDTO updatePessoaJuridica(Long id, PessoaJuridicaDTO dto) {

        Pessoa pessoa = pessoaRepository.findById(id);
        if (pessoa == null) {
            throw new NotFoundException("Pessoa não encontrada com o ID: " + id);
        }


        if (!(pessoa instanceof PessoaJuridica pj)) {
            throw new BadRequestException("A Pessoa com ID " + id + " não é uma Pessoa Jurídica.");
        }

        Optional<PessoaJuridica> pjExistente = pessoaRepository.findByCnpj(dto.cnpj());
        if (pjExistente.isPresent() && !pjExistente.get().getId().equals(id)) {
            throw new BadRequestException("O CNPJ " + dto.cnpj() + " já está em uso por outra Pessoa.");
        }

        pj.setRazaoSocial(dto.razaoSocial());
        pj.setEmail(dto.email());
        pj.setCnpj(dto.cnpj());

        pessoaRepository.persist(pj);

        return PessoaResponseDTO.valueOf(pj);
    }


    @Override
    @Transactional
    public void deletePessoa(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id);
        if (pessoa == null) {
            throw new NotFoundException("Pessoa não encontrada com o ID: " + id);
        }
        pessoaRepository.delete(pessoa);
    }
}