package com.demo.service.order.model;

import com.demo.service.order.enums.TransactionStateEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

/**
 * 主事务记录数据
 */
@Data
@Table("tb_local_transaction")
public class LocalTransaction implements Persistable {

    @Id
    private Long id;

    /**
     * 事务状态
     *
     * @see TransactionStateEnum
     */
    private Integer state;

    //业务参数
    private String param;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date modifyTime;

    @Version
    private Integer version;

    @Transient
    private boolean isNew;

    @Override
    public boolean isNew() {
        return isNew;
    }
}
