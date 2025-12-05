package com.example.repository;

import com.example.model.Pedido;
import com.example.model.Usuario;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {
    public PanacheQuery<Pedido> findByUsuario(Usuario usuario) {
        if (usuario == null || usuario.getId() == null)
            return null;
        return find("usuario.id = ?1 ",  usuario.getId());
    }

    public Pedido findByIdCompleto(Long id) {
        return find("SELECT p FROM Pedido p " +
                    "LEFT JOIN FETCH p.itens i " +
                    "LEFT JOIN FETCH i.produto " +
                    "WHERE p.id = ?1", id).firstResult();
    }
}
