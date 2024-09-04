package com.fatec.livrariaonlinejpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.livrariaonlinejpa.model.ItemCompra;

@Repository
public interface ItemCompraRepository extends JpaRepository<ItemCompra,Long> {

}
