package com.fatec.livrariaonlinejpa.controller;

import com.fatec.livrariaonlinejpa.model.Cliente;
import com.fatec.livrariaonlinejpa.services.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fatec.livrariaonlinejpa.model.Endereco;
import com.fatec.livrariaonlinejpa.services.EnderecoService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/endereco")
@RequiredArgsConstructor
public class EnderecoController {
    private final EnderecoService service;
    private final ClienteService clienteService;

    @GetMapping("/cadastrar")
    public String cadastroEndereco(HttpSession session,Model model, @RequestParam(required=false, name = "onEdit") String onEdit) {
        Endereco endereco =  new Endereco();
        model.addAttribute("endereco", endereco);
        model.addAttribute("onEdit", onEdit);
        return "cliente/cadastrar_endereco";
    }

    @GetMapping("/editar/{id}")
    public String editar(HttpSession session, Model model, @PathVariable long id) {
        Endereco endereco = service.findById(id);
        model.addAttribute("endereco", endereco);
        return "cliente/editar_endereco";
    }


    @PostMapping("/updateEndereco")
    public String updateEndereco(HttpSession session, @ModelAttribute("endereco") Endereco endereco) {
        service.update(endereco);
        return "redirect:/endereco/enderecoscliente";
    }

    @GetMapping("/enderecoscliente")
    public String listarEndereco(HttpSession session,Model model) {
        if(session.getAttribute("clienteId") == null){
            return "redirect:/cliente/novo";
        }
        Cliente cliente = clienteService.findById((long)session.getAttribute("clienteId"));
        model.addAttribute("listEndereco", cliente.getEnderecosEntrega());
        return "cliente/enderecos";
    }

    @PostMapping("/salvar")
    public String salvar(HttpSession session, @ModelAttribute("endereco") Endereco endereco, @RequestParam(required=false, name = "onEdit") String onEdit) {
        long clienteId = (long)session.getAttribute("clienteId");
        clienteService.addEnderecoEntrega(clienteId,endereco);
        if (onEdit != null) {
            return "redirect:/endereco/enderecoscliente";
        }
        return "redirect:/cliente/perfil";
    }

    @GetMapping("/deleteEndereco/{id}")
    public String deleteEndereco(HttpSession session,@PathVariable Long id){
        clienteService.removeEnderecoEntrega((long)session.getAttribute("clienteId"), id);
        return "redirect:/endereco/enderecoscliente";
    }
    
    
}
