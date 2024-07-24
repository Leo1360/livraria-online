package com.fatec.livrariaonlinejpa.dto;

import com.fatec.livrariaonlinejpa.model.Cartao;
import com.fatec.livrariaonlinejpa.model.Pagamento;
import com.fatec.livrariaonlinejpa.services.CartaoService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Data
public class CartaoDTO {
    private long id;
    private BigDecimal valor;
    @Autowired
    private CartaoService service;

    public Pagamento toPagamento(){
        Cartao cartao = service.findById(this.id);
        Pagamento pagamento = new Pagamento();
        pagamento.setCartao(cartao);
        pagamento.setValor(this.valor);
        return pagamento;
    }

}
