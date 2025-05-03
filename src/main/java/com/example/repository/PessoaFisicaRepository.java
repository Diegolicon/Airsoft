package com.example.repository;

import com.example.model.PessoaFisica;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PessoaFisicaRepository implements PanacheRepository<PessoaFisica> {

    public PessoaFisica findByCpf(String cpf) {
        return find("cpf = ?1", cpf).firstResult();
    }
}
