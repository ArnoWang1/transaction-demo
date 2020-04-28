package com.demo.service.order.service.impl;

import com.demo.service.order.dao.LocalTransactionDAO;
import com.demo.service.order.dao.OrderDAO;
import com.demo.service.order.dao.OrderLevelDAO;
import com.demo.service.order.dto.ResultDTO;
import com.demo.service.order.enums.ResultCodeEnum;
import com.demo.service.order.enums.TransactionStateEnum;
import com.demo.service.order.model.LocalTransaction;
import com.demo.service.order.service.IOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 用户服务
 */
@Service
public class OrderService implements IOrderService {

    @Resource
    private LocalTransactionDAO localTransactionDAO;

    @Resource
    private OrderDAO orderDAO;

    @Resource
    private OrderLevelDAO orderLevelDAO;

    private DateTimeFormatter FULL_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 分服务Try接口
     *
     * @return
     */
    @Override
    public ResultDTO orderTry(Long tid, Integer orderNo) {
        //模拟try成功
        if (Integer.valueOf(2020).equals(orderNo)) {
            //保存本地事务记录，状态为进行中
            LocalTransaction localTransaction = new LocalTransaction();
            localTransaction.setId(tid);
            localTransaction.setState(TransactionStateEnum.RUNNABLE.code);
            localTransaction.setParam(String.valueOf(orderNo));
            localTransaction.setNew(true);
            localTransaction.setCreateTime(new Date());
            localTransaction.setModifyTime(new Date());
            localTransaction.setVersion(1);
            localTransactionDAO.save(localTransaction);
            return new ResultDTO(ResultCodeEnum.SUCCESSFUL.code, "");
        }
        //模拟失败的情况
        return new ResultDTO(ResultCodeEnum.UNSUCCESSFUL.code, "service order try unsuccessful");
    }

    /**
     * 主服务业务逻辑
     *
     * @param tid      唯一事务ID
     * @param orderNo 用户账号
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void business(long tid, Integer orderNo) {
        //模拟分服务失败
        if (Integer.valueOf(2019).equals(orderNo)) {
            //本地事务记录状态改成失败
            LocalTransaction localTransaction = localTransactionDAO.findById(tid).get();
            localTransaction.setState(TransactionStateEnum.UNSUCCESSFUL_01.code);
            localTransaction.setNew(false);
            localTransaction.setModifyTime(new Date());
            localTransactionDAO.save(localTransaction);
            return;
        }
        //执行主服务业务逻辑
        orderDAO.updateScoreByOrderNo(orderNo, LocalDateTime.now().format(FULL_FORMATTER));
        orderLevelDAO.updateLevelByOrderNo(orderNo, LocalDateTime.now().format(FULL_FORMATTER));
        //本地事务记录状态改为成功
        LocalTransaction localTransaction = localTransactionDAO.findById(tid).get();
        localTransaction.setState(TransactionStateEnum.SUCCESSFUL.code);
        localTransaction.setNew(false);
        localTransaction.setModifyTime(new Date());
        localTransactionDAO.save(localTransaction);
        //TODO 事务逻辑最好有幂等性保证
        //模拟分服务出现异常
        if (Integer.valueOf(2021).equals(orderNo)) {
            throw new RuntimeException("order service business exception");
        }
    }

    /**
     * 主服务事务状态失败
     *
     * @param tid   唯一事务ID
     * @param state 失败状态编码
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void mainTransactionFail(long tid, Integer state) {
        //分服务更新本地事务记录状态，并释放资源等
        LocalTransaction localTransaction = localTransactionDAO.findById(tid).get();
        localTransaction.setState(state);
        localTransaction.setNew(false);
        localTransaction.setModifyTime(new Date());
        localTransactionDAO.save(localTransaction);
    }

}
