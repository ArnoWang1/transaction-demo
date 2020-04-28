package com.demo.service.user.service;

import com.demo.service.user.dto.ResultDTO;

public interface IUserService {

    /**
     * 主服务业务逻辑
     *
     * @param tid      唯一事务ID
     * @param username 用户账号
     * @return
     */
    ResultDTO business(long tid, String username);

    /**
     * 主服务事务状态查询
     *
     * @param tid 唯一事务ID
     * @return
     */
    public Integer getTransactionState(long tid);

    /**
     * 分服务Try接口有失败，主服务直接记录事务失败
     *
     * @param tid 唯一事务ID
     * @return
     */
    void fail(long tid);

}
