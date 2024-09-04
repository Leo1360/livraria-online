package com.fatec.livrariaonlinejpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.livrariaonlinejpa.model.Cupom;

@Repository
public interface CupomRepository extends JpaRepository<Cupom, Long> {

    boolean existsByNome(String nome);
    Cupom findByNome(String nome);

    List<Cupom> findByCliente_Id(long id);

}
