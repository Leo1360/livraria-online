package com.fatec.livrariaonlinejpa.services;

import com.fatec.livrariaonlinejpa.model.Produto;
import com.fatec.livrariaonlinejpa.repositories.PedidoRepository;
import com.fatec.livrariaonlinejpa.repositories.ProdutoRepository;
import com.fatec.livrariaonlinejpa.util.DataSet;
import com.fatec.livrariaonlinejpa.util.RelatorioVendas;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdmService {
    private final PedidoService pedidoService;
    private final ProdutoService produtoService;
    private final PedidoRepository pedidoRepository;

    public RelatorioVendas getRelatorioVendas(Date iniDate, Date endDate){
        RelatorioVendas relatorio = new RelatorioVendas();
        List<Produto> produtos = produtoService.findAll();
        for(Produto p: produtos){
            DataSet dataSet = new DataSet();
            dataSet.setData(pedidoService.getSalesHistory(p.getId(),iniDate, endDate));
            System.out.println(dataSet);
            dataSet.setLabel(p.getNome());
            relatorio.getRelatorio().add(dataSet);
        }
        return relatorio;
    }

    public int getDailyAmoutOfSales(long prdId,Date date){
        return pedidoRepository.getSalesByDay(prdId,date);
    }
}
