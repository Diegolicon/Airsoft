package com.example.repository;

import java.util.List;
import java.util.Optional;

import com.example.model.Produto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<Produto> {

    public Optional<Produto> findByNome(String nome) {
        // "FROM Produto WHERE nome = ?1"
        return find("nome", nome).firstResultOptional();
    }

}