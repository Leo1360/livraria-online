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
    private double subTotal;
    private double frete;
    @ManyToOne
    private Cupom cupom;
    private double desconto;



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
        this.subTotal = total;
    }

    public double getTotal(){
        if(this.subTotal<this.desconto){
            return this.frete;
        }
        return (this.frete + this.subTotal) - this.desconto;
    }
}
