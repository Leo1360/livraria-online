package com.fatec.livrariaonlinejpa.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ItemCompra> itens;
    @ManyToOne
    private Endereco enderecoEntrega;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Pagamento> pagamentoList;
    @ManyToOne
    private Cliente cliente;
    private String status;
    private LocalDate data;
    private double total;
}
