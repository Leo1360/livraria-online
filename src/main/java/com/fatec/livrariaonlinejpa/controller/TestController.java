package com.fatec.livrariaonlinejpa.controller;

import com.fatec.livrariaonlinejpa.model.Cartao;
import com.fatec.livrariaonlinejpa.model.Cliente;
import com.fatec.livrariaonlinejpa.model.Cupom;
import com.fatec.livrariaonlinejpa.model.TipoCupom;
import com.fatec.livrariaonlinejpa.repositories.ClienteRepository;
import com.fatec.livrariaonlinejpa.services.ClienteService;
import com.fatec.livrariaonlinejpa.services.CupomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final CupomService cupomService;
    private final ClienteRepository clienteRepository;

    @GetMapping("/test")
    public String teste(){
        Cupom cupom = new Cupom();
        cupom.setDesconto(50);
        cupom.setNome("troca50");
        cupom.setTipo(TipoCupom.troca);
        Cliente cliente = clienteRepository.getReferenceById(1l);
        cupom.setCliente(cliente);
        cupomService.save(cupom);
        return "/home";
    }
}
