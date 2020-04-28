package com.demo.service.order.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

/**
 * 用户等级表
 */
@Data
@Table("tb_order_level")
public class OrderLevel {

    @Id
    private Integer id;

    private Integer orderNo;

    private Integer level;

    /**
     * 创建时间
     */
    private Date modifyTime;

}
