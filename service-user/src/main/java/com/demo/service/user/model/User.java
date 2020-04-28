package com.demo.service.user.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

/**
 * 用户信息表
 */
@Data
@Table("tb_user")
public class User {

    @Id
    private Integer id;

    private String username;

    private Integer score;

    /**
     * 创建时间
     */
    private Date modifyTime;

}
