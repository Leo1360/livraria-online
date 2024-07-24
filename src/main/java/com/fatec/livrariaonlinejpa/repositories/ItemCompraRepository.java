package com.fatec.livrariaonlinejpa.repositories;

import com.fatec.livrariaonlinejpa.model.ItemCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCompraRepository extends JpaRepository<ItemCompra,Long> {

}
