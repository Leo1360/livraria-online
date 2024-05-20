package com.fatec.livrariaonlinejpa.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Cupom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;
    private BigDecimal desconto = new BigDecimal(0);
    private TipoCupom tipo;
    private boolean ativo = true;
    @ManyToOne
    private Cliente cliente;
}
