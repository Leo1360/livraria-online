package com.fatec.livrariaonlinejpa.services;

import com.fatec.livrariaonlinejpa.dto.NovaTrocaDTO;
import com.fatec.livrariaonlinejpa.model.Troca;
import com.fatec.livrariaonlinejpa.repositories.ClienteRepository;
import com.fatec.livrariaonlinejpa.repositories.ItemCompraRepository;
import com.fatec.livrariaonlinejpa.repositories.TrocaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrocaService {
    private final TrocaRepository repo;
    private final ClienteRepository clienteRepository;
    private final ItemCompraRepository itemCompraRepository;

    public Troca save(Troca troca){

        return  repo.save(troca);
    }

    public Troca toTroca(NovaTrocaDTO trocaDTO){
        Troca troca =  new Troca();
        troca.setCliente(clienteRepository.getReferenceById(trocaDTO.getClientId()));
        troca.setItemCompra(itemCompraRepository.getReferenceById(trocaDTO.getItemId()));
        troca.setQnt(trocaDTO.getQnt());
        troca.setValor(trocaDTO.getValorUnit());
        troca.setMotivo(trocaDTO.getMotivo());
        return troca;
    }

}
