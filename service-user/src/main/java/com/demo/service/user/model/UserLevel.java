package com.demo.service.user.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

/**
 * 用户等级表
 */
@Data
@Table("tb_user_level")
public class UserLevel {

    @Id
    private Integer id;

    private String username;

    private Integer level;

    /**
     * 创建时间
     */
    private Date modifyTime;

}
