package com.fatec.livrariaonlinejpa.controller;

import com.fatec.livrariaonlinejpa.dto.NovaTrocaDTO;
import com.fatec.livrariaonlinejpa.model.RetornoMercadoria;
import com.fatec.livrariaonlinejpa.services.ClienteService;
import com.fatec.livrariaonlinejpa.services.ItemCompraService;
import com.fatec.livrariaonlinejpa.services.RetornoMercadoriaService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pedido")
public class PedidoController {
    private final RetornoMercadoriaService retornoMercadoriaService;
    private final ItemCompraService itemCompraService;
    private final ClienteService clienteService;

    @PostMapping("/troca/criar")
    private String novaTroca(HttpSession session, @ModelAttribute("troca") NovaTrocaDTO trocaDTO){
        RetornoMercadoria retornoMercadoria = retornoMercadoriaService.toTroca(trocaDTO);
        itemCompraService.registrarDevolucaoItem(retornoMercadoria.getItemCompra().getId(), retornoMercadoria.getQnt());
        retornoMercadoriaService.save(retornoMercadoria);
        return "redirect:/cliente/pedido/"+trocaDTO.getPedidoId();
    }

    @PostMapping("/devolucao/criar")
    private String novaDevolucao(HttpSession session, @ModelAttribute("troca") NovaTrocaDTO trocaDTO){
        RetornoMercadoria retornoMercadoria = retornoMercadoriaService.toDevolucao(trocaDTO);
        itemCompraService.registrarDevolucaoItem(retornoMercadoria.getItemCompra().getId(), retornoMercadoria.getQnt());
        retornoMercadoriaService.save(retornoMercadoria);
        return "redirect:/cliente/pedido/"+trocaDTO.getPedidoId();
    }

}
