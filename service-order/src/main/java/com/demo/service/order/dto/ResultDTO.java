package com.demo.service.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 接口结果对象
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO {

    /**
     * 结果码
     */
    private Integer code;

    /**
     * 附带内容
     */
    private String msg;

}
