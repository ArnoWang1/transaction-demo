package com.demo.service.user.model;

import com.demo.service.user.enums.TransactionStateEnum;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

/**
 * 主事务记录数据
 */
@Data
@Table("tb_main_transaction")
public class MainTransaction implements Persistable {

    @Id
    private Long id;

    /**
     * 事务状态
     *
     * @see TransactionStateEnum
     */
    private Integer state;

    /**
     * 创建时间
     */
    private Date createTime;

    @Transient
    private boolean isNew;

    @Override
    public boolean isNew() {
        return isNew;
    }
}
