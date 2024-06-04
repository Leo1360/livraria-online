package com.fatec.livrariaonlinejpa.util;

import com.fatec.livrariaonlinejpa.model.Produto;
import com.fatec.livrariaonlinejpa.model.ResumoVenda;
import com.fatec.livrariaonlinejpa.repositories.ResumoVendaRepository;
import com.fatec.livrariaonlinejpa.services.AdmService;
import com.fatec.livrariaonlinejpa.services.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
        Date yesterday = DateUtils.dateOf("2024-05-27");
        for(Produto prod: produtos){
            int qnt = admService.getDailyAmoutOfSales(prod.getId(), yesterday);
            ResumoVenda resumo = new ResumoVenda(prod,yesterday,qnt);
            resumoRepository.save(resumo);

        }
    }

}
