package com.fatec.livrariaonlinejpa.model;

import jakarta.persistence.*;
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
    
    private double valor;

}
