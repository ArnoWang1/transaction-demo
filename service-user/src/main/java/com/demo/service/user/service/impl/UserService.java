package com.demo.service.user.service.impl;

import com.demo.service.user.dao.MainTransactionDAO;
import com.demo.service.user.dao.UserDAO;
import com.demo.service.user.dao.UserLevelDAO;
import com.demo.service.user.dto.ResultDTO;
import com.demo.service.user.enums.ResultCodeEnum;
import com.demo.service.user.enums.TransactionStateEnum;
import com.demo.service.user.model.MainTransaction;
import com.demo.service.user.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户服务
 */
@Service
public class UserService implements IUserService {

    @Resource
    private MainTransactionDAO mainTransactionDAO;

    @Resource
    private UserDAO userDAO;

    @Resource
    private UserLevelDAO userLevelDAO;

    /**
     * 主服务业务逻辑
     *
     * @param tid      唯一事务ID
     * @param username 用户账号
     * @return
     */
    @Transactional(rollbackFor = Throwable.class)
    public ResultDTO business(long tid, String username) {
        //模拟主服务失败
        if ("no".equals(username)) {
            //保存事务失败记录
            mainTransactionDAO.save(generateMainTransaction(tid, TransactionStateEnum.UNSUCCESSFUL_01.code));
            return new ResultDTO(ResultCodeEnum.UNSUCCESSFUL.code, "user business error");
        }
        //执行主服务业务逻辑
        userDAO.updateScoreByUsername(username);
        userLevelDAO.updateLevelByUsername(username);
        //保存事务成功记录
        mainTransactionDAO.save(generateMainTransaction(tid, TransactionStateEnum.SUCCESSFUL.code));
        //模拟主服务出现异常
        if ("exception".equals(username)) {
            throw new RuntimeException("user service business exception");
        }
        return new ResultDTO(ResultCodeEnum.SUCCESSFUL.code, "");
    }

    /**
     * 生成事务记录对象
     *
     * @param tid   事务ID
     * @param state 事务状态
     * @return
     */
    private MainTransaction generateMainTransaction(long tid, int state) {
        MainTransaction mainTransaction = new MainTransaction();
        mainTransaction.setId(tid);
        mainTransaction.setState(state);
        mainTransaction.setCreateTime(new Date());
        mainTransaction.setNew(true);
        return mainTransaction;
    }

    /**
     * 分服务Try接口有失败，主服务直接记录事务失败
     *
     * @param tid 唯一事务ID
     * @return
     */
    @Override
    public void fail(long tid) {
        //保存事务失败记录
        mainTransactionDAO.save(generateMainTransaction(tid, TransactionStateEnum.UNSUCCESSFUL_01.code));
    }

    /**
     * 主服务事务状态查询
     *
     * @param tid 唯一事务ID
     * @return
     */
    @Override
    public Integer getTransactionState(long tid) {
        //查询事务状态，没数据记录默认还在进行中
        return mainTransactionDAO.findById(tid).map(MainTransaction::getState).orElse(TransactionStateEnum.RUNNABLE.code);
    }
}
