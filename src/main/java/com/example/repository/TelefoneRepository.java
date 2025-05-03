package com.example.repository;

import java.util.List;
import com.example.model.Telefone;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TelefoneRepository implements PanacheRepository<Telefone> {

    public List<Telefone> findByDdd(String ddd) {
        return list("ddd = ?1", ddd);
    }
}
