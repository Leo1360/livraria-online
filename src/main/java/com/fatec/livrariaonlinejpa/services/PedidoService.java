package com.fatec.livrariaonlinejpa.services;

import com.fatec.livrariaonlinejpa.model.Pedido;
import com.fatec.livrariaonlinejpa.repositories.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository repo;

    public Pedido findById(long id){
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Pedido not found"));
    }

    public Pedido salvarPedido(Pedido pedido){
        return  repo.save(pedido);
    }

    public void update(Pedido pedido){
        findById(pedido.getId());
        repo.save(pedido);
    }


}
