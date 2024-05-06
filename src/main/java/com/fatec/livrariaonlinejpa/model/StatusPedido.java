package com.fatec.livrariaonlinejpa.model;

public enum StatusPedido {
    EM_PROCESSAMENTO("Em Processamento"),PAGAMENTO_REALIZADO("Pagamento Realizado"),PAGAMENTO_RECUSADO("Pagamento Recusado"),PEDIDO_CANCELADO("Pedido Cancelado"),EM_TRANSPORTE("Em Transporte"),ENTREGUE("Entregue");
    private final String texto;


    StatusPedido(String texto) {
        this.texto = texto;
    }

    public String getTexto(){
        return this.texto;
    }

    public static StatusPedido valueOfLabel(String label){
        for(StatusPedido status : values()){
            if(status.texto == label){
                return status;
            }
        }
        return null;
    }
}
