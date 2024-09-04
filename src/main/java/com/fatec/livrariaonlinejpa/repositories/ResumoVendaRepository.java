package com.fatec.livrariaonlinejpa.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.livrariaonlinejpa.model.ResumoVenda;

@Repository
public interface ResumoVendaRepository extends JpaRepository<ResumoVenda,Long> {

    List<ResumoVenda> getResumoVendaByDateIsBetweenOrderByDate(LocalDate iniDate, LocalDate endDate);

}
