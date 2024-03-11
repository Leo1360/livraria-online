package com.fatec.livrariaonlinejpa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Cartao{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String digitos;
    private String cvv;
    private String vencimento;
    private String cpf;
    private boolean preferencial;
}
