package com.fatec.livrariaonlinejpa.services;

import com.fatec.livrariaonlinejpa.model.Cliente;
import com.fatec.livrariaonlinejpa.model.Cupom;
import com.fatec.livrariaonlinejpa.model.RetornoMercadoria;
import com.fatec.livrariaonlinejpa.model.TipoCupom;
import com.fatec.livrariaonlinejpa.repositories.CupomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CupomService {
    private final CupomRepository repo;

    public boolean validarCupom(Cupom cupom, long clientId) {
        if(! cupom.isAtivo()){
            return false;
        }
        if(cupom.getTipo().getTipoString().equals("troca")){
            if(cupom.getCliente() == null) return false;
            if(cupom.getCliente().getId() != clientId) return false;
        }

        return true;
    }

    public Cupom save(Cupom cupom){
        return repo.save(cupom);
    }

    public Cupom findById(long id){
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cupom not found"));
    }

    public Cupom findByNome(String nome){
        return repo.findByNome(nome);
    }

    public void delete(long id){
        if(repo.existsById(id)){
            repo.deleteById(id);
        }
    }

    public boolean existsByName(String nome){
        return repo.existsByNome(nome);
    }


    public void update(Cupom novoCupom){
        if(repo.existsById(novoCupom.getId())){
            repo.save(novoCupom);
        }
    }

    public Cupom gerarCupom(RetornoMercadoria retorno){
        Cupom cupom = new Cupom();
        cupom.setCliente(retorno.getPedido().getCliente());
        cupom.setNome("troca" + retorno.getId());
        cupom.setDesconto(retorno.getValor() * retorno.getQnt());
        cupom.setTipo(TipoCupom.troca);
        cupom = save(cupom);
        return cupom;
    }
}
