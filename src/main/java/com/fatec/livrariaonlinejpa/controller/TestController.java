package com.fatec.livrariaonlinejpa.controller;

import com.fatec.livrariaonlinejpa.model.*;
import com.fatec.livrariaonlinejpa.repositories.PedidoRepository;
import com.fatec.livrariaonlinejpa.services.ResumoVendasService;
import com.fatec.livrariaonlinejpa.services.RetornoMercadoriaService;
import com.fatec.livrariaonlinejpa.util.DataPoint;
import com.fatec.livrariaonlinejpa.util.DateUtils;
import com.fatec.livrariaonlinejpa.util.RelatorioVendas;
import com.fatec.livrariaonlinejpa.util.ScheduledTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final ResumoVendasService service;

    @GetMapping("/test")
    public String teste(){

        return "/teste";
    }
}
