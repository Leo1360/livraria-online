package com.fatec.livrariaonlinejpa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Troca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int qnt;
    private double valor;
    private String motivo;
    @ManyToOne
    private Cliente cliente;
    @ManyToOne
    private ItemCompra itemCompra;
}
