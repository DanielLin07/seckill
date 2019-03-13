package com.daniel.seckill.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.daniel.seckill.common.CodeMsg;
import com.daniel.seckill.common.Result;
import com.daniel.seckill.common.ResultBuilder;
import com.daniel.seckill.model.User;
import com.daniel.seckill.redis.UserKey;
import com.daniel.seckill.service.RedisService;
import com.daniel.seckill.service.UserService;
import com.daniel.seckill.util.SecurityUtil;
import com.daniel.seckill.vo.LoginVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


/**
 * 用户登录相关的Controller
 *
 * @author DanielLin07
 * @date 2018/11/18 17:50
 */
@Controller
@RequestMapping("login")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private RedisService redisService;
    @Autowired
    private UserService userService;

    /**
     * 跳转至登录界面
     *
     * @return 登录界面
     */
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    /**
     * 执行用户登录
     *
     * @param loginVO 登录用户信息
     * @return 登录结果
     */
    @RequestMapping("/doLogin")
    @ResponseBody
    public Result doLogin(HttpServletResponse response, LoginVO loginVO) {
        if (loginVO == null) {
            return ResultBuilder.buildResult(CodeMsg.USERNAME_EMPTY);
        }

        // 根据用户名查询出的User，如果为null，则该用户不存在
        User confirmUser = userService.queryByUsername(loginVO.getUsername());
        if (confirmUser == null) {
            return ResultBuilder.buildResult(CodeMsg.USERNAME_NOT_EXIST);
        }

        // 判断加密后的密码与数据库中存放的密码是否一致
        if (!SecurityUtil.encryptPassword(loginVO.getPassword(), loginVO.getUsername(), confirmUser.getSalt())
                .equals(confirmUser.getPassword())) {
            return ResultBuilder.buildResult(CodeMsg.PASSWORD_ERROR);
        }

        String token = SecurityUtil.randomUUID();
        JSONObject data = new JSONObject();
        data.put("token", token);
        addCookie(response, token, confirmUser);
        return ResultBuilder.buildResult(CodeMsg.SUCCESS, data);
    }

    /**
     * 添加用户的token信息到Cookie中
     *
     * @param response response
     * @param token    用户token
     * @param user     User对象
     */
    private void addCookie(HttpServletResponse response, String token, User user) {
        redisService.setex(UserKey.getByToken, token, JSON.toJSONString(user));
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(UserKey.getByToken.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
