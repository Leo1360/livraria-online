package com.fatec.livrariaonlinejpa.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ManyToAny;

import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class ItemCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Produto produto;
    private BigDecimal valorUnit = new BigDecimal(0);
    private int qnt;
    private int qntDevolvida;
}
