package com.fatec.livrariaonlinejpa.controller;

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

    @GetMapping("/editar")
    public String editar(HttpSession session, Model model, @RequestParam(name = "id") long id) {
        Endereco endereco = service.findById(id);
        model.addAttribute("endereco", endereco);
        return "cliente/editar_endereco";
    }
    

    @PostMapping("/editarEndereco")
    public String editarEndereco(HttpSession session, @ModelAttribute("endereco") Endereco endereco) {
        service.update(endereco);
        return "redirect:/cliente/endereco";
    }
    
    
}
