package com.fatec.livrariaonlinejpa.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fatec.livrariaonlinejpa.model.ItemCompra;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/carrinho")
public class CompraController {
    
    @PostMapping("/addItem")
    public String addItemCarrinho(HttpSession session ,@RequestBody ItemCompra novoItem) {
        List<ItemCompra> itens = (List<ItemCompra>) session.getAttribute("listaProdutos");
        boolean find = false;
        for(ItemCompra item : itens){
            if(item.getProduto().getId() == novoItem.getProduto().getId()){
                item.setQnt(novoItem.getQnt());
                find = true;
                break;
            }
        }
        if(! find){
            itens.add(novoItem);
        }
        return "carrinho";
    }

    @PostMapping("/removeItem")
    public String removeItemCarrinho(HttpSession session ,@RequestBody long idProduto){
        List<ItemCompra> itens = (List<ItemCompra>) session.getAttribute("listaProdutos");
        ItemCompra itemRemove = null ;
        for(ItemCompra item : itens){
            if(item.getProduto().getId() == idProduto){
                itemRemove = item;
                break;
            }
        }
        itens.remove(itemRemove);
        return "carrinho";
    }



}
