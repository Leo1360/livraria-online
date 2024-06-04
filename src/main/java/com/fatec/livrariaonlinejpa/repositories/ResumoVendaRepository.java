package com.fatec.livrariaonlinejpa.repositories;

import com.fatec.livrariaonlinejpa.model.ResumoVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ResumoVendaRepository extends JpaRepository<ResumoVenda,Long> {

    List<ResumoVenda> getResumoVendaByDateIsBetween(Date iniDate, Date endDate);

}
