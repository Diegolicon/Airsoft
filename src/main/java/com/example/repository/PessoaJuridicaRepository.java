package com.example.repository;

import com.example.model.PessoaJuridica;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PessoaJuridicaRepository implements PanacheRepository<PessoaJuridica> {

    public PessoaJuridica findByCnpj(String cnpj) {
        return find("cnpj = ?1", cnpj).firstResult();
    }
}

