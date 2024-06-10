package com.fatec.livrariaonlinejpa.repositories;

import com.fatec.livrariaonlinejpa.model.Pedido;
import com.fatec.livrariaonlinejpa.util.DataPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findPedidoByClienteId(long id);

    //----Used for the daily schedule task that generates the sales report--------------------------
    @Query(value = "CALL get_product_sales_by_day(:prdid, :todaydate)", nativeQuery = true)
    List<Object[]> callGetDailySales(@Param("prdid") Long prodId, @Param("todaydate") LocalDate todayDate);

    default int getSalesByDay(long prodId, LocalDate day){
        List<Object[]> result = callGetDailySales(prodId,day);
        return result.stream().map(res -> ((BigDecimal) res[1]).intValue()).findFirst().orElse(0);
    }
    //-----------------------------------------------------------------------------------------------

}


