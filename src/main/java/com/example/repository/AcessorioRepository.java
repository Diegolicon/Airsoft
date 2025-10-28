package com.example.repository;

import java.util.List;

import com.example.model.Acessorio;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AcessorioRepository implements PanacheRepository<Acessorio> {
    public List<Acessorio> findByTipo(String tipo) {
        return list("LOWER(tipo) LIKE ?1", "%" + tipo.toLowerCase() + "%");
    }

    public List<Acessorio> findByFabricante(String fabricante) {
        return list("fabricante = ?1", fabricante);
    }
}