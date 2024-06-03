package com.fatec.livrariaonlinejpa.controller;

import com.fatec.livrariaonlinejpa.model.*;
import com.fatec.livrariaonlinejpa.repositories.PedidoRepository;
import com.fatec.livrariaonlinejpa.services.RetornoMercadoriaService;
import com.fatec.livrariaonlinejpa.util.DataPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final PedidoRepository repo;

    @GetMapping("/test")
    public String teste(){

        return "/teste";
    }
}
