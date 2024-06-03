package com.fatec.livrariaonlinejpa.repositories;

import com.fatec.livrariaonlinejpa.model.Pedido;
import com.fatec.livrariaonlinejpa.util.DataPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findPedidoByClienteId(long id);

    @Query(value = "CALL get_sales_of(:prdid,:inidate,:enddate);", nativeQuery = true)
    List<Object[]> callGetSales(@Param("prdid") Long prodId, @Param("inidate") Date iniDate, @Param("enddate") Date endDate);

    default List<DataPoint> getSalesReport(Long prodId, Date iniDate, Date endDate){
        List<Object[]> results = callGetSales(prodId, iniDate, endDate);
        return results.stream()
                .map(res -> new DataPoint((Date) res[0],((BigDecimal) res[1]).intValue()))
                .collect(Collectors.toList());
    }
}


