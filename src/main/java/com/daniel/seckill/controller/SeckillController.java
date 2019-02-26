package com.daniel.seckill.controller;

import com.daniel.seckill.common.Constant;
import com.daniel.seckill.model.OrderInfo;
import com.daniel.seckill.model.SeckillOrderInfo;
import com.daniel.seckill.model.User;
import com.daniel.seckill.service.GoodsService;
import com.daniel.seckill.service.OrderService;
import com.daniel.seckill.service.SeckillService;
import com.daniel.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 秒杀功能的Controller
 *
 * @author DanielLin07
 * @date 2019/1/19 16:27
 */
@Controller
@RequestMapping("seckill")
public class SeckillController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SeckillService seckillService;

    @RequestMapping("doSeckill")
    public String doSeckill(Model model, User user, @RequestParam("goodsId") long goodsId) {
        // 首先判断用户是否登录
        if (user == null) {
            return "login";
        }

        // 判断库存是否足够
        GoodsVO goodsVO = goodsService.queryGoodsVOById(goodsId);
        int goodsStock = goodsVO.getGoodsStock();
        if (goodsStock <= 0) {
            model.addAttribute("msg", Constant.SECKILL_OVER);
            return "seckillFail";
        }

        // 判断用户是否重复秒杀
        SeckillOrderInfo seckillOrderInfo = orderService.querySeckillOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if (seckillOrderInfo != null) {
            model.addAttribute("msg", Constant.SECKILL_REPEAT);
            return "seckillFail";
        }

        // 执行秒杀操作，先减库存，再下订单，最后再写入秒杀订单表
        OrderInfo orderInfo = seckillService.doSeckill(user, goodsVO);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goodsVO", goodsVO);
        return "orderDetail";
    }

}
