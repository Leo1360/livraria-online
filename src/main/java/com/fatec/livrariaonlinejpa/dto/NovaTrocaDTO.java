package com.fatec.livrariaonlinejpa.dto;

import lombok.Data;

@Data
public class NovaTrocaDTO {
    private long itemId;
    private long pedidoId;
    private double valorUnit;
    private int qnt;
    private String motivo;

}
