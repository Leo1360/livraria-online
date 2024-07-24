package com.fatec.livrariaonlinejpa.repositories;

import com.fatec.livrariaonlinejpa.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto,Long> {
    //@Query("SELECT p FROM Produto p where lower(CONCAT(p.nome, p.descricao)) like '%?1%'")
    @Query("select p from Produto p where lower(CONCAT(p.descricao,p.nome)) like concat('%',:query,'%')  ")
    List<Produto> findByNomeOrDescricao(@Param("query") String query);
}
