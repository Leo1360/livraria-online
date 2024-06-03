package com.fatec.livrariaonlinejpa.util;

import lombok.Data;

import java.util.Date;

@Data
public class DataPoint {
    private Date x;
    private int y;

    public DataPoint() {
    }

    public DataPoint(Date x, int y) {
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
}
