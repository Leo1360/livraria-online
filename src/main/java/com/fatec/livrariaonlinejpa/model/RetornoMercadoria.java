package com.fatec.livrariaonlinejpa.model;

import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class RetornoMercadoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int qnt;
    private BigDecimal valor = new BigDecimal(0);
    private String motivo;
    @ManyToOne
    private ItemCompra itemCompra;
    private TipoRetornoMercadoria tipo;
    @ManyToOne
    private Pedido pedido;
    private StatusRetMercadoria status;
    @OneToOne(cascade = CascadeType.MERGE)
    private Cupom cupom;
}
