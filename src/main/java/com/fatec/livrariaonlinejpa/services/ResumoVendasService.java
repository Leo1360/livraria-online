package com.fatec.livrariaonlinejpa.services;

import com.fatec.livrariaonlinejpa.model.Produto;
import com.fatec.livrariaonlinejpa.model.ResumoVenda;
import com.fatec.livrariaonlinejpa.repositories.ResumoVendaRepository;
import com.fatec.livrariaonlinejpa.util.DataPoint;
import com.fatec.livrariaonlinejpa.util.DataSet;
import com.fatec.livrariaonlinejpa.util.RelatorioVendas;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumoVendasService {
    private final ResumoVendaRepository repo;
    private final ProdutoService produtoService;

    public List<List<ResumoVenda>> getesumoVendasPeriodo(Date iniDate, Date endDate){
        List<Produto> produtos = produtoService.findAll();
        List<ResumoVenda> ret = repo.getResumoVendaByDateIsBetween(iniDate,endDate);
        List<List<ResumoVenda>> relatorio = produtos.stream()
                .map(p -> ret.stream()
                        .filter(r -> r.getProduto().getId() == p.getId())
                        .toList()
                )
                .toList();

        return relatorio;
    }

    public RelatorioVendas getRelatVenda(Date iniDate, Date endDate){
        List<List<ResumoVenda>> ret = getesumoVendasPeriodo(iniDate,endDate);
        RelatorioVendas relatorio = new RelatorioVendas();
        for(List<ResumoVenda> list : ret){
            List<DataPoint> dpList = list.stream().map(r -> new DataPoint(r.getDate(),r.getQntVendas())).toList();
            String label = list.get(0).getProduto().getNome();
            relatorio.addDataset(new DataSet(label, dpList));
        }
        return relatorio;

    }

}
