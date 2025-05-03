package com.example.repository;

import com.example.model.Pedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {
    public Pedido findPedido(Long id) {
        return findById(id);
    }
}
