package com.fatec.livrariaonlinejpa.model;

public enum TipoRetornoMercadoria {
    TROCA("Troca"), DEVOLUCAO("Devolução");

    private final String nome;

    TipoRetornoMercadoria(String nome) {
        this.nome = nome;
    }
}
