package com.fatec.livrariaonlinejpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.livrariaonlinejpa.model.RetornoMercadoria;

@Repository
public interface RetornoMercadoriaRepository extends JpaRepository<RetornoMercadoria,Long> {


    List<RetornoMercadoria> findByPedidoId(long pedidoId);
}
