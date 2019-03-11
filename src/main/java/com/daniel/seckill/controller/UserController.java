package com.daniel.seckill.controller;

import com.daniel.seckill.common.CodeMsg;
import com.daniel.seckill.common.Result;
import com.daniel.seckill.common.ResultBuilder;
import com.daniel.seckill.service.UserService;
import com.daniel.seckill.vo.LoginVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 用户相关的Controller
 *
 * @author DanielLin07
 * @date 2018/11/18 17:50
 */
@Controller
@RequestMapping("user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/info")
    @ResponseBody
    public Result toLogin(LoginVO loginVO) {
        return ResultBuilder.buildResult(CodeMsg.SUCCESS, loginVO);
    }

}
