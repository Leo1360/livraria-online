package com.fatec.livrariaonlinejpa.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class RetornoMercadoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int qnt;
    private double valor;
    private String motivo;
    @ManyToOne
    private ItemCompra itemCompra;
    private TipoRetornoMercadoria tipo;
    @ManyToOne
    private Pedido pedido;
    private StatusRetMercadoria status;
    @OneToOne
    private Cupom cupom;
}
