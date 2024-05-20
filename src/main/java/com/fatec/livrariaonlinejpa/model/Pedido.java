package com.fatec.livrariaonlinejpa.model;

import java.math.BigDecimal;
import java.math.MathContext;
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
    private StatusPedido status;
    private LocalDate data;
    private BigDecimal subTotal = new BigDecimal(0);
    private BigDecimal frete = new BigDecimal(0);
    @ManyToOne
    private Cupom cupom;
    private BigDecimal desconto = new BigDecimal(0);



    public void addItem(ItemCompra item){
        this.itens.add(item);
    }

    public void addPagamento(Pagamento pagamento){
        this.pagamentoList.add(pagamento);
    }

    public void atualizarTotal(){
        BigDecimal total = new BigDecimal(0);
        for(ItemCompra item: this.itens){
            BigDecimal itemSub = item.getValorUnit().multiply(new BigDecimal(item.getQnt()));
            total = total.add(itemSub);
        }
        this.subTotal = total;
    }


    public double getTotal(){
        if(this.desconto != null && this.subTotal.doubleValue() < this.desconto.doubleValue()){
            return this.frete.doubleValue();
        }
        BigDecimal total = this.frete;
        total = total.add(this.subTotal);
        total = total.subtract(this.desconto);
        return total.doubleValue();
    }

    public BigDecimal getTotalBigDecimal(){
        if(this.desconto != null && this.subTotal.doubleValue() < this.desconto.doubleValue()){
            return this.frete;
        }
        BigDecimal total = this.frete;
        total = total.add(this.subTotal);
        total = total.subtract(this.desconto);
        return total;
    }
}
