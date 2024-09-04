package com.fatec.livrariaonlinejpa.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.fatec.livrariaonlinejpa.dto.AddCarrinhoItemDTO;
import com.fatec.livrariaonlinejpa.model.Produto;
import com.fatec.livrariaonlinejpa.services.ProdutoService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProdutoController {
    private final ProdutoService produtoService;

    @GetMapping("/home")
    public String home(){
        return "home";
    }


    @GetMapping("/find")
    public String pesquisar(HttpSession session, @RequestParam String query, Model model){
        List<Produto> produtos = produtoService.pesquisar(query);
        model.addAttribute("produtoList",produtos);
        model.addAttribute("item", new AddCarrinhoItemDTO());
        return "/pesquisa";
    }

    @GetMapping("/produto/{id}")
    public String getProduto(HttpSession session, Model model, @PathVariable long id){
        model.addAttribute("produto",produtoService.findById(id));
        model.addAttribute("newItem",new AddCarrinhoItemDTO(id));
        return "produto";
    }


}
