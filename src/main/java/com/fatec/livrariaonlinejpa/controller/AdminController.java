package com.fatec.livrariaonlinejpa.controller;

import com.fatec.livrariaonlinejpa.model.Pedido;
import com.fatec.livrariaonlinejpa.model.StatusPedido;
import com.fatec.livrariaonlinejpa.model.RetornoMercadoria;
import com.fatec.livrariaonlinejpa.services.PedidoService;
import com.fatec.livrariaonlinejpa.services.RetornoMercadoriaService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/adm")
public class AdminController {
    private final PedidoService pedidoService;
    private final RetornoMercadoriaService retornoMercadoriaService;

    @GetMapping("/perfil")
    public String home(HttpSession session){
        return "/admin/admin_perfil";
    }

    @GetMapping("/pedidos")
    public String listarPedidos(HttpSession session, Model model){
        model.addAttribute("pedidos", pedidoService.findAll());
        return "admin/admin_pedidos";
    }

    @PostMapping("/pedido/mudarStatus")
    public String mudarStatus(HttpSession session, @RequestParam("pedidoId") long id, @RequestParam("novoStatus") String status){
        Pedido pedido = pedidoService.findById(id);
        pedido.setStatus(StatusPedido.valueOf(status));
        pedidoService.update(pedido);
        return "redirect:/adm/pedidos";
    }

    @GetMapping("/pedido/{id}")
    public String showPedido(HttpSession session, Model model, @PathVariable long id){
        List<RetornoMercadoria> retornoMercadoriaList = retornoMercadoriaService.listTrocasPedido(id);
        model.addAttribute("trocas", retornoMercadoriaList);
        model.addAttribute("pedido",pedidoService.findById(id));
        return "/admin/admin_pedido";
    }

    @GetMapping("/aprovarRetorno/{id}")
    public String aprovarRetorno(HttpSession session, @PathVariable long id){
        retornoMercadoriaService.aprovar(id);
        return "redirect:/adm/pedidos";
    }

    @GetMapping("/reprovarRetorno/{id}")
    public String reprovarRetorno(HttpSession session, @PathVariable long id){
        retornoMercadoriaService.reprovar(id);
        return "redirect:/adm/pedidos";
    }
}
