package com.fatec.livrariaonlinejpa.services;

import com.fatec.livrariaonlinejpa.model.Pedido;
import com.fatec.livrariaonlinejpa.model.Produto;
import com.fatec.livrariaonlinejpa.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository repo;

    public Produto findById(long id){
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Produto not found"));
    }

    public Produto salvar(Produto prod){
        return  repo.save(prod);
    }

    public void update(Produto prod){
        findById(prod.getId());
        repo.save(prod);
    }

    public List<Produto> pesquisar(String query){
        List<Produto> produtoList = repo.findByNomeOrDescricao(query.toLowerCase());
        if(produtoList == null){
            produtoList = new ArrayList<>();
        }
        return produtoList;
    }
}
