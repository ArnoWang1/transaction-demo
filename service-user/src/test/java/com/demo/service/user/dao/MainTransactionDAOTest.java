package com.demo.service.user.dao;

import com.demo.service.user.enums.TransactionStateEnum;
import com.demo.service.user.model.MainTransaction;
import com.demo.service.user.utils.TidUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional(rollbackFor = Throwable.class)
public class MainTransactionDAOTest {

    @Autowired
    private MainTransactionDAO mainTransactionDao;

    @Test
    public void testSave() {
        long tid = TidUtil.getTid();
        MainTransaction mainTransaction = new MainTransaction();
        mainTransaction.setId(tid);
        mainTransaction.setState(TransactionStateEnum.SUCCESSFUL.code);
        mainTransaction.setCreateTime(new Date());
        mainTransaction.setNew(true);
        log.info("model {}", mainTransaction.toString());
        mainTransactionDao.save(mainTransaction);
        //查询比较
        MainTransaction queryModel = mainTransactionDao.findById(tid).get();
        assert mainTransaction.getState().equals(queryModel.getState());
    }

}
