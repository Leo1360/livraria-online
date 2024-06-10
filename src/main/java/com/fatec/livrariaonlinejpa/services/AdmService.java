package com.fatec.livrariaonlinejpa.services;

import com.fatec.livrariaonlinejpa.model.Produto;
import com.fatec.livrariaonlinejpa.repositories.PedidoRepository;
import com.fatec.livrariaonlinejpa.repositories.ProdutoRepository;
import com.fatec.livrariaonlinejpa.util.DataSet;
import com.fatec.livrariaonlinejpa.util.RelatorioVendas;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
