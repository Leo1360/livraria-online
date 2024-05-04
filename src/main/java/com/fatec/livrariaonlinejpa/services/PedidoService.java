package com.fatec.livrariaonlinejpa.services;

import com.fatec.livrariaonlinejpa.model.ItemCompra;
import com.fatec.livrariaonlinejpa.model.Pagamento;
import com.fatec.livrariaonlinejpa.model.Pedido;
import com.fatec.livrariaonlinejpa.repositories.PedidoRepository;
import com.fatec.livrariaonlinejpa.util.ValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository repo;
    private final CupomService cupomService;

    public Pedido findById(long id){
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Pedido not found"));
    }

    public Pedido salvarNovoPedido(Pedido pedido){
        pedido.setData(LocalDate.now());
        pedido.setStatus("Aguardando Pagamento");
        double total = 0;
        for(ItemCompra item : pedido.getItens()){
            total += item.getValorUnit() * item.getQnt();
        }
        pedido.setSubTotal(total);
        return repo.save(pedido);
    }

    public void update(Pedido pedido){
        findById(pedido.getId());
        repo.save(pedido);
    }

    public List<Pedido> listarPedidosByCliente(long clienteId){
        return repo.findPedidoByClienteId(clienteId);
    }

    public ValidationResult validarPedido(Pedido pedido){
        if(pedido.getCupom() != null){
            if(! cupomService.validarCupom(pedido.getCupom(), pedido.getCliente().getId())){
                return new ValidationResult(false,"Este cupom n é valido para esse usuario");
            }
        }
        if(pedido.getEnderecoEntrega() == null) return new ValidationResult(false, "Informe um endereço válido");
        if(pedido.getItens().isEmpty()) return new ValidationResult(false, "O pedido deve ter ao menos um item");
        double totalPag = 0;
        for(Pagamento pagamento : pedido.getPagamentoList()){
               totalPag += pagamento.getValor()*100;
               if(pagamento.getCartao() == null) return new ValidationResult(false, "Houve um erro na seleção do cartão, tente selecionar outro e depois o atual novamente");
        }
        totalPag = totalPag/100;

        if(totalPag != pedido.getTotal()) return new ValidationResult(false, "O valor total nos cartões não corresponde ao total da compra.");

        return new ValidationResult(true,null);
    }

}
