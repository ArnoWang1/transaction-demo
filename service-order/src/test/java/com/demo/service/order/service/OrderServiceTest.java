package com.demo.service.order.service;


import com.demo.service.order.dao.LocalTransactionDAO;
import com.demo.service.order.dao.OrderDAO;
import com.demo.service.order.dao.OrderLevelDAO;
import com.demo.service.order.enums.TransactionStateEnum;
import com.demo.service.order.model.LocalTransaction;
import com.demo.service.order.model.Order;
import com.demo.service.order.model.OrderLevel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional(rollbackFor = Throwable.class)
public class OrderServiceTest {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private LocalTransactionDAO localTransactionDAO;

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private OrderLevelDAO orderLevelDAO;

    /**
     * 成功状态测试
     */
    @Test
    public void testBusinessSuccessful() {
        long tid = System.currentTimeMillis();
        orderService.orderTry(tid, 2020);

        //执行业务逻辑
        orderService.business(tid, 2020);
        //判断数据状态
        Order order = orderDAO.findById(1).get();
        assert 10 == order.getScore();
        OrderLevel orderLevel = orderLevelDAO.findById(1).get();
        assert 2 == orderLevel.getLevel();
        LocalTransaction localTransaction = localTransactionDAO.findById(tid).get();
        assert TransactionStateEnum.SUCCESSFUL.code.equals(localTransaction.getState());
    }

    /**
     * 失败状态测试
     */
    @Test
    public void testBusinessUnsuccessful() {
        long tid = System.currentTimeMillis();
        orderService.orderTry(tid, 2020);

        //执行业务逻辑
        orderService.business(tid, 2019);
        //判断数据状态
        Order order = orderDAO.findById(1).get();
        assert 0 == order.getScore();
        OrderLevel orderLevel = orderLevelDAO.findById(1).get();
        assert 1 == orderLevel.getLevel();
        LocalTransaction localTransaction = localTransactionDAO.findById(tid).get();
        assert TransactionStateEnum.UNSUCCESSFUL_01.code.equals(localTransaction.getState());
    }

    /**
     * 异常状态测试
     */
    @Test
    public void testBusinessException() throws InterruptedException {
        long tid = System.currentTimeMillis();
        orderService.orderTry(tid, 2020);

        //执行业务逻辑
        try {
            orderService.business(tid, 2021);
        } catch (Exception e) {
            log.error("business exception", e);
            assert e.getMessage().contains("business");
        }
        //遇到异常，事务还是初始状态
        LocalTransaction localTransaction = localTransactionDAO.findById(tid).get();
        assert TransactionStateEnum.RUNNABLE.code.equals(localTransaction.getState());
    }
}
