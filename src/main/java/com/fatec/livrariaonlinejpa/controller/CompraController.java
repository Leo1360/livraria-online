package com.fatec.livrariaonlinejpa.controller;

import java.util.ArrayList;
import java.util.List;

import com.fatec.livrariaonlinejpa.dto.AddCarrinhoItemDTO;
import com.fatec.livrariaonlinejpa.model.*;
import com.fatec.livrariaonlinejpa.services.*;
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
    private final CartaoService cartaoService;

    @GetMapping("/carrinho/show")
    public String getCarrinho(HttpSession session, Model model){
        List<ItemCompra> compraList = (List<ItemCompra>) session.getAttribute("listaProdutos");
        if(compraList == null){
            compraList = new ArrayList<>();
        }
        model.addAttribute("itens", compraList);
        double total = 0;
        for(ItemCompra item: compraList){
            total += item.getValorUnit() * item.getQnt();
        }
        model.addAttribute("total",total);

        Endereco endereco = (Endereco) session.getAttribute("enderecoEntrega");
        Cliente cliente = clienteService.findById((Long) session.getAttribute("clienteId"));
        if(endereco == null ){
            endereco = cliente.getEnderecoPreferencial();
        }
        model.addAttribute("endereco",endereco);

        List<Pagamento> pagamentos = ((List<Pagamento>) session.getAttribute("cartoes"));
        Pagamento pag =  new Pagamento();
        if(pagamentos == null){
            pag.setCartao(cliente.getCartaoPreferencial());
            pag.setValor(total);
        }else{
            pag = pagamentos.get(0);
        }
        model.addAttribute("pagamento", pag);

        return "compra/carrinho";
    }

    @GetMapping("/carrinho/selecionarEndereco")
    public String selecionarEndereco(HttpSession session, Model model){
        Cliente cliente = clienteService.findById((Long) session.getAttribute("clienteId"));
        model.addAttribute("enderecos",cliente.getEnderecosEntrega());
        return "/compra/selecionar_endereco";
    }

    @GetMapping("/carrinho/selecionarCartao")
    public String selecionarCartao(HttpSession session, Model model){
        Cliente cliente = clienteService.findById((Long) session.getAttribute("clienteId"));
        model.addAttribute("cartoes",cliente.getCartoes());
        return "/compra/selecionar_cartao";
    }


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

    @GetMapping("/carrinho/setEndereco/{enderecoId}")
    public String setEndereco(HttpSession session ,@PathVariable long enderecoId){
        Endereco endereco = enderecoService.findById(enderecoId);
        session.setAttribute("enderecoEntrega",endereco);
        return "redirect:/carrinho/show";
    }

    @GetMapping("/carrinho/setCartoes/{id}")
    public String setCartoes(HttpSession session , @PathVariable long id){
        List<Pagamento> pagamentoList = new ArrayList<>();
        Pagamento pagamento = new Pagamento();
        pagamento.setCartao(cartaoService.findById(id));
        List<ItemCompra> itens = (List<ItemCompra>) session.getAttribute("listaProdutos");
        if(itens != null){
            double total = 0;
            for( ItemCompra item: itens){
                total += item.getValorUnit() * item.getQnt();
            }
            pagamento.setValor(total);

        }
        pagamentoList.add(pagamento);
        session.setAttribute("cartoes",pagamentoList);
        return "redirect:/carrinho/show";
    }

    @GetMapping("/carrinho/finalizarCompra")
    public String finalizarCompra(HttpSession session){
        Pedido pedido = new Pedido();
        Cliente cliente = clienteService.findById((Long) session.getAttribute("clienteId"));
        pedido.setCliente(cliente);
        pedido.setItens((List<ItemCompra>) session.getAttribute("listaProdutos"));
        pedido.setPagamentoList((List<Pagamento>) session.getAttribute("cartoes"));
        pedido.setEnderecoEntrega((Endereco) session.getAttribute("enderecoEntrega"));
        pedidoService.salvarNovoPedido(pedido);
        session.removeAttribute("listaProdutos");
        session.removeAttribute("cartoes");
        session.removeAttribute("enderecoEntrega");
        return "redirect:/cliente/pedidos";
    }


    @GetMapping("/produto/{id}")
    public String getProduto(HttpSession session, Model model, @PathVariable long id){
        model.addAttribute("produto",produtoService.findById(id));
        model.addAttribute("newItem",new AddCarrinhoItemDTO(id));
        return "produto";
    }

}
