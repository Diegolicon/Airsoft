package com.example.model;

import jakarta.persistence.*;

@Entity
public class ItemPedido extends IdentidadePadrao{    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    private Integer quantidade;

    @Column(name = "preco_unitario")
    private double precoUnitario;

    @jakarta.persistence.Transient
    public double getSubtotal() {
        return precoUnitario * quantidade;
    }

    public ItemPedido(Pedido pedido, Produto produto, Integer quantidade, double precoUnitario){
        this.pedido = pedido;
        this.produto =  produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public ItemPedido(){
        
    }
}