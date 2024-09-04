package com.fatec.livrariaonlinejpa.util;

import lombok.Data;

@Data
public class DataPoint {
    private String x;
    private int y;

    public DataPoint() {
    }

    public DataPoint(String x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "DataPoint{" +
                "data=" + x +
                ", valor=" + y +
                '}';
    }

    public void addY(int val){
        this.y += val;
    }
}
