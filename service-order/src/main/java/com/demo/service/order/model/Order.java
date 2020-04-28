package com.demo.service.order.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

/**
 * 用户信息表
 */
@Data
@Table("tb_order")
public class Order {

    @Id
    private Integer id;

    private Integer orderNo;

    private Integer score;

    /**
     * 创建时间
     */
    private Date modifyTime;

}
