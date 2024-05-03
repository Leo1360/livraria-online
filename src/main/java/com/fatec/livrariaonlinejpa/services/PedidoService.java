package com.fatec.livrariaonlinejpa.services;

import com.fatec.livrariaonlinejpa.model.ItemCompra;
import com.fatec.livrariaonlinejpa.model.Pedido;
import com.fatec.livrariaonlinejpa.repositories.PedidoRepository;
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
        pedido.setTotal(total);
        return repo.save(pedido);
    }

    public void update(Pedido pedido){
        findById(pedido.getId());
        repo.save(pedido);
    }

    public List<Pedido> listarPedidosByCliente(long clienteId){
        return repo.findPedidoByClienteId(clienteId);
    }



}
