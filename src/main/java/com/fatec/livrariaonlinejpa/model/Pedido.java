package com.fatec.livrariaonlinejpa.model;

import java.math.BigDecimal;
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
    private BigDecimal Total = new BigDecimal(0);
    private BigDecimal frete = new BigDecimal(0);
    @ManyToMany
    private List<Cupom> cupons = new ArrayList<>();
    private BigDecimal desconto = new BigDecimal(0);



    public void addItem(ItemCompra item){
        this.itens.add(item);
    }

    public void addPagamento(Pagamento pagamento){
        this.pagamentoList.add(pagamento);
    }

    public void atualizarSubtotal(){
        BigDecimal total = new BigDecimal(0);
        for(ItemCompra item: this.itens){
            BigDecimal itemSub = item.getValorUnit().multiply(new BigDecimal(item.getQnt()));
            total = total.add(itemSub);
        }
        this.subTotal = total;
    }

    public int qntItensRestantes(){
        int devolvidos = 0;
        int total = 0;
        for(ItemCompra item : this.itens){
            devolvidos += item.getQntDevolvida();
            total += item.getQnt();
        }
        return total-devolvidos;
    }


}
