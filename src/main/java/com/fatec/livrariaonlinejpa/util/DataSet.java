package com.fatec.livrariaonlinejpa.util;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
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

    public DataSet() {
    }

    public DataSet(String label, List<DataPoint> data) {
        this.label = label;
        this.data = data;
    }

    public DataSet addDataPoint(DataPoint dp){
        this.data.add(dp);
        return this;
    }

    public String getLabel() {
        return label;
    }

    public DataSet setLabel(String label) {
        this.label = label;
        return this;
    }

    public List<DataPoint> getData() {
        return data;
    }

    public DataSet setData(List<DataPoint> data) {
        this.data = data;
        return this;
    }
}
