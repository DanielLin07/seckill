package com.daniel.seckill.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.daniel.seckill.common.Result;
import com.daniel.seckill.common.ResultGenerator;
import com.daniel.seckill.model.User;
import com.daniel.seckill.redis.JedisAdapter;
import com.daniel.seckill.service.UserService;
import com.daniel.seckill.util.SecurityUtil;
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

    private JedisAdapter jedisAdapter;

    private UserService userService;


    /**
     * Spring官方推荐：使用构造方法注入Bean
     *
     * @param jedisAdapter jedisAdapter Bean
     * @param userService  userService Bean
     */
    @Autowired
    public LoginController(JedisAdapter jedisAdapter, UserService userService) {

        this.jedisAdapter = jedisAdapter;
        this.userService = userService;
    }


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
     * @param user 用户信息
     * @return 登录结果
     */
    @RequestMapping("/doLogin")
    @ResponseBody
    public Result doLogin(HttpServletResponse response, User user) {

        if (user == null) {
            return ResultGenerator.genFailResult("用户信息不能为空！");
        }

        // 根据用户名查询出的User，如果为null，则该用户不存在
        User confirmUser = userService.queryByUsername(user.getUsername());
        if (confirmUser == null) {
            return ResultGenerator.genFailResult("用户名不存在！");
        }

        // 判断加密后的密码与数据库中存放的密码是否一致
        if (!SecurityUtil.encryptPassword(user.getPassword(), user.getUsername(), confirmUser.getSalt())
                .equals(confirmUser.getPassword())) {
            return ResultGenerator.genFailResult("用户名与密码不匹配！");
        }

        String token = SecurityUtil.randomUUID();
        JSONObject data = new JSONObject();
        data.put("token", token);
        // addCookie(response, token, confirmUser);
        return ResultGenerator.genFullSuccessResult("登录成功！", data);
    }


    private void addCookie(HttpServletResponse response, String token, User user) {

        jedisAdapter.set("user:token:" + token, JSON.toJSONString(user));
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(3600 * 24);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
