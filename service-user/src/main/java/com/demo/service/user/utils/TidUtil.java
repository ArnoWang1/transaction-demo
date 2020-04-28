package com.demo.service.user.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * 事务ID工具
 */
public class TidUtil {

    private static Snowflake snowflake = IdUtil.createSnowflake(1, 1);

    /**
     * 获取唯一事务ID
     */
    public static long getTid() {
        return snowflake.nextId();
    }

}
