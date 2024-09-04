package com.fatec.livrariaonlinejpa.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fatec.livrariaonlinejpa.model.ItemCompra;
import com.fatec.livrariaonlinejpa.repositories.ItemCompraRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemCompraService {
    private final ItemCompraRepository repo;

    public ItemCompra findById(long id){
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Item Compra not found"));
    }

    public ItemCompra save(ItemCompra item){
        return repo.save(item);
    }

    public void update(ItemCompra item){
        findById(item.getId());
        repo.save(item);
    }

    public void delete(long id){
        repo.deleteById(id);
    }

    public void registrarDevolucaoItem(long id, int qnt){
        ItemCompra itemCompra = findById(id);
        itemCompra.setQntDevolvida(itemCompra.getQntDevolvida() + qnt);
        save(itemCompra);
    }
}
