package com.example.repository;

import java.util.List;

import com.example.model.Produto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<Produto> {

    public List<Produto> findByNome(String nome) {
        return find("nome LIKE ?1 ", "%" + nome + "%").list();
    }

}