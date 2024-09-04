package com.fatec.livrariaonlinejpa.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Cupom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String nome;
    private BigDecimal desconto = new BigDecimal(0);
    private TipoCupom tipo;
    private boolean ativo = true;
    @ManyToOne
    private Cliente cliente;
}
