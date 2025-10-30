package com.example.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

@Entity
public class Pedido extends IdentidadePadrao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valorTotal;
    private String status;

    @OneToMany(mappedBy = "id")
    private List<ItemPedido> itens = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    private Double CalcularTotal(){
        if (itens!=null) {
            for (ItemPedido i : itens) {     
                valorTotal += i.getSubtotal();  
            }     
        } return valorTotal; 
    } 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
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
