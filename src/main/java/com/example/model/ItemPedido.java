package com.example.model;

import jakarta.persistence.*;

@Entity
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "acessorio_id")
    private Acessorio acessorio;

    private Integer quantidade;

    @Column(name = "preco_unitario")
    private Double precoUnitario;

    public Double getSubtotal() {
        return precoUnitario * quantidade;
    }
}