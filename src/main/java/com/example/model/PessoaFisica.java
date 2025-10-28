package com.example.model;


import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class PessoaFisica extends Pessoa {

    private String cpf;
    private LocalDate nascimento;

    public PessoaFisica() {

    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getIdade() {
        return nascimento;
    }

    public void setIdade(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public PessoaFisica(LocalDate nascimento, String cpf) {
        this.nascimento = nascimento;
        this.cpf = cpf;
    }
}
