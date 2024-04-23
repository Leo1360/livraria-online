package com.fatec.livrariaonlinejpa.controller;


import com.fatec.livrariaonlinejpa.model.Cartao;
import com.fatec.livrariaonlinejpa.model.Cliente;
import com.fatec.livrariaonlinejpa.model.Endereco;
import com.fatec.livrariaonlinejpa.model.Pedido;
import com.fatec.livrariaonlinejpa.services.ClienteService;

import com.fatec.livrariaonlinejpa.services.PedidoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;
    private final PedidoService pedidoService;

    @GetMapping("/setSession/{id}")
    public String getMethodName(HttpSession session,@PathVariable long id) {
        session.setAttribute("clienteId", id);
        return "redirect:/cliente/perfil";
    }
    
    
    // Telas de exibição
    @GetMapping("/cartoes")
    public String listarCartoes(HttpSession session,Model model) {
        if(session.getAttribute("clienteId") == null){
            return "redirect:/cliente/novo";
        }
        Cliente cliente = clienteService.findById((long)session.getAttribute("clienteId"));
        model.addAttribute("listCartoes", cliente.getCartoes());
        return "cliente/cartoes";
    }
    
    @GetMapping("/endereco")
    public String listarEndereco(HttpSession session,Model model) {
        if(session.getAttribute("clienteId") == null){
            return "redirect:/cliente/novo";
        }
        Cliente cliente = clienteService.findById((long)session.getAttribute("clienteId"));
        model.addAttribute("listEndereco", cliente.getEnderecosEntrega());
        return "cliente/enderecos";
    }

    @GetMapping("/perfil")
    public String mostrarCliente(HttpSession session,Model model) {
        if(session.getAttribute("clienteId") == null ){
            return "redirect:/cliente/novo";
        }
        long clientId = (long)session.getAttribute("clienteId");
        Cliente cliente = clienteService.findById(clientId);
        model.addAttribute("cliente", cliente);
        return "/cliente/perfil";
    }



    // Telas de cadastro/forms
    @GetMapping("/novo")
    public String novocliente(HttpSession session,Model model) {
        Cliente cliente = new Cliente();
        model.addAttribute("cliente", cliente);
        return "cliente/cadastro";
    }

    @GetMapping("/editar")
    public String editarCliente(HttpSession session, Model model){
        Cliente cliente = clienteService.findById((long)session.getAttribute("clienteId"));
        model.addAttribute("cliente", cliente);
        return "cliente/editar_cliente";
    }

    @GetMapping("/cadastrarcartao")
    public String cadastroCartao(HttpSession session,Model model, @RequestParam(name = "onEdit", required = false) String onEdit) {
        Cartao cartao = new Cartao();
        model.addAttribute("cartao", cartao);
        model.addAttribute("onEdit", onEdit);
        return "cliente/cadastrar_cartao";
    }

    @GetMapping("/cadastrarendereco")
    public String cadastroEndereco(HttpSession session,Model model, @RequestParam(required=false, name = "onEdit") String onEdit) {
        Endereco endereco =  new Endereco();
        model.addAttribute("endereco", endereco);
        model.addAttribute("onEdit", onEdit);
        return "cliente/cadastrar_endereco";
    }


    @GetMapping("/pedidos")
    public String listPedidosCliente(HttpSession session,Model model){
        long id = (Long) session.getAttribute("clienteId");
        List<Pedido> pedidos = pedidoService.listarPedidosByCliente(id);
        model.addAttribute("pedidos", pedidos);
        return "/cliente/pedidos";
    }


    // --------------------create----------------------------

    @PostMapping("/cadastrarCliente")
    public String cadastrarCliente(HttpSession session, @ModelAttribute("cliente") Cliente cliente) {
        // salvando cliente
        clienteService.save(cliente);
        // salvando id do cliente na sessão
        session.setAttribute("clienteId", cliente.getId());
        return "redirect:/cliente/cadastrarcartao";
    }

    @PostMapping("/addCartao")
    public String addCartao(HttpSession session, @ModelAttribute("cartao") Cartao cartao, @RequestParam(name = "onEdit", required = false) String onEdit) {
        // salvando cliente
        clienteService.addCartao((long)session.getAttribute("clienteId"),cartao);
        // salvando id do cliente na sessão
        if(onEdit != null){
            return "redirect:/cliente/cartoes";
        }
        return "redirect:/cliente/cadastrarendereco";
    }
    
    @PostMapping("/addEndereco")
    public String addEndereco(HttpSession session, @ModelAttribute("endereco") Endereco endereco, @RequestParam(required=false, name = "onEdit") String onEdit) {
        // salvando cliente
        clienteService.addEnderecoEntrega((long)session.getAttribute("clienteId"),endereco);
        // salvando id do cliente na sessão
        if (onEdit != null) {
            return "redirect:/cliente/endereco";
        }
        return "redirect:/cliente/perfil";
    }

    // -------------------update----------------------------

    @PostMapping("/editCliente")
    public String editCliente(HttpSession session, @ModelAttribute("cliente") Cliente cliente) {
        cliente.setId((long) session.getAttribute("clienteId"));
        clienteService.update(cliente);
        return "redirect:perfil";
    }

    // ------------------DELETE------------------------

    @GetMapping("/deleteCartao")
    public String  deleteCartao(HttpSession session,@RequestParam(name = "id") Long id){
        clienteService.removeCartao((long)session.getAttribute("clienteId"), id);
        return "redirect:/cliente/cartoes";
    }

    @GetMapping("/deleteEndereco")
    public String  deleteEndereco(HttpSession session,@RequestParam(name = "id") Long id){
        clienteService.removeEnderecoEntrega((long)session.getAttribute("clienteId"), id);
        return "redirect:/cliente/endereco";
    }

    @GetMapping("/deletarConta")
    public String deletarConta(HttpSession session) {
        clienteService.delete((Long) session.getAttribute("clienteId"));
        session.removeAttribute("clienteId");
        return "redirect:/cliente/novo";
    }

    @GetMapping("/pedido/{id}")
    public String getDetalhesPedido(HttpSession session,@PathVariable Long id, Model model){
        Pedido pedido = pedidoService.findById(id);
        model.addAttribute("pedido", pedido);
        return "/cliente/pedido";

    }

}
