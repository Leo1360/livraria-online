package com.fatec.livrariaonlinejpa.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Cartao cartao;
    private BigDecimal valor = new BigDecimal(0);
}
