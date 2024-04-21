package com.fatec.livrariaonlinejpa.services;

import com.fatec.livrariaonlinejpa.model.Troca;
import com.fatec.livrariaonlinejpa.repositories.TrocaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrocaService {
    private final TrocaRepository repo;

    public Troca save(Troca troca){

        return  repo.save(troca);
    }

}
