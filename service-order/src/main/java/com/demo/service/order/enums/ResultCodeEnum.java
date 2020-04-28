package com.demo.service.order.enums;

public enum ResultCodeEnum {

    SUCCESSFUL(0, "成功"),
    UNSUCCESSFUL(1, "失败");
    public Integer code;

    public String desc;

    ResultCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
