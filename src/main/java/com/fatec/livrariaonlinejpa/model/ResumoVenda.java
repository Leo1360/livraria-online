package com.fatec.livrariaonlinejpa.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
public class ResumoVenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Produto produto;
    private LocalDate date;
    private int qntVendas;

    public ResumoVenda() {
    }

    public ResumoVenda(Produto produto, LocalDate date, int qntVendas) {
        this.produto = produto;
        this.date = date;
        this.qntVendas = qntVendas;
    }
}
