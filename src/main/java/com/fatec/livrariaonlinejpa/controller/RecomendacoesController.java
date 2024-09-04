package com.fatec.livrariaonlinejpa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fatec.livrariaonlinejpa.services.RecomendacoesService;
import com.fatec.livrariaonlinejpa.util.Message;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/recomendacao")
@RequiredArgsConstructor
public class RecomendacoesController {
    private final RecomendacoesService recomendacoesService;

    @GetMapping("/iniciar")
    public ResponseEntity<Message> initChat(HttpSession session){
        String respo = recomendacoesService.iniciarChat(session);
        Message msg = new Message();
        msg.setMsg(respo);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PostMapping("/sendMsg")
    public ResponseEntity<Message> sendMsg(HttpSession session, @RequestBody String menssagem){
        String respo = recomendacoesService.sendNewMsg(session,menssagem);
        Message msg = new Message();
        msg.setMsg(respo);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping("/show")
    public String show(HttpSession session){
        return "recomendacao";
    }

}
