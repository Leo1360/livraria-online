package com.fatec.livrariaonlinejpa.services;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fatec.livrariaonlinejpa.model.Produto;
import com.fatec.livrariaonlinejpa.model.ResumoVenda;
import com.fatec.livrariaonlinejpa.repositories.ResumoVendaRepository;
import com.fatec.livrariaonlinejpa.util.DataPoint;
import com.fatec.livrariaonlinejpa.util.DataSet;
import com.fatec.livrariaonlinejpa.util.RelatorioVendas;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResumoVendasService {
    private final ResumoVendaRepository repo;
    private final ProdutoService produtoService;

    private List<List<ResumoVenda>> getesumoVendasPeriodo(LocalDate iniDate, LocalDate endDate){
        List<Produto> produtos = produtoService.findAll();
        List<ResumoVenda> ret = repo.getResumoVendaByDateIsBetweenOrderByDate(iniDate,endDate);
        return produtos.stream()
                .map(p -> ret.stream()
                        .filter(r -> r.getProduto().getId() == p.getId())
                        .toList()
                )
                .toList();
    }

    public RelatorioVendas getRelatVenda(LocalDate iniDate, LocalDate endDate){
        List<List<ResumoVenda>> ret = getesumoVendasPeriodo(iniDate,endDate);
        RelatorioVendas relatorio = new RelatorioVendas();
        for(List<ResumoVenda> list : ret){
            List<DataPoint> dpList = list.stream().map(r -> new DataPoint(r.getDate().toString(),r.getQntVendas())).toList();
            String label = list.get(0).getProduto().getNome();
            relatorio.addDataset(new DataSet(label, dpList));
        }
        return relatorio;

    }

    public RelatorioVendas getRelatAgrupado(LocalDate iniDate, LocalDate endDate, String tipoAgrupamento){
        List<List<ResumoVenda>> relat = getesumoVendasPeriodo(iniDate,endDate);

        if(tipoAgrupamento.equalsIgnoreCase("s")){
            return agruparSemanalmente(relat);
        }
        if(tipoAgrupamento.equalsIgnoreCase("m")){
            return agruparMensalmente(relat);
        }
        return new RelatorioVendas();

    }

    private RelatorioVendas agruparSemanalmente(List<List<ResumoVenda>> relat) {
        RelatorioVendas relatorioVendas = new RelatorioVendas();
        for(List<ResumoVenda> vendaList: relat) {
            int desloc = vendaList.get(0).getDate().get(ChronoField.ALIGNED_WEEK_OF_YEAR);
            String label = vendaList.get(0).getProduto().getNome();
            List<DataPoint> dataPointList = new ArrayList<>();
            for (ResumoVenda resumo : vendaList) {
                int weekNumber = resumo.getDate().get(ChronoField.ALIGNED_WEEK_OF_YEAR);
                if (dataPointList.size() - 1 < (weekNumber - desloc)) {
                    dataPointList.add(new DataPoint("Week" + weekNumber, resumo.getQntVendas()));
                    continue;
                }
                dataPointList.get(weekNumber - desloc).addY(resumo.getQntVendas());
            }
            relatorioVendas.addDataset(new DataSet(label,dataPointList));
        }

        return relatorioVendas;
    }

    private RelatorioVendas agruparMensalmente(List<List<ResumoVenda>> relat){
        RelatorioVendas relatorioVendas = new RelatorioVendas();
        for(List<ResumoVenda> vendaList: relat) {
            int desloc = vendaList.get(0).getDate().getMonthValue();
            String label = vendaList.get(0).getProduto().getNome();
            List<DataPoint> dataPointList = new ArrayList<>();
            for (ResumoVenda resumo : vendaList) {
                int monthValue = resumo.getDate().getMonthValue();
                if (dataPointList.size() - 1 < (monthValue - desloc)) {
                    dataPointList.add(new DataPoint("Month-" + monthValue, resumo.getQntVendas()));
                    continue;
                }
                dataPointList.get(monthValue - desloc).addY(resumo.getQntVendas());
            }
            relatorioVendas.addDataset(new DataSet(label,dataPointList));
        }

        return relatorioVendas;
    }
}
