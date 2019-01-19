package com.daniel.seckill.controller;

import com.daniel.seckill.common.Result;
import com.daniel.seckill.common.ResultGenerator;
import com.daniel.seckill.redis.JedisAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author DanielLin07
 * @date 2018/11/12 21:42
 */
@Controller
@RequestMapping("samples")
public class SampleController {

    private JedisAdapter jedisAdapter;

    @Autowired
    public SampleController(JedisAdapter jedisAdapter) {
        this.jedisAdapter = jedisAdapter;
    }

    @RequestMapping("set")
    @ResponseBody
    public Result testSet() {
        try {
            jedisAdapter.set("books1", "python1");
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult();
        }
    }

    @RequestMapping("get")
    @ResponseBody
    public Result testGet() {
        try {
            String data = jedisAdapter.get("books1");
            System.out.println("data:" + data);
            return ResultGenerator.genFullSuccessResult(data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult();
        }
    }
}
