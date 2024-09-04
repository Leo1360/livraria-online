package com.fatec.livrariaonlinejpa.util;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fatec.livrariaonlinejpa.model.Produto;
import com.fatec.livrariaonlinejpa.model.ResumoVenda;
import com.fatec.livrariaonlinejpa.repositories.ResumoVendaRepository;
import com.fatec.livrariaonlinejpa.services.AdmService;
import com.fatec.livrariaonlinejpa.services.ProdutoService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ScheduledTask {
    private final AdmService admService;
    private final ProdutoService produtoService;
    private final ResumoVendaRepository resumoRepository;

//    @Scheduled(fixedRate = 500000)
    public void gerarRelatorioDiario(){
        List<Produto> produtos = produtoService.findAll();
//        Date yesterday = DateUtils.yesterday();
        LocalDate yesterday = LocalDate.of(2024,04,05);

        for(Produto prod: produtos){
            int qnt = admService.getDailyAmoutOfSales(prod.getId(), yesterday);
            ResumoVenda resumo = new ResumoVenda(prod,yesterday,qnt);
            resumoRepository.save(resumo);

        }
    }

}
