package com.fatec.livrariaonlinejpa.controller;


import com.fatec.livrariaonlinejpa.dto.NovaTrocaDTO;
import com.fatec.livrariaonlinejpa.model.*;
import com.fatec.livrariaonlinejpa.services.CartaoService;
import com.fatec.livrariaonlinejpa.services.ClienteService;

import com.fatec.livrariaonlinejpa.services.PedidoService;
import com.fatec.livrariaonlinejpa.services.RetornoMercadoriaService;
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
    private final RetornoMercadoriaService retornoMercadoriaService;


    @GetMapping("/setSession/{id}")
    public String getMethodName(HttpSession session,@PathVariable long id) {
        session.setAttribute("clienteId", id);
        return "redirect:/cliente/perfil";
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

    @PostMapping("/cadastrarCliente")
    public String cadastrarCliente(HttpSession session, @ModelAttribute("cliente") Cliente cliente) {
        // salvando cliente
        clienteService.save(cliente);
        // salvando id do cliente na sessão
        session.setAttribute("clienteId", cliente.getId());
        return "redirect:/cartao/cadastrarcartao";
    }

    @PostMapping("/editCliente")
    public String editCliente(HttpSession session, @ModelAttribute("cliente") Cliente cliente) {
        cliente.setId((long) session.getAttribute("clienteId"));
        clienteService.update(cliente);
        return "redirect:perfil";
    }

    @GetMapping("/deletarConta")
    public String deletarConta(HttpSession session) {
        clienteService.delete((Long) session.getAttribute("clienteId"));
        session.removeAttribute("clienteId");
        return "redirect:/cliente/novo";
    }

    //--------------------------------------------------------



}
