package com.fatec.livrariaonlinejpa.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Endereco> enderecosEntrega;

    @ManyToOne
    private Endereco enderecoPreferencial;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Cartao> cartoes;

    @ManyToOne
    private Cartao cartaoPreferencial;
    
}
