package com.fatec.livrariaonlinejpa.controller;

import com.fatec.livrariaonlinejpa.dto.AddCarrinhoItemDTO;
import com.fatec.livrariaonlinejpa.dto.EnderecoDTO;
import com.fatec.livrariaonlinejpa.model.*;
import com.fatec.livrariaonlinejpa.services.CarrinhoService;
import com.fatec.livrariaonlinejpa.services.PedidoService;
import com.fatec.livrariaonlinejpa.services.ProdutoService;
import com.fatec.livrariaonlinejpa.util.ValidationResult;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/carrinho")
public class CarrinhoController {
    private final CarrinhoService carrinhoService;
    private final PedidoService pedidoService;

    @PostMapping("/addCupom")
    public String addCupom(HttpSession session, @RequestParam("nome") String nome){
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        carrinhoService.addCupom(pedido,nome);

        session.setAttribute("pedido", pedido);
        return "redirect:/carrinho/show";
    }

    @GetMapping("/removerCupom/{id}")
    public String removerCupom(HttpSession session, @PathVariable Long id){
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        carrinhoService.removerCupom(pedido,id);
        session.setAttribute("pedido", pedido);
        return "redirect:/carrinho/show";
    }

    @PostMapping("/addItem")
    public String addItemCarrinho(HttpSession session , @ModelAttribute("newItem") AddCarrinhoItemDTO itemDto) {
        Pedido pedido = (Pedido) session.getAttribute("pedido");

        if(pedido == null){
            long clientId = (Long) session.getAttribute("clienteId");
            pedido = carrinhoService.inicializarCarrinho(clientId);
        }

        carrinhoService.addItem(pedido,itemDto);


        session.setAttribute("pedido", pedido);
        return "redirect:/carrinho/show";
    }

    @PostMapping("/editItem")
    public String editQntItem(HttpSession session , @ModelAttribute("newItem") AddCarrinhoItemDTO itemDto){
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        carrinhoService.editQntItem(pedido, itemDto.toItemIdAndQnt());
        session.setAttribute("pedido", pedido);
        return "redirect:/carrinho/show";
    }

    @GetMapping("/removeItem/{idProduto}")
    public String removeItemCarrinho(HttpSession session ,@PathVariable long idProduto){
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        carrinhoService.removerItem(pedido, idProduto);

        session.setAttribute("pedido", pedido);
        return "redirect:/carrinho/show";
    }

    @PostMapping("/setEndereco")
    public String setEndereco(HttpSession session ,@ModelAttribute EnderecoDTO ende){
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        carrinhoService.setEndereco(pedido, ende);

        session.setAttribute("pedido", pedido);
        return "redirect:/carrinho/show";
    }

    @GetMapping("/removerCartao/{id}")
    public String removerCartao(HttpSession session ,@PathVariable long id){
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        carrinhoService.removeCartao(pedido, id);
        session.setAttribute("pedido", pedido);
        return "redirect:/carrinho/show";
    }

    @GetMapping("/addCartao/{id}")
    public String setCartoes(HttpSession session , @PathVariable long id){
        Pedido pedido = (Pedido) session.getAttribute("pedido");

        carrinhoService.addCartao(pedido,id);


        session.setAttribute("pedido", pedido);
        return "redirect:/carrinho/show";
    }

    @GetMapping("/editarPagamento")
    public String editPagamento(HttpSession session ,@RequestParam("id") long id, @RequestParam("valor") BigDecimal valor ){
        Pedido pedido = (Pedido) session.getAttribute("pedido");

        session.setAttribute("pedido", pedido);
        return "redirect:/carrinho/show";
    }

    @GetMapping("/show")
    public String getCarrinho(HttpSession session, Model model){
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        String alert = (String) session.getAttribute("alert");
        model.addAttribute("alert",alert);
        session.removeAttribute("alert");
        long clientId = (Long) session.getAttribute("clienteId");

        pedido = carrinhoService.getCarrinho(pedido,clientId);

        session.setAttribute("pedido", pedido);
        model.addAttribute("pedido",pedido);
        model.addAttribute("itemModel",new AddCarrinhoItemDTO());
        return "compra/carrinho";
    }

    @GetMapping("/selecionarEndereco")
    public String selecionarEndereco(HttpSession session, Model model){
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        model.addAttribute("enderecos",pedido.getCliente().getEnderecosEntrega());
        model.addAttribute("enderecoDto", new EnderecoDTO());
        return "/compra/selecionar_endereco";
    }

    @GetMapping("/selecionarCartao")
    public String selecionarCartao(HttpSession session, Model model){
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        model.addAttribute("cartoes",pedido.getCliente().getCartoes());
        return "/compra/selecionar_cartao";
    }


    @GetMapping("/finalizarCompra")
    public String finalizarCompra(HttpSession session){
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        ValidationResult result = pedidoService.validarPedido(pedido);
        if(!result.isValid()){
            session.setAttribute("alert", result.getErrorText());
            return "redirect:/carrinho/show";
        }
        pedidoService.salvarNovoPedido(pedido);
        session.removeAttribute("pedido");
        return "redirect:/pedido/cliente";
    }

}
