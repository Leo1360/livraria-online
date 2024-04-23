package com.fatec.livrariaonlinejpa.controller;

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

@Controller
@RequiredArgsConstructor
public class PedidoController {
    private final TrocaService trocaService;
    private final ItemCompraService itemCompraService;
    private final ClienteService clienteService;

    @PostMapping("/troca/nova")
    private String novaTroca(HttpSession session, @ModelAttribute("troca") Troca troca){
        ItemCompra item = itemCompraService.findById(troca.getItemCompra().getId());
        Cliente cliente = clienteService.findById(troca.getCliente().getId());
        troca.setCliente(cliente);
        troca.setItemCompra(item);
        trocaService.save(troca);
        return "";
    }


}
