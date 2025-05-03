package com.example.service;

import com.example.DTO.ClienteDTO;
import com.example.DTO.ClienteResponseDTO;
import com.example.model.Cliente;
import com.example.model.Pessoa;
import com.example.repository.ClienteRepository;
import com.example.repository.PessoaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    PessoaRepository pessoaRepository;

    private void validarCliente(ClienteDTO clienteDTO) {
        if (clienteDTO.pessoaId() == null) {
            throw new IllegalArgumentException("ID deve ser informado.");
        }
    }

    @Override
    @Transactional
    public ClienteResponseDTO create(ClienteDTO clienteDTO) {
        validarCliente(clienteDTO);

        Cliente cliente = new Cliente();
        cliente.setSaldo(clienteDTO.saldo());
        cliente.setVip(clienteDTO.vip());
        cliente.setObservacoes(clienteDTO.observacoes());
        Pessoa pessoa = pessoaRepository.findPessoa(clienteDTO.pessoaId());
        if (pessoa == null) {
            throw new NotFoundException("Pessoa não encontrado com o ID: " + clienteDTO.pessoaId());
        }
        cliente.setPessoa(pessoa);
        clienteRepository.persist(cliente);
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    public ClienteResponseDTO findById(Long id) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            return null; // Retorna null se não encontrar, o Resource trata o 404
        }
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    public List<ClienteResponseDTO> findAll() {
        List<Cliente> clientes = clienteRepository.listAll();
        return clientes.stream()
                .map(ClienteResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ClienteResponseDTO update(Long id, ClienteDTO clienteDTO) {
        validarCliente(clienteDTO);

        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            throw new NotFoundException("Cliente não encontrado com o ID: " + id);
        }

        cliente.setSaldo(clienteDTO.saldo());
        cliente.setVip(clienteDTO.vip());
        cliente.setObservacoes(clienteDTO.observacoes());

        clienteRepository.persist(cliente); // Atualiza a entidade
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            throw new NotFoundException("Cliente não encontrado com o ID: " + id);
        }
        clienteRepository.delete(cliente);
    }
}