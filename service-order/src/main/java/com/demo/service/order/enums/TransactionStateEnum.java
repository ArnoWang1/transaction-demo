package com.demo.service.order.enums;

public enum TransactionStateEnum {

    RUNNABLE(1, "事务进行中"),
    SUCCESSFUL(2, "事务成功"),
    UNSUCCESSFUL_01(3, "事务可控失败状态"),
    UNSUCCESSFUL_02(4, "事务不可控失败状态");

    public Integer code;

    public String desc;

    TransactionStateEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
