package com.fatec.livrariaonlinejpa.util;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RelatorioVendas {
    private List<DataSet> relatorio = new ArrayList<>();

    public void addDataset(DataSet dataSet){
        this.relatorio.add(dataSet);

    }
}
