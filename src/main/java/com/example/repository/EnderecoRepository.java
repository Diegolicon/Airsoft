package com.example.repository;

import com.example.model.Endereco;
import com.example.model.Pessoa;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;


@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco> {


    public List<Endereco> findByPessoa(Pessoa pessoa) {
        // "FROM Endereco WHERE pessoa = ?1"
        return find("pessoa", pessoa).list();
    }


    public List<Endereco> findByPessoaId(Long pessoaId) {
        // "FROM Endereco WHERE pessoa.id = ?1"
        return find("pessoa.id", pessoaId).list();
    }

    public List<Endereco> findByCep(String cep) {
        // "FROM Endereco WHERE cep = ?1"
        return find("cep", cep).list();
    }
}