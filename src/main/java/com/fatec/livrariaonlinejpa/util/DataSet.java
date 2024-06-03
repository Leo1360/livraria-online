package com.fatec.livrariaonlinejpa.util;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DataSet {
    private String label;
    private List<DataPoint> data = new ArrayList<>();

    @Override
    public String toString() {
        return "DataSet{" +
                "label='" + label + '\'' +
                ", series=" + data +
                '}';
    }
}
