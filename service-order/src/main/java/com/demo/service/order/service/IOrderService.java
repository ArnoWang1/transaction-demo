package com.demo.service.order.service;

import com.demo.service.order.dto.ResultDTO;

public interface IOrderService {

    /**
     * 订单服务Try接口
     *
     * @return
     */
    ResultDTO orderTry(Long tid, Integer orderNo);

    /**
     * 主服务业务逻辑
     *
     * @param tid      唯一事务ID
     * @param username 用户账号
     * @return
     */
    void business(long tid, Integer orderNo);

    /**
     * 主服务事务状态失败
     *
     * @param tid   唯一事务ID
     * @param state 失败状态编码
     * @return
     */
    void mainTransactionFail(long tid, Integer state);
}
