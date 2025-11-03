package com.example.repository; // Certifique-se que o pacote está correto

import com.example.model.Pessoa;
import com.example.model.PessoaFisica;
import com.example.model.PessoaJuridica;

// 1. Importar o PanacheRepository
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;


@ApplicationScoped
public class PessoaRepository implements PanacheRepository<Pessoa> {

    public Optional<PessoaFisica> findByCpf(String cpf) {
        // "FROM PessoaFisica WHERE cpf = ?1"
        // O Panache nos deixa simplificar para:
        return find("cpf", cpf).firstResultOptional();
    }

    public Optional<PessoaJuridica> findByCnpj(String cnpj) {
        // "FROM PessoaJuridica WHERE cnpj = ?1"
        return find("cnpj", cnpj).firstResultOptional();
    }

    public Optional<Pessoa> findByEmail(String email) {
        // "FROM Pessoa WHERE email = ?1"
        return find("email", email).firstResultOptional();
    }

}