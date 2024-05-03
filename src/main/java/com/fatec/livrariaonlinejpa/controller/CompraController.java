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
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        if(pedido == null){
            pedido = new Pedido();
        }

        if(pedido.getCliente() == null){
            Cliente cliente = clienteService.findById((Long) session.getAttribute("clienteId"));
            pedido.setCliente(cliente);
        }

        if(pedido.getItens() == null){
            pedido.setItens(new ArrayList<>());
        }
        pedido.atualizarTotal();

        if(pedido.getEnderecoEntrega() == null ){
            pedido.setEnderecoEntrega(pedido.getCliente().getEnderecoPreferencial());
            pedido.setFrete(enderecoService.calcularFrete(pedido.getEnderecoEntrega(), pedido.getItens()));
        }

        if(pedido.getPagamentoList().isEmpty()){
            Pagamento pag =  new Pagamento();
            pag.setCartao(pedido.getCliente().getCartaoPreferencial());
            pag.setValor(pedido.getTotal() + pedido.getFrete());
            pedido.addPagamento(pag);
        }else{
            if(pedido.getPagamentoList().size() == 1){
                pedido.getPagamentoList().get(0).setValor(pedido.getTotal() + pedido.getFrete());
            }
        }


        session.setAttribute("pedido", pedido);
        model.addAttribute("pedido",pedido);
        model.addAttribute("itemModel",new AddCarrinhoItemDTO());
        return "compra/carrinho";
    }

    @GetMapping("/carrinho/selecionarEndereco")
    public String selecionarEndereco(HttpSession session, Model model){
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        model.addAttribute("enderecos",pedido.getCliente().getEnderecosEntrega());
        return "/compra/selecionar_endereco";
    }

    @GetMapping("/carrinho/selecionarCartao")
    public String selecionarCartao(HttpSession session, Model model){
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        model.addAttribute("cartoes",pedido.getCliente().getCartoes());
        return "/compra/selecionar_cartao";
    }

    @GetMapping("/produto/{id}")
    public String getProduto(HttpSession session, Model model, @PathVariable long id){
        model.addAttribute("produto",produtoService.findById(id));
        model.addAttribute("newItem",new AddCarrinhoItemDTO(id));
        return "produto";
    }

//-------------------------------------------------------------------------------------------

    @PostMapping("/carrinho/addItem")
    public String addItemCarrinho(HttpSession session , @ModelAttribute("newItem") AddCarrinhoItemDTO itemDto) {
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        if(pedido == null){
            pedido = new Pedido();
        }

        ItemCompra novoItem = itemDto.toItem(produtoService);

        boolean find = false;
        for(ItemCompra item : pedido.getItens()){
            if(item.getProduto().getId() == novoItem.getProduto().getId()){
                item.setQnt(novoItem.getQnt());
                find = true;
                break;
            }
        }
        if(! find){
            pedido.addItem(novoItem);
        }
        pedido.atualizarTotal();
        pedido.setFrete(enderecoService.calcularFrete(pedido.getEnderecoEntrega(), pedido.getItens()));
        session.setAttribute("pedido", pedido);
        return "redirect:/carrinho/show";
    }

    @PostMapping("/carrinho/editItem")
    public String editQntItem(HttpSession session , @ModelAttribute("newItem") AddCarrinhoItemDTO itemDto){
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        for(ItemCompra item : pedido.getItens()){
            if(item.getProduto().getId() == itemDto.getId()){
                item.setQnt(itemDto.getQnt());
                session.setAttribute("pedido", pedido);
                break;
            }
        }

        return "redirect:/carrinho/show";
    }

    @GetMapping("/carrinho/removeItem/{idProduto}")
    public String removeItemCarrinho(HttpSession session ,@PathVariable long idProduto){
        Pedido pedido = (Pedido) session.getAttribute("pedido");

        ItemCompra itemExcluir = pedido.getItens().stream().filter(item -> item.getProduto().getId() == idProduto).findFirst().orElse(null);
        if(itemExcluir != null){
            pedido.getItens().remove(itemExcluir);
            // TODO Verificar exclusão do item
            pedido.setFrete(enderecoService.calcularFrete(pedido.getEnderecoEntrega(), pedido.getItens()));
            session.setAttribute("pedido", pedido);
        }

        return "redirect:/carrinho/show";
    }

    @GetMapping("/carrinho/setEndereco/{enderecoId}")
    public String setEndereco(HttpSession session ,@PathVariable long enderecoId){
        Endereco endereco = enderecoService.findById(enderecoId);
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        pedido.setEnderecoEntrega(endereco);
        pedido.setFrete(enderecoService.calcularFrete(pedido.getEnderecoEntrega(),pedido.getItens()));
        return "redirect:/carrinho/show";
    }

    @GetMapping("/carrinho/addCartao/{id}")
    public String setCartoes(HttpSession session , @PathVariable long id){
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        boolean find = false;

        for(Pagamento pag : pedido.getPagamentoList()){
            if(pag.getCartao().getId() == id){
                find = true;
                break;
            }
        }

        if(! find){
            Pagamento pagamento = new Pagamento();
            pagamento.setCartao(cartaoService.findById(id));

            if(pedido.getPagamentoList().isEmpty()){
                pagamento.setValor(pedido.getTotal());
            }
            pedido.addPagamento(pagamento);
        }

        session.setAttribute("pedido", pedido);
        return "redirect:/carrinho/show";
    }

    @GetMapping("/carrinho/editarPagamento")
    public String editPagamento(HttpSession session ,@RequestParam("id") long id, @RequestParam("valor") double valor ){
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        for(Pagamento pagamento : pedido.getPagamentoList()){
            if (pagamento.getCartao().getId() == id){
                pagamento.setValor(valor);
            }
        }
        session.setAttribute("pedido", pedido);
        return "redirect:/carrinho/show";
    }

    @GetMapping("/carrinho/removerCartao/{id}")
    public String removerCartao(HttpSession session ,@PathVariable long id){
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        Pagamento pagamento = pedido.getPagamentoList().stream().filter(item -> item.getCartao().getId() == id).findFirst().orElse(null);
        pedido.getPagamentoList().remove(pagamento);
        session.setAttribute("pedido", pedido);
        return "redirect:/carrinho/show";
    }


    @GetMapping("/carrinho/finalizarCompra")
    public String finalizarCompra(HttpSession session){
        Pedido pedido = (Pedido) session.getAttribute("pedido");

        pedidoService.salvarNovoPedido(pedido);
        session.removeAttribute("enderecoEntrega");
        return "redirect:/cliente/pedidos";
    }

    //--------------------------------------------------------------------------------
    private boolean isLogged(HttpSession session){
        return (boolean) session.getAttribute("isLogged");
    }

    private void setLoggin(HttpSession session, boolean isLogged){
        session.setAttribute("isLogged", isLogged);
    }

}
