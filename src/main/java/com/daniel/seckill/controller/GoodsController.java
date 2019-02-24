package com.daniel.seckill.controller;

import com.daniel.seckill.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商品相关的Controller
 *
 * @author DanielLin07
 * @date 2019/1/19 16:27
 */
@Controller
@RequestMapping("goods")
public class GoodsController {

    @RequestMapping("toList")
    public String toList(Model model, User user) {
        model.addAttribute("user", user);
        return "goods_list";
    }
}
