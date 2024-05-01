package com.fatec.livrariaonlinejpa.controller;

import com.fatec.livrariaonlinejpa.model.Cartao;
import com.fatec.livrariaonlinejpa.services.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final ClienteService clienteService;
    @GetMapping("/test")
    public String teste(){
        Cartao cartao = new Cartao();
        cartao.setDigitos("123456123");
        cartao.setCpf("12312312312");
        cartao.setCvv("123");
        cartao.setVencimento("04/28");
        clienteService.addCartao(1,cartao);
        return "/home";
    }
}
