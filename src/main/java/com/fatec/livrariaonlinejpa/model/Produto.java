package com.fatec.livrariaonlinejpa.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

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
