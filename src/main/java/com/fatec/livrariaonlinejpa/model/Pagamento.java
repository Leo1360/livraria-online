package com.fatec.livrariaonlinejpa.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Cartao cartao;
    private double valor;
}
