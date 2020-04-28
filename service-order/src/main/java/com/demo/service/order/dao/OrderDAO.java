package com.demo.service.order.dao;

import com.demo.service.order.model.Order;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface OrderDAO extends CrudRepository<Order, Integer> {

    /**
     * 给用户增加积分
     */
    @Modifying
    @Query("UPDATE tb_order SET score = score + 10, modify_time = :modifyTime WHERE order_no = :orderNo")
    int updateScoreByOrderNo(@Param("orderNo") Integer orderNo, @Param("modifyTime") String modifyTime);

}
