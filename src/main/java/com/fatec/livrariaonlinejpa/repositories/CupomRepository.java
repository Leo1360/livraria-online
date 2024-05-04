package com.fatec.livrariaonlinejpa.repositories;

import com.fatec.livrariaonlinejpa.model.Cupom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CupomRepository extends JpaRepository<Cupom, Long> {

    boolean existsByNome(String nome);
    Cupom findByNome(String nome);

}
