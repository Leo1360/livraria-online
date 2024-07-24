package com.fatec.livrariaonlinejpa.controller;

import com.fatec.livrariaonlinejpa.model.Cartao;
import com.fatec.livrariaonlinejpa.model.Cliente;
import com.fatec.livrariaonlinejpa.services.CartaoService;
import com.fatec.livrariaonlinejpa.services.ClienteService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cartao")
public class CartaoController {
    private final CartaoService cartaoService;
    private final ClienteService clienteService;

    @GetMapping("/listarCliente")
    public String listarCartoes(HttpSession session, Model model) {
        if(session.getAttribute("clienteId") == null){
            return "redirect:/cliente/novo";
        }
        Cliente cliente = clienteService.findById((long)session.getAttribute("clienteId"));
        model.addAttribute("listCartoes", cliente.getCartoes());
        return "/cliente/cartoes";
    }

    @GetMapping("/cadastrarcartao")
    public String cadastroCartao(HttpSession session,Model model, @RequestParam(name = "onEdit", required = false) String onEdit) {
        Cartao cartao = new Cartao();
        model.addAttribute("cartao", cartao);
        model.addAttribute("onEdit", onEdit);
        return "cliente/cadastrar_cartao";
    }


    @PostMapping("/addCartao")
    public String addCartao(HttpSession session, @ModelAttribute("cartao") Cartao cartao, @RequestParam(name = "onEdit", required = false) String onEdit) {
        // salvando cliente
        long clientid = (long)session.getAttribute("clienteId");
        cartaoService.save(cartao);
        clienteService.addCartao(clientid,cartao);
        // salvando id do cliente na sessão
        if(onEdit != null){
            return "redirect:/cartao/listarCliente";
        }
        clienteService.setCartaoPreferencial(clientid, cartao.getId());
        return "redirect:/endereco/cadastrar";
    }

    @GetMapping("/deleteCartao")
    public String  deleteCartao(HttpSession session,@RequestParam(name = "id") Long id){
        clienteService.removeCartao((long)session.getAttribute("clienteId"), id);
        return "redirect:/cartao/listarCliente";
    }

}
