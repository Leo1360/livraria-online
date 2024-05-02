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
    private List<ItemCompra> itens = new ArrayList<>();
    @ManyToOne
    private Endereco enderecoEntrega;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Pagamento> pagamentoList = new ArrayList<>();
    @ManyToOne
    private Cliente cliente;
    private String status;
    private LocalDate data;
    private double total;



    public void addItem(ItemCompra item){
        this.itens.add(item);
    }

    public void addPagamento(Pagamento pagamento){
        this.pagamentoList.add(pagamento);
    }

    public void atualizarTotal(){
        double total = 0;
        for(ItemCompra item: this.itens){
            total += item.getValorUnit() * item.getQnt();
        }
        this.total = total;
    }
}
