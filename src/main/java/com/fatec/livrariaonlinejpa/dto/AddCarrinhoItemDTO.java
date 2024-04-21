package com.fatec.livrariaonlinejpa.dto;

import com.fatec.livrariaonlinejpa.model.ItemCompra;
import com.fatec.livrariaonlinejpa.model.Produto;
import com.fatec.livrariaonlinejpa.services.ProdutoService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@Data
public class AddCarrinhoItemDTO {
    private long id;
    private int qnt;

    public AddCarrinhoItemDTO(long id) {
        this.id = id;
    }

    public AddCarrinhoItemDTO() {
    }

    public ItemCompra toItem(ProdutoService service){
        ItemCompra itemCompra =  new ItemCompra();
        itemCompra.setProduto(service.findById(id));
        itemCompra.setValorUnit(itemCompra.getProduto().getValor());
        itemCompra.setQnt(qnt);
        return itemCompra;
    }

}
