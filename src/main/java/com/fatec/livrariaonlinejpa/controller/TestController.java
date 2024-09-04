package com.fatec.livrariaonlinejpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.fatec.livrariaonlinejpa.util.ScheduledTask;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final ScheduledTask task;

    @GetMapping("/test")
    public String teste(HttpSession session){
        task.gerarRelatorioDiario();
        return "/teste";
    }
}
