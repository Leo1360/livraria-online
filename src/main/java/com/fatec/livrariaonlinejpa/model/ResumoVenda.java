package com.fatec.livrariaonlinejpa.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

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
