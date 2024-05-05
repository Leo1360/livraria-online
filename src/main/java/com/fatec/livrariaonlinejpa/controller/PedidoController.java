package com.fatec.livrariaonlinejpa.controller;

import com.fatec.livrariaonlinejpa.dto.NovaTrocaDTO;
import com.fatec.livrariaonlinejpa.model.Cliente;
import com.fatec.livrariaonlinejpa.model.ItemCompra;
import com.fatec.livrariaonlinejpa.model.Troca;
import com.fatec.livrariaonlinejpa.services.ClienteService;
import com.fatec.livrariaonlinejpa.services.ItemCompraService;
import com.fatec.livrariaonlinejpa.services.TrocaService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pedido")
public class PedidoController {
    private final TrocaService trocaService;
    private final ItemCompraService itemCompraService;
    private final ClienteService clienteService;

    @PostMapping("/troca/criar")
    private String novaTroca(HttpSession session, @ModelAttribute("troca") NovaTrocaDTO trocaDTO){
        Troca troca = trocaService.toTroca(trocaDTO);
        itemCompraService.registrarDevolucaoItem(troca.getItemCompra().getId(), troca.getQnt());
        trocaService.save(troca);
        return "redirect:/cliente/perfil";
    }


}
