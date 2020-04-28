package com.demo.service.user.controller;

import com.demo.service.user.dto.ResultDTO;
import com.demo.service.user.enums.ResultCodeEnum;
import com.demo.service.user.service.IUserService;
import com.demo.service.user.utils.TidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

import static com.demo.service.user.constant.ApiUrlConst.SERVICE_ORDER_TRY_API;

/**
 * 订单接口
 */
@RestController
@Slf4j
public class UserController {

    @Resource
    private IUserService userService;

    @Resource
    private RestTemplate restTemplate;

    /**
     * 事务发起入口
     *
     * @param username 账号
     * @param orderNo  订单编号，分服务事务参数
     * @return
     */
    @GetMapping("/business")
    public ResultDTO business(@RequestParam("username") String username,
                              @RequestParam("orderNo") Integer orderNo) {
        //生成全局唯一事务ID
        long tid = TidUtil.getTid();
        //首先调用各分服务Try接口
        ResultDTO resultDTO = restTemplate.getForEntity(SERVICE_ORDER_TRY_API + "?tid=" + tid + "&orderNo=" + orderNo + "&sleepMillis=200",
                ResultDTO.class).getBody();
        //分服务Try失败
        if (ResultCodeEnum.UNSUCCESSFUL.code.equals(resultDTO.getCode())) {
            userService.fail(tid);
            return resultDTO;
        } else {
            ResultDTO myResultDTO = userService.business(tid, username);
            return myResultDTO;
        }
    }

    /**
     * 主服务事务状态查询接口
     *
     * @param tid 全局唯一事务ID
     * @return
     */
    @GetMapping("/transaction/state")
    public Integer getTransactionState(@RequestParam("tid") Long tid) {
        return userService.getTransactionState(tid);
    }

}
