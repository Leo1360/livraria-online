package com.fatec.livrariaonlinejpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.livrariaonlinejpa.model.Cartao;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long>{
    
}
