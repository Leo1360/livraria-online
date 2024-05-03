package com.fatec.livrariaonlinejpa.model;

public enum TipoCupom {
    troca("troca"),promocao("promocao");

    private final String tipo;
    TipoCupom(String tipoCupom) {
        this.tipo = tipoCupom;
    }

    public String getTipo(){
        return  this.tipo;
    }
}
