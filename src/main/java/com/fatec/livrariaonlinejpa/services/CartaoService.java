package com.fatec.livrariaonlinejpa.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fatec.livrariaonlinejpa.model.Cartao;
import com.fatec.livrariaonlinejpa.repositories.CartaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartaoService {
    private final  CartaoRepository repo;

    public Cartao findById(long id) {
        return repo.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cartao not found"));

    }

    public Cartao save(Cartao cartao){
        return repo.save(cartao);
    }

    public void update(Cartao novoCartao){
        Cartao antigoCartao = findById(novoCartao.getId());
        if(novoCartao.getCpf().isBlank()){antigoCartao.setCpf(novoCartao.getCpf());}
        if(novoCartao.getCvv().isBlank()){antigoCartao.setCvv(novoCartao.getCvv());}
        if(novoCartao.getDigitos().isBlank()){antigoCartao.setDigitos(novoCartao.getDigitos());}
        if(novoCartao.getVencimento().isBlank()){antigoCartao.setVencimento(novoCartao.getVencimento());}

        repo.save(antigoCartao);
    }

    public void delete(long id){
        if (!repo.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado");
        }
        repo.deleteById(id);
    }
    
}
