package com.demo.service.order.controller;

import com.demo.service.order.dto.ResultDTO;
import com.demo.service.order.enums.ResultCodeEnum;
import com.demo.service.order.enums.TransactionStateEnum;
import com.demo.service.order.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.concurrent.Executor;

import static com.demo.service.order.constant.ApiUrlConst.SERVICE_USER_TRANSACTION_STATE_API;

/**
 * 订单接口
 */
@RestController
@Slf4j
public class OrderController {

    @Resource
    private IOrderService orderService;

    @Resource
    private Executor executor;

    @Resource
    private RestTemplate restTemplate;

    /**
     * 订单Try接口，每个分服务参与事务，只需要提供Try接口
     *
     * @param tid         全局唯一事务ID
     * @param orderNo     订单编号，分服务事务参数
     * @param sleepMillis 睡眠毫秒数，作用是等待主服务事务完成，由主服务根据自己的情况传一个建议值
     * @return
     */
    @GetMapping("/try")
    public ResultDTO orderTry(@RequestParam("tid") Long tid,
                              @RequestParam("orderNo") Integer orderNo,
                              @RequestParam("sleepMillis") Long sleepMillis) {
        //try服务
        ResultDTO resultDTO = orderService.orderTry(tid, orderNo);
        //结果没问题，启动异步线程任务
        if (ResultCodeEnum.SUCCESSFUL.code.equals(resultDTO.getCode())) {
            executor.execute(() -> {
                //失败重试3次
                int retry = 3;
                while (retry > 0) {
                    //先睡眠建议时间
                    try {
                        Thread.sleep(sleepMillis);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //调用主服务提供的事务状态查询接口，查询事务状态
                    Integer state = restTemplate.getForEntity(SERVICE_USER_TRANSACTION_STATE_API + tid, Integer.class).getBody();
                    //主服务事务状态是进行中，则再等等
                    if (TransactionStateEnum.RUNNABLE.code.equals(state)) {
                        log.info("service user transaction state is runnable, wait again");
                    }
                    //主服务事务状态失败了，则本地事务直接调用失败接口
                    if (TransactionStateEnum.UNSUCCESSFUL_01.code.equals(state)) {
                        orderService.mainTransactionFail(tid, state);
                        log.info("service user transaction state is unsuccessful_01, go fail");
                        break;
                    }
                    //主服务事务状态失败了，则本地事务直接调用失败接口
                    if (TransactionStateEnum.UNSUCCESSFUL_02.code.equals(state)) {
                        orderService.mainTransactionFail(tid, state);
                        log.info("service user transaction state is unsuccessful_02, go fail");
                        break;
                    }
                    //主服务事务状态成功了，则本地事务执行正常的事务逻辑
                    if (TransactionStateEnum.SUCCESSFUL.code.equals(state)) {
                        orderService.business(tid, orderNo);
                        log.info("service user transaction state is successful, go success");
                        break;
                    }
                    //其他情况重试
                    --retry;
                }
            });
        }
        return resultDTO;

    }

}
