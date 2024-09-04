package com.fatec.livrariaonlinejpa.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fatec.livrariaonlinejpa.model.Cupom;
import com.fatec.livrariaonlinejpa.model.ItemCompra;
import com.fatec.livrariaonlinejpa.model.Pagamento;
import com.fatec.livrariaonlinejpa.model.Pedido;
import com.fatec.livrariaonlinejpa.model.RetornoMercadoria;
import com.fatec.livrariaonlinejpa.model.StatusPedido;
import com.fatec.livrariaonlinejpa.model.StatusRetMercadoria;
import com.fatec.livrariaonlinejpa.model.TipoCupom;
import com.fatec.livrariaonlinejpa.model.TipoRetornoMercadoria;
import com.fatec.livrariaonlinejpa.repositories.PedidoRepository;
import com.fatec.livrariaonlinejpa.util.ValidationResult;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository repo;
    private final CupomService cupomService;
    private final RetornoMercadoriaService retornoMercadoriaService;

    public Pedido findById(long id){
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Pedido not found"));
    }

    public List<Pedido> findAll(){
        return repo.findAll();
    }

    public Pedido salvarNovoPedido(Pedido pedido){
        pedido.setData(LocalDate.now());
        pedido.setStatus(StatusPedido.EM_PROCESSAMENTO);
        if(! pedido.getCupons().isEmpty()){
            for(Cupom cupom: pedido.getCupons()){
                if(cupom.getTipo() == TipoCupom.troca){
                    cupom.setAtivo(false);
                }
            }
        }
        return repo.save(pedido);
    }

    public void update(Pedido pedido){
        findById(pedido.getId());
        repo.save(pedido);
    }

    public List<Pedido> listarPedidosByCliente(long clienteId){
        return repo.findPedidoByClienteId(clienteId);
    }

    public void cancelarPedido(Pedido pedido){
        for(ItemCompra item : pedido.getItens()){
            item.setQntDevolvida(item.getQnt());
        }
        pedido.setStatus(StatusPedido.PEDIDO_CANCELADO);
        RetornoMercadoria retornoMercadoria = new RetornoMercadoria();
        retornoMercadoria.setPedido(pedido);
        retornoMercadoria.setTipo(TipoRetornoMercadoria.TROCA);
        retornoMercadoria.setMotivo("CANCELAMENTO DE PEDIDO");
        retornoMercadoria.setValor(pedido.getTotal());
        retornoMercadoria.setQnt(1);
        retornoMercadoria.setStatus(StatusRetMercadoria.PRODUTOS_RECEBIDOS);
        retornoMercadoria = retornoMercadoriaService.save(retornoMercadoria);
        Cupom cupom = cupomService.gerarCupom(retornoMercadoria);
        retornoMercadoria.setCupom(cupom);
        retornoMercadoriaService.save(retornoMercadoria);
    }

    public ValidationResult validarCancelamento(Pedido pedido) {
        if(pedido.getStatus() != StatusPedido.EM_PROCESSAMENTO && pedido.getStatus() != StatusPedido.PAGAMENTO_REALIZADO){
            return new ValidationResult(false,"Não é possivel cancelar o pedido, ele ja saiu para entrega. Solicite Devolução após o mesmo chegar");

        }
        if(! retornoMercadoriaService.findByPedidId(pedido.getId()).isEmpty()){
            return new ValidationResult(false, "Não é possivel cancelar o pedido inteiro pois ja existem solicitações para itens especificos. Solicite undividualmente");
        }
        return new ValidationResult(true,"");
    }

    public ValidationResult validarPedido(Pedido pedido){
        if(! pedido.getCupons().isEmpty()){
            Boolean areCuponsValid = pedido.getCupons().stream().map(cupom -> cupomService.validarCupom(cupom, pedido.getCliente().getId())).reduce((aBoolean, aBoolean2) -> aBoolean && aBoolean2).orElse(true);
            if(! areCuponsValid){
                return new ValidationResult(false,"Cupom invalido");
            }
        }
        if(pedido.getEnderecoEntrega() == null) return new ValidationResult(false, "Informe um endereço válido");
        if(pedido.getItens().isEmpty()) return new ValidationResult(false, "O pedido deve ter ao menos um item");
        BigDecimal totalPag = new BigDecimal(0);
        for(Pagamento pagamento : pedido.getPagamentoList()){
            totalPag = totalPag.add(pagamento.getValor());
            if(pagamento.getCartao() == null) return new ValidationResult(false, "Houve um erro na seleção do cartão, tente selecionar outro e depois o atual novamente");
        }

        BigDecimal total = pedido.getTotal();
        if(totalPag.toString() == total.toString()) return new ValidationResult(false, "O valor total nos cartões não corresponde ao total da compra.");

        return new ValidationResult(true,null);
    }




}
