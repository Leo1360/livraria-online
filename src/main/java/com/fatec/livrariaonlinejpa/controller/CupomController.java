package com.fatec.livrariaonlinejpa.controller;

import com.fatec.livrariaonlinejpa.model.Cupom;
import com.fatec.livrariaonlinejpa.services.CupomService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cupom")
public class CupomController {
    private final CupomService cupomService;

    @GetMapping("/show")
    public String listCupons(HttpSession session, Model model){
        long clientId = (Long) session.getAttribute("clienteId");
        List<Cupom> cupomList = cupomService.listByClienteId(clientId);
        model.addAttribute("cupons", cupomList);
        return "/cliente/cupons_cliente";
    }

    @GetMapping("/show/adm")
    public String listAllCupons(HttpSession session, Model model){
        long clientId = (Long) session.getAttribute("clienteId");
        List<Cupom> cupomList = cupomService.findAll();
        model.addAttribute("cupons", cupomList);
        return "/admin/admin_cupons";
    }

    @PostMapping("/new")
    public String cadastrarCupom(HttpSession session, @RequestParam("nome") String nome, @RequestParam("desconto") double desconto){
        cupomService.gerarCupom(nome, desconto);
        return"redirect:/cupom/show/adm";
    }

    @GetMapping("/delet/{id}")
    public String deletarCupom(HttpSession session, @PathVariable long id){
        cupomService.delete(id);
        return "redirect:/cupom/show/adm";
    }

}
