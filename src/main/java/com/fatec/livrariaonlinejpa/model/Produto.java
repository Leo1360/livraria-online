package com.fatec.livrariaonlinejpa.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String pathImg;

    private String nome;

    @Column(length = 500)
    private String descricao;

    private String ano;
    private String autor;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<CategoriaLivro> categoria;

    private String editora;
    private String edicao;
    private int numeroPaginas;
    private String isbn;
    private String cogBarras;
    private String dimensao;
    

    
    private BigDecimal valor = new BigDecimal(0);

}
