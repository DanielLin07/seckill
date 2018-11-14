package com.daniel.seckill.common;

/**
 * 响应结果封装
 *
 * @author DanielLin07
 * @date 2018/11/10 13:59
 */
public class Result<T> {

    /**
     * 响应结果码
     */
    private int code;

    /**
     * 响应结果信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;


    public Result() {}

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
