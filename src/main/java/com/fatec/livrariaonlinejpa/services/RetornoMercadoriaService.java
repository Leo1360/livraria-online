package com.fatec.livrariaonlinejpa.services;

import com.fatec.livrariaonlinejpa.dto.NovaTrocaDTO;
import com.fatec.livrariaonlinejpa.model.*;
import com.fatec.livrariaonlinejpa.repositories.ClienteRepository;
import com.fatec.livrariaonlinejpa.repositories.ItemCompraRepository;
import com.fatec.livrariaonlinejpa.repositories.PedidoRepository;
import com.fatec.livrariaonlinejpa.repositories.RetornoMercadoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetornoMercadoriaService {
    private final RetornoMercadoriaRepository repo;
    private final ItemCompraRepository itemCompraRepository;
    private final PedidoRepository pedidoRepository;
    private final CupomService cupomService;

    public RetornoMercadoria save(RetornoMercadoria retornoMercadoria){

        return  repo.save(retornoMercadoria);
    }

    public RetornoMercadoria toTroca(NovaTrocaDTO trocaDTO){
        RetornoMercadoria retornoMercadoria =  new RetornoMercadoria();
        retornoMercadoria.setItemCompra(itemCompraRepository.getReferenceById(trocaDTO.getItemId()));
        retornoMercadoria.setPedido(pedidoRepository.getReferenceById(trocaDTO.getPedidoId()));
        retornoMercadoria.setQnt(trocaDTO.getQnt());
        retornoMercadoria.setValor(trocaDTO.getValorUnit());
        retornoMercadoria.setMotivo(trocaDTO.getMotivo());
        retornoMercadoria.setTipo(TipoRetornoMercadoria.TROCA);
        retornoMercadoria.setStatus(StatusRetMercadoria.AGUARDANDO_ANALISE);
        return retornoMercadoria;
    }

    public RetornoMercadoria toDevolucao(NovaTrocaDTO trocaDTO){
        RetornoMercadoria retornoMercadoria =  new RetornoMercadoria();
        retornoMercadoria.setItemCompra(itemCompraRepository.getReferenceById(trocaDTO.getItemId()));
        retornoMercadoria.setPedido(pedidoRepository.getReferenceById(trocaDTO.getPedidoId()));
        retornoMercadoria.setQnt(trocaDTO.getQnt());
        retornoMercadoria.setValor(trocaDTO.getValorUnit());
        retornoMercadoria.setMotivo(trocaDTO.getMotivo());
        retornoMercadoria.setTipo(TipoRetornoMercadoria.DEVOLUCAO);
        retornoMercadoria.setStatus(StatusRetMercadoria.AGUARDANDO_ANALISE);
        return retornoMercadoria;
    }

    public List<RetornoMercadoria> listTrocasPedido(long pedidoId){
        return repo.findByPedidoId(pedidoId);
    }

    public void aprovar(long id) {
        RetornoMercadoria retorno = repo.findById(id).orElse(null);
        if(retorno != null) {
            Cupom cupom = new Cupom();
            cupom.setCliente(retorno.getPedido().getCliente());
            cupom.setNome("troca"+retorno.getId());
            cupom.setDesconto(retorno.getValor() * retorno.getQnt());
            cupom.setTipo(TipoCupom.troca);
            cupom = cupomService.save(cupom);

            retorno.setStatus(StatusRetMercadoria.APROVADO);
            retorno.setCupom(cupom);
            repo.save(retorno);
        }
    }

    public void reprovar(long id) {
        RetornoMercadoria retorno = repo.findById(id).orElse(null);
        if(retorno != null){
            retorno.getItemCompra().setQntDevolvida(retorno.getItemCompra().getQntDevolvida() - retorno.getQnt());
            retorno.setStatus(StatusRetMercadoria.REPROVADO);
            repo.save(retorno);
        }
    }
}
