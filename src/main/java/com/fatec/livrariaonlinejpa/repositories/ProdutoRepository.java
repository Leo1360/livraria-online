package com.fatec.livrariaonlinejpa.repositories;

import com.fatec.livrariaonlinejpa.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto,Long> {
}
