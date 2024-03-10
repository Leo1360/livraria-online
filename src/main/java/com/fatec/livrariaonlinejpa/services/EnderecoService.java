package com.fatec.livrariaonlinejpa.services;

import org.springframework.stereotype.Service;

import com.fatec.livrariaonlinejpa.repositories.EnderecoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnderecoService {
private final EnderecoRepository repo;

    public void delete(long id){
        repo.deleteById(id);
    }
    

}
