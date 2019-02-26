package com.daniel.seckill.common;

/**
 * 常量枚举类
 *
 * @author DanielLin07
 * @date 2018/11/10 13:58
 */
public enum Constant {

    /**
     * 秒杀模块 5005XX
     */
    SECKILL_OVER(500500, "商品秒杀已经结束"),
    SECKILL_REPEAT(500501, "不能重复秒杀"),

    /**
     * 请求无效
     */
    BAD_REQUEST(412, "请求无效"),

    /**
     * 服务端异常
     */
    SERVER_ERROR(500, "服务端异常");

    /**
     * 状态码
     */
    int code;

    /**
     * 信息
     */
    String msg;

    Constant(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
