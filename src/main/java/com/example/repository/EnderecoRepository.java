package com.example.repository;

import com.example.model.Endereco;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco> {

    public Endereco findBySigla(String cep) {
        return find("SELECT e FROM Endereco e WHERE e.cep = ?1 ", cep).firstResult();
    }
}