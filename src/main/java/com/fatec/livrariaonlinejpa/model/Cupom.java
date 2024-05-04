package com.fatec.livrariaonlinejpa.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Cupom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;
    private double desconto;
    private TipoCupom tipo;
    private boolean ativo = true;
    @ManyToOne
    private Cliente cliente;
}
