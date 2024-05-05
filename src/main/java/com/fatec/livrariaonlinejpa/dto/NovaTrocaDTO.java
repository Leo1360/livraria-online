package com.fatec.livrariaonlinejpa.dto;

import com.fatec.livrariaonlinejpa.model.Troca;
import com.fatec.livrariaonlinejpa.services.ClienteService;
import com.fatec.livrariaonlinejpa.services.ItemCompraService;
import com.fatec.livrariaonlinejpa.services.TrocaService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Data
public class NovaTrocaDTO {
    private long itemId;
    private long clientId;
    private double valorUnit;
    private int qnt;
    private String motivo;

}
