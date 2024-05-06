package com.fatec.livrariaonlinejpa.repositories;

import com.fatec.livrariaonlinejpa.model.RetornoMercadoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RetornoMercadoriaRepository extends JpaRepository<RetornoMercadoria,Long> {


    List<RetornoMercadoria> findByPedidoId(long pedidoId);
}
