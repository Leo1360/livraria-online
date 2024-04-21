package com.fatec.livrariaonlinejpa.controller;

import java.util.List;

import com.fatec.livrariaonlinejpa.model.Endereco;
import com.fatec.livrariaonlinejpa.model.Pagamento;
import com.fatec.livrariaonlinejpa.model.Pedido;
import com.fatec.livrariaonlinejpa.services.EnderecoService;
import com.fatec.livrariaonlinejpa.services.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fatec.livrariaonlinejpa.model.ItemCompra;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/carrinho")
@RequiredArgsConstructor
public class CompraController {
    private final EnderecoService enderecoService;
    private final PedidoService pedidoService;

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
        session.setAttribute("listaProdutos", itens);
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
        session.setAttribute("listaProdutos", itens);
        return "carrinho";
    }

    @PostMapping("/setEndereco")
    public String setEndereco(HttpSession session ,@RequestBody long enderecoId){
        Endereco endereco = enderecoService.findById(enderecoId);
        session.setAttribute("enderecoEntrega",endereco);
        return "Selecionar Cartão";
    }

    @PostMapping("/setCartoes")
    public String setCartoes(HttpSession session , @RequestBody List<Pagamento> pagamentos){
        session.setAttribute("cartoes",pagamentos);
        return "conferir compra";
    }

    @GetMapping("/finalizarCompra")
    public String finalizarCompra(HttpSession session , @RequestBody List<Pagamento> pagamentos){
        Pedido pedido = new Pedido();
        pedido.setItens((List<ItemCompra>) session.getAttribute("listaProdutos"));
        pedido.setPagamentoList((List<Pagamento>) session.getAttribute("cartoes"));
        pedido.setEnderecoEntrega((Endereco) session.getAttribute("enderecoEntrega"));
        pedidoService.salvarPedido(pedido);
        return "Pedidos";
    }

    @GetMapping("/show")
    public String getCarrinho(HttpSession session, Model model){
        List<ItemCompra> compraList = (List<ItemCompra>) session.getAttribute("listaProdutos");
        model.addAttribute("itens", compraList);
        return "compra/carrinho";
    }

}
