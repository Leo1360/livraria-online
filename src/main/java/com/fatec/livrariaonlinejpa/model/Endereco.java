package com.fatec.livrariaonlinejpa.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Endereco{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    @ManyToOne(cascade = CascadeType.ALL)
    private Cidade cidade;
}
