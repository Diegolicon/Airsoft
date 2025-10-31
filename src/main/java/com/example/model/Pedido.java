package com.example.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;

import jakarta.persistence.*;

@Entity
public class Pedido extends IdentidadePadrao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double valorTotal;
    private String status;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    private double CalcularTotal(){
        if (itens != null) {
            for (ItemPedido i : itens) {     
                valorTotal += i.getSubtotal();  
            }     
        } 
        return valorTotal;
    }

    public void AdicionarItem(Produto produto, Integer quantidade){
        ItemPedido item = new ItemPedido(this, produto, quantidade, produto.getPreco());
        itens.add(item);
        CalcularTotal();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}