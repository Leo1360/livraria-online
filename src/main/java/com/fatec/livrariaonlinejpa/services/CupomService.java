package com.fatec.livrariaonlinejpa.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fatec.livrariaonlinejpa.model.Cupom;
import com.fatec.livrariaonlinejpa.model.RetornoMercadoria;
import com.fatec.livrariaonlinejpa.model.TipoCupom;
import com.fatec.livrariaonlinejpa.repositories.CupomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CupomService {
    private final CupomRepository repo;

    public Cupom getReferenceById(long id){
        return repo.getReferenceById(id);
    }

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

    public List<Cupom> findAll(){
        return repo.findAll();
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
        cupom.setDesconto(retorno.getValor().multiply(new BigDecimal(retorno.getQnt())));
        cupom.setTipo(TipoCupom.troca);
        cupom = save(cupom);
        return cupom;
    }

    public Cupom gerarCupom(String nome, double desconto){
        Cupom cupom =  new Cupom();
        cupom.setNome(nome);
        cupom.setDesconto(new BigDecimal(desconto));
        cupom.setTipo(TipoCupom.promocao);
        return save(cupom);
    }

    public List<Cupom> listByClienteId(long id){
        return repo.findByCliente_Id(id);
    }


}
