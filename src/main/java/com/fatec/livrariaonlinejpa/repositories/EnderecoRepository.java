package com.fatec.livrariaonlinejpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.livrariaonlinejpa.model.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco,Long>{
    
}
