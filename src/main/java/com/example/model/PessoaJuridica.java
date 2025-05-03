package com.example.model;


import jakarta.persistence.Entity;

@Entity
public class PessoaJuridica extends Pessoa {

    private String cnpj;

    public PessoaJuridica() {

    }

    // Getters e setters
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public PessoaJuridica(String cnpj) {
        this.cnpj = cnpj;
    }
}

