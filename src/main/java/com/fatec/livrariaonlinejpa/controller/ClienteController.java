package com.fatec.livrariaonlinejpa.controller;


import com.fatec.livrariaonlinejpa.model.Cartao;
import com.fatec.livrariaonlinejpa.model.Cliente;
import com.fatec.livrariaonlinejpa.model.Endereco;
import com.fatec.livrariaonlinejpa.services.ClienteService;

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




@Controller
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;
    
    @GetMapping("/novo")
    public String novocliente(HttpSession httpSession,Model model) {
        Cliente cliente = new Cliente();
        model.addAttribute("cliente", cliente);
        return "cadastro";
    }

    @GetMapping("/cartoes")
    public String listarCartoes(HttpSession httpSession,Model model) {
        return "cartoes";
    }

    @GetMapping("/cadastrarcartao")
    public String cadastroCartao(HttpSession httpSession,Model model) {
        Cartao cartao = new Cartao();
        model.addAttribute("cartao", cartao);
        return "cadastrar_cartao";
    }

    @GetMapping("/perfil")
    public String mostrarCliente(HttpSession httpSession,Model model) {
        Cliente cliente = clienteService.findById((long)httpSession.getAttribute("clienteId"));
        model.addAttribute("cliente", cliente);
        return "perfil";
    }

    @GetMapping("/endereco")
    public String listarEndereco(HttpSession session,Model model) {
        Cliente cliente = clienteService.findById((long) session.getAttribute("clienteId"));
        model.addAttribute("enderecos", cliente.getEnderecosEntrega());
        return "enderecos";
    }

    @GetMapping("/cadastrarendereco")
    public String cadastroEndereco(HttpSession httpSession,Model model) {
        Endereco endereco =  new Endereco();
        model.addAttribute("endereco", endereco);
        return "cadastrar_endereco";
    }

    @GetMapping("/editar")
    public String editarCliente(HttpSession httpSession, Model model){
        Cliente cliente = clienteService.findById((long)httpSession.getAttribute("clienteId"));
        model.addAttribute("cliente", cliente);
        return "editar_cliente";
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
    public String addCartao(HttpSession session, @ModelAttribute("cartao") Cartao cartao) {
        // salvando cliente
        clienteService.addCartao((long)session.getAttribute("clienteId"),cartao);
        // salvando id do cliente na sessão
        return "redirect:/cliente/cadastrarendereco";
    }
    
    @PostMapping("/addEndereco")
    public String addEndereco(HttpSession session, @ModelAttribute("endereco") Endereco endereco) {
        // salvando cliente
        clienteService.addEnderecoEntrega((long)session.getAttribute("clienteId"),endereco);
        // salvando id do cliente na sessão
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

    @DeleteMapping("/deleteCartao/{id}")
    public String  deleteCartao(HttpSession session,@PathVariable Long id){
        clienteService.removeCartao((long)session.getAttribute("clienteId"), id);
        return "redirect:/cartoes";
    }

    @DeleteMapping("/deleteEndereco/{id}")
    public String  deleteEndereco(HttpSession session,@PathVariable Long id){
        clienteService.removeEnderecoEntrega((long)session.getAttribute("clienteId"), id);
        return "redirect:/endereco";
    }

    @GetMapping("/deletarConta")
    public String deletarConta(HttpSession session) {
        clienteService.delete((Long) session.getAttribute("clienteId"));
        return "redirect:/cliente/novo";
    }
    

}
