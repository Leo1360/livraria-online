package com.fatec.livrariaonlinejpa.services;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.fatec.livrariaonlinejpa.repositories.PedidoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdmService {
    private final PedidoService pedidoService;
    private final ProdutoService produtoService;
    private final PedidoRepository pedidoRepository;

    public int getDailyAmoutOfSales(long prdId, LocalDate date){
        return pedidoRepository.getSalesByDay(prdId,date);
    }
}
