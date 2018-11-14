package com.daniel.seckill.common;

import org.apache.commons.lang3.StringUtils;

/**
 * 请求结果生成工具
 *
 * @author DanielLin07
 * @date 2018/11/10 14:00
 */
public class ResultGenerator {

    /**
     * 生成响应成功的默认Result
     *
     * @return 默认Result
     */
    public static Result genSuccessResult() {

        return new Result(CodeMsg.SUCCESS.code, CodeMsg.SUCCESS.msg);
    }


    /**
     * 生成带有message信息响应成功Result
     *
     * @param message 响应信息
     * @return 响应成功Result
     */
    public static <T> Result<T> genSuccessResult(String message) {

        Result<T> result = new Result<>();
        result.setCode(CodeMsg.SUCCESS.code);
        if (StringUtils.isBlank(message)) {
            message = CodeMsg.SUCCESS.msg;
        }
        result.setMessage(message);
        return result;
    }


    /**
     * 生成带有data数据的响应成功Result
     *
     * @param data 响应数据
     * @return 响应成功Result
     */
    public static <T> Result<T> genFullSuccessResult(T data) {

        return new Result<>(CodeMsg.SUCCESS.code, CodeMsg.SUCCESS.msg, data);
    }


    /**
     * 生成带有data数据的响应成功Result
     *
     * @param message 响应信息
     * @param data    响应数据
     * @return 响应成功Result
     */
    public static <T> Result<T> genFullSuccessResult(String message, T data) {

        Result<T> result = genSuccessResult(message);
        result.setData(data);
        return result;
    }


    /**
     * 生成响应失败的默认Result
     *
     * @return 默认Result
     */
    public static Result genFailResult() {

        return new Result(CodeMsg.SERVER_ERROR.code, CodeMsg.SERVER_ERROR.msg);
    }


    /**
     * 生成带有message信息响应失败Result
     *
     * @param message 响应信息
     * @return 响应失败Result
     */
    public static Result genFailResult(String message) {

        Result result = new Result();
        result.setCode(CodeMsg.SERVER_ERROR.code);
        if (StringUtils.isBlank(message)) {
            message = CodeMsg.SERVER_ERROR.msg;
        }
        result.setMessage(message);
        return result;
    }

}
