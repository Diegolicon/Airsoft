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

    /**
     * Busca uma PessoaJuridica pelo seu CNPJ.
     */
    public Optional<PessoaJuridica> findByCnpj(String cnpj) {
        // "FROM PessoaJuridica WHERE cnpj = ?1"
        return find("cnpj", cnpj).firstResultOptional();
    }

    /**
     * Busca qualquer Pessoa (PF ou PJ) pelo email.
     */
    public Optional<Pessoa> findByEmail(String email) {
        // "FROM Pessoa WHERE email = ?1"
        return find("email", email).firstResultOptional();
    }

    public Optional<PessoaFisica> findByCpfExceptId(String cpf, Long id) {
        if (id == null) {
            return findByCpf(cpf); // Reusa o método acima
        }
        // Query: "FROM PessoaFisica WHERE cpf = ?1 AND id != ?2"
        return find("cpf = ?1 AND id != ?2", cpf, id).firstResultOptional();
    }

    public Optional<PessoaJuridica> findByCnpjExceptId(String cnpj, Long id) {
        if (id == null) {
            return findByCnpj(cnpj);
        }
        // Query: "FROM PessoaJuridica WHERE cnpj = ?1 AND id != ?2"
        return find("cnpj = ?1 AND id != ?2", cnpj, id).firstResultOptional();
    }
}