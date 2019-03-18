package com.daniel.seckill.exception;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.daniel.seckill.common.CodeMsg;
import com.daniel.seckill.common.Result;
import com.daniel.seckill.common.ResultBuilder;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局异常处理器
 *
 * @author DanielLin07
 * @date 2019/3/7 17:28
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(HttpServletRequest request, Exception e) {
        e.printStackTrace();

        if (e instanceof GlobalException) {
            GlobalException exception = (GlobalException) e;
            return ResultBuilder.build(exception.getCodeMsg());
        } else if (e instanceof BindException) {
            BindException ex = (BindException) e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return ResultBuilder.build(CodeMsg.SERVER_ERROR.code, msg);
        } else {
            return ResultBuilder.build(CodeMsg.SERVER_ERROR);
        }
    }
}
