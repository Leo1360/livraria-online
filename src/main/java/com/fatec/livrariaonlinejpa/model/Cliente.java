package com.fatec.livrariaonlinejpa.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Cliente{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String nome;

    private String dataNascimento; 

    private String cpf;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Endereco> enderecosEntrega;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Cartao> cartoes;

    
}
