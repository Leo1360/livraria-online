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
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findPedidoByClienteId(long id);

    @Query(value = "CALL get_sales_of(:prdid,:inidate,:enddate);", nativeQuery = true)
    List<Object[]> callGetSales(@Param("prdid") Long prodId, @Param("inidate") Date iniDate, @Param("enddate") Date endDate);

    @Query(value = "CALL get_product_sales_by_day(:prdid, :todaydate)", nativeQuery = true)
    List<Object[]> callGetDailySales(@Param("prdid") Long prodId, @Param("todaydate") Date todayDate);

    default int getSalesByDay(long prodId, Date day){
        List<Object[]> result = callGetDailySales(prodId,day);
        return result.stream().map(res -> ((BigDecimal) res[1]).intValue()).findFirst().orElse(0);
    }

    default List<DataPoint> getSalesReport(Long prodId, Date iniDate, Date endDate){
        List<Object[]> results = callGetSales(prodId, iniDate, endDate);
        return results.stream()
                .map(res -> new DataPoint((Date) res[0],((BigDecimal) res[1]).intValue()))
                .collect(Collectors.toList());
    }
}


