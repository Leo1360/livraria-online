package com.fatec.livrariaonlinejpa.controller;

import java.util.ArrayList;
import java.util.List;

import com.fatec.livrariaonlinejpa.dto.AddCarrinhoItemDTO;
import com.fatec.livrariaonlinejpa.model.*;
import com.fatec.livrariaonlinejpa.services.ClienteService;
import com.fatec.livrariaonlinejpa.services.EnderecoService;
import com.fatec.livrariaonlinejpa.services.PedidoService;
import com.fatec.livrariaonlinejpa.services.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class CompraController {
    private final EnderecoService enderecoService;
    private final PedidoService pedidoService;
    private final ProdutoService produtoService;
    private final ClienteService clienteService;

    @PostMapping("/carrinho/addItem")
    public String addItemCarrinho(HttpSession session , @ModelAttribute("newItem") AddCarrinhoItemDTO itemDto) {
        ItemCompra novoItem = itemDto.toItem(produtoService);
        List<ItemCompra> itens = (List<ItemCompra>) session.getAttribute("listaProdutos");
        boolean find = false;
        if(itens == null){
            itens = new ArrayList<>();
            itens.add(novoItem);
        }else{
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
        }

        session.setAttribute("listaProdutos", itens);
        return "redirect:/carrinho/show";
    }

    @GetMapping("/carrinho/removeItem/{idProduto}")
    public String removeItemCarrinho(HttpSession session ,@PathVariable long idProduto){
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
        return "redirect:/carrinho/show";
    }

    @PostMapping("/carrinho/setEndereco")
    public String setEndereco(HttpSession session ,@RequestBody long enderecoId){
        Endereco endereco = enderecoService.findById(enderecoId);
        session.setAttribute("enderecoEntrega",endereco);
        return "Selecionar Cartão";
    }

    @PostMapping("/carrinho/setCartoes")
    public String setCartoes(HttpSession session , @RequestBody List<Pagamento> pagamentos){
        session.setAttribute("cartoes",pagamentos);
        return "conferir compra";
    }

    @GetMapping("/carrinho/finalizarCompra")
    public String finalizarCompra(HttpSession session , @RequestBody List<Pagamento> pagamentos){
        Pedido pedido = new Pedido();
        pedido.setItens((List<ItemCompra>) session.getAttribute("listaProdutos"));
        pedido.setPagamentoList((List<Pagamento>) session.getAttribute("cartoes"));
        pedido.setEnderecoEntrega((Endereco) session.getAttribute("enderecoEntrega"));
        pedidoService.salvarPedido(pedido);
        return "Pedidos";
    }

    @GetMapping("/carrinho/show")
    public String getCarrinho(HttpSession session, Model model){
        model.addAttribute("itens", (List<ItemCompra>) session.getAttribute("listaProdutos"));
        List<ItemCompra> compraList = (List<ItemCompra>) session.getAttribute("listaProdutos");
        float total = 0;
        for(ItemCompra item: compraList){
            total += item.getValorUnit() * item.getQnt();
        }
        model.addAttribute("total",total);

        Endereco endereco = (Endereco) session.getAttribute("enderecoEntrega");
        Cliente cliente = clienteService.findById((Long) session.getAttribute("clienteId"));
        if(endereco == null ){
            endereco = cliente.getEnderecosEntrega().stream().filter(Endereco::isPreferencial).findFirst().orElse(cliente.getEnderecosEntrega().get(0));
        }
        model.addAttribute("endereco",endereco);

        List<Pagamento> pagamentos = ((List<Pagamento>) session.getAttribute("cartoes"));
        Pagamento pag =  new Pagamento();
        if(pagamentos == null){
            pag.setCartao(cliente.getCartoes().get(0));
            pag.setValor(total);
        }else{
            pag = pagamentos.get(0);
        }
        model.addAttribute("pagamento", pag);

        return "compra/carrinho";
    }

    @GetMapping("/produto/{id}")
    public String getProduto(HttpSession session, Model model, @PathVariable long id){
        model.addAttribute("produto",produtoService.findById(id));
        model.addAttribute("newItem",new AddCarrinhoItemDTO(id));
        return "produto";
    }

}
