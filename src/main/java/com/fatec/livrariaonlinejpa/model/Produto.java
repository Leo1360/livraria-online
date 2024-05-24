package com.fatec.livrariaonlinejpa.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

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
    
    private BigDecimal valor = new BigDecimal(0);

}
