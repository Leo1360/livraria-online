package com.fatec.livrariaonlinejpa.controller;

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
        clienteService.setCartaoPreferencial(1,7);
        return "/home";
    }
}
