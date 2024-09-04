package com.fatec.livrariaonlinejpa.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fatec.livrariaonlinejpa.dto.NovaTrocaDTO;
import com.fatec.livrariaonlinejpa.model.Cupom;
import com.fatec.livrariaonlinejpa.model.RetornoMercadoria;
import com.fatec.livrariaonlinejpa.model.StatusPedido;
import com.fatec.livrariaonlinejpa.model.StatusRetMercadoria;
import com.fatec.livrariaonlinejpa.model.TipoRetornoMercadoria;
import com.fatec.livrariaonlinejpa.repositories.ItemCompraRepository;
import com.fatec.livrariaonlinejpa.repositories.PedidoRepository;
import com.fatec.livrariaonlinejpa.repositories.RetornoMercadoriaRepository;

import lombok.RequiredArgsConstructor;

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
        retornoMercadoria.setValor(new BigDecimal(trocaDTO.getValorUnit()));
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
        retornoMercadoria.setValor(new BigDecimal(trocaDTO.getValorUnit()));
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
            retorno.setStatus(StatusRetMercadoria.APROVADO);
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

    public void sinalizarEnvio(long id){
        RetornoMercadoria retorno = repo.findById(id).orElse(null);
        if(retorno != null){
            retorno.setStatus(StatusRetMercadoria.PRODUTOS_ENVIADOS);
            repo.save(retorno);
        }
    }

    public void sinalizarRecebimento(long id){
        RetornoMercadoria retorno = repo.findById(id).orElse(null);
        if(retorno != null) {
            if(retorno.getPedido().qntItensRestantes() == 0){
                retorno.getPedido().setStatus(StatusPedido.ENTREGUE);
            }
            Cupom cupom = cupomService.gerarCupom(retorno);

            retorno.setCupom(cupomService.getReferenceById(cupom.getId()));


            retorno.setStatus(StatusRetMercadoria.PRODUTOS_RECEBIDOS);
            repo.save(retorno);
        }
    }

    public List<RetornoMercadoria> findByPedidId(long id){
        return repo.findByPedidoId(id);
    }
}
