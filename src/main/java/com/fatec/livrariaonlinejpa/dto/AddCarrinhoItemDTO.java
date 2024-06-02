package com.fatec.livrariaonlinejpa.dto;

import com.fatec.livrariaonlinejpa.model.ItemCompra;
import com.fatec.livrariaonlinejpa.model.Produto;
import com.fatec.livrariaonlinejpa.services.ProdutoService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Data
public class AddCarrinhoItemDTO {
    private long id;
    private int qnt;

    public AddCarrinhoItemDTO(long id) {
        this.id = id;
    }

    public AddCarrinhoItemDTO() {
    }

    public ItemCompra toItem(){
        ItemCompra itemCompra =  new ItemCompra();
        Produto produto = new Produto();
        produto.setId(id);
        itemCompra.setProduto(produto);
        itemCompra.setQnt(qnt);
        return itemCompra;
    }

    public ItemCompra toItemIdAndQnt(){
        ItemCompra item = new ItemCompra();
        item.setId(this.id);
        item.setQnt(this.qnt);
        return item;
    }
}
