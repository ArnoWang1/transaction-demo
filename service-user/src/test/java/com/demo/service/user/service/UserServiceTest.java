package com.demo.service.user.service;


import com.demo.service.user.dao.MainTransactionDAO;
import com.demo.service.user.dao.UserDAO;
import com.demo.service.user.dao.UserLevelDAO;
import com.demo.service.user.dto.ResultDTO;
import com.demo.service.user.enums.ResultCodeEnum;
import com.demo.service.user.enums.TransactionStateEnum;
import com.demo.service.user.model.MainTransaction;
import com.demo.service.user.model.User;
import com.demo.service.user.model.UserLevel;
import com.demo.service.user.utils.TidUtil;
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
public class UserServiceTest {

    @Autowired
    private IUserService userService;

    @Autowired
    private MainTransactionDAO mainTransactionDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserLevelDAO userLevelDAO;

    /**
     * 成功状态测试
     */
    @Test
    public void testBusinessSuccessful() {
        long tid = TidUtil.getTid();
        //执行业务逻辑
        ResultDTO resultDTO = userService.business(tid, "admin");
        //判断结果
        assert ResultCodeEnum.SUCCESSFUL.code.equals(resultDTO.getCode());
        //判断数据状态
        User user = userDAO.findById(1).get();
        assert 10 == user.getScore();
        UserLevel userLevel = userLevelDAO.findById(1).get();
        assert 2 == userLevel.getLevel();
        MainTransaction mainTransaction = mainTransactionDAO.findById(tid).get();
        assert TransactionStateEnum.SUCCESSFUL.code.equals(mainTransaction.getState());
    }

    /**
     * 失败状态测试
     */
    @Test
    public void testBusinessUnsuccessful() {
        long tid = TidUtil.getTid();
        //执行业务逻辑
        ResultDTO resultDTO = userService.business(tid, "no");
        //判断结果
        assert ResultCodeEnum.UNSUCCESSFUL.code.equals(resultDTO.getCode());
        //判断数据状态
        User user = userDAO.findById(1).get();
        assert 0 == user.getScore();
        UserLevel userLevel = userLevelDAO.findById(1).get();
        assert 1 == userLevel.getLevel();
        MainTransaction mainTransaction = mainTransactionDAO.findById(tid).get();
        assert TransactionStateEnum.UNSUCCESSFUL_01.code.equals(mainTransaction.getState());
    }

    /**
     * 异常状态测试
     */
    @Test
    public void testBusinessException() throws InterruptedException {
        long tid = TidUtil.getTid();
        //执行业务逻辑
        try {
            ResultDTO resultDTO = userService.business(tid, "exception");
        } catch (Exception e) {
            log.error("business exception", e);
            assert e.getMessage().contains("business");
        }
    }
}
