package com.fatec.livrariaonlinejpa.controller;

import com.fatec.livrariaonlinejpa.dto.NovaTrocaDTO;
import com.fatec.livrariaonlinejpa.model.Pedido;
import com.fatec.livrariaonlinejpa.model.RetornoMercadoria;
import com.fatec.livrariaonlinejpa.services.ClienteService;
import com.fatec.livrariaonlinejpa.services.ItemCompraService;
import com.fatec.livrariaonlinejpa.services.PedidoService;
import com.fatec.livrariaonlinejpa.services.RetornoMercadoriaService;
import com.fatec.livrariaonlinejpa.util.ValidationResult;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pedido")
public class PedidoController {
    private final RetornoMercadoriaService retornoMercadoriaService;
    private final ItemCompraService itemCompraService;
    private final PedidoService pedidoService;

    @GetMapping("/cliente")
    public String listPedidosCliente(HttpSession session,Model model){
        long id = (Long) session.getAttribute("clienteId");
        List<Pedido> pedidos = pedidoService.listarPedidosByCliente(id);
        model.addAttribute("pedidos", pedidos);
        return "/cliente/pedidos";
    }

    @GetMapping("/{id}")
    public String getDetalhesPedido(HttpSession session,@PathVariable Long id, Model model){
        Pedido pedido = pedidoService.findById(id);
        List<RetornoMercadoria> retornos = retornoMercadoriaService.findByPedidId(id);
        model.addAttribute("pedido", pedido);
        model.addAttribute("trocas", retornos);
        return "/cliente/pedido";

    }

    @PostMapping("/troca/criar")
    private String novaTroca(HttpSession session, @ModelAttribute("troca") NovaTrocaDTO trocaDTO){
        RetornoMercadoria retornoMercadoria = retornoMercadoriaService.toTroca(trocaDTO);
        itemCompraService.registrarDevolucaoItem(retornoMercadoria.getItemCompra().getId(), retornoMercadoria.getQnt());
        retornoMercadoriaService.save(retornoMercadoria);
        return "redirect:/pedido/"+trocaDTO.getPedidoId();
    }

    @PostMapping("/devolucao/criar")
    private String novaDevolucao(HttpSession session, @ModelAttribute("troca") NovaTrocaDTO trocaDTO){
        RetornoMercadoria retornoMercadoria = retornoMercadoriaService.toDevolucao(trocaDTO);
        itemCompraService.registrarDevolucaoItem(retornoMercadoria.getItemCompra().getId(), retornoMercadoria.getQnt());
        retornoMercadoriaService.save(retornoMercadoria);
        return "redirect:/pedido/"+trocaDTO.getPedidoId();
    }

    @GetMapping("/sinalizarEnvioItemRetornado/{id}")
    public String sinalizarRecebimento(HttpSession session, @PathVariable long id, @RequestParam("pedidoId") long pedidoId){
        retornoMercadoriaService.sinalizarEnvio(id);
        return "redirect:/pedido/" + pedidoId;
    }

    @PostMapping("/cancelar")
    public String cancelarPedido(HttpSession session, @RequestParam("id") long id){
        Pedido pedido = pedidoService.findById(id);
        ValidationResult result = pedidoService.validarCancelamento(pedido);
        if(! result.isValid()){
            session.setAttribute("alert", result.getErrorText());
            return"redirect:/pedido/" + id;
        }
        pedidoService.cancelarPedido(pedido);
        return "redirect:/pedido/cliente";
    }

}
