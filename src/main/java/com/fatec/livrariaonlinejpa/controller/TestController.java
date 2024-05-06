package com.fatec.livrariaonlinejpa.controller;

import com.fatec.livrariaonlinejpa.model.*;
import com.fatec.livrariaonlinejpa.services.RetornoMercadoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final RetornoMercadoriaService retornoMercadoriaService;

    @GetMapping("/test")
    public String teste(){
        List<RetornoMercadoria> retornoMercadoriaList = retornoMercadoriaService.listTrocasPedido(12);
        System.out.println(retornoMercadoriaList);
        return "/home";
    }
}
