package com.demo.service.order.dao;

import com.demo.service.order.model.OrderLevel;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface OrderLevelDAO extends CrudRepository<OrderLevel, Integer> {

    /**
     * 给用户增加等级
     */
    @Modifying
    @Query("UPDATE tb_order_level SET `level` = `level` + 1, modify_time = :modifyTime WHERE order_no = :orderNo")
    int updateLevelByOrderNo(@Param("orderNo") Integer orderNo, @Param("modifyTime") String modifyTime);

}
