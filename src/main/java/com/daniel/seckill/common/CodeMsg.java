package com.daniel.seckill.common;

/**
 * 请求结果枚举类
 *
 * @author DanielLin07
 * @date 2018/11/10 13:58
 */
public enum CodeMsg {

    /**
     * 成功处理请求
     */
    SUCCESS(200, "SUCCESS"),

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

    CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "CodeMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
